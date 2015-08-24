package rest.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rest.Application;
import rest.user.User;
import rest.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles(value = "tests")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TaskControllerTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .addFilter(springSecurityFilterChain)
                .build();

        User firstUser = new User("John", "Rambo", "jRambo", "abcdefgh");
        User secondUser = new User("Johny", "Bravo", "jBravo", "johny123");
        userRepository.save(firstUser);
        userRepository.save(secondUser);

        List<Task> tasks = new ArrayList<Task>();
        Task firstTask = new Task("first_task", false);
        Task secondTask = new Task("second_task", true);
        Task thridTask = new Task("thrid_task", false);

        firstTask.setUser(firstUser);
        secondTask.setUser(firstUser);
        thridTask.setUser(secondUser);

        taskRepository.save(firstTask);
        taskRepository.save(secondTask);
        taskRepository.save(thridTask);
    }

    @After
    public void destroy() throws Exception{
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username="jBravo", password = "johny123")
    public void create_new_task_with_authorize_user_return_ok() throws Exception {
        Task task = new Task("New Task",false);
        String jsonTask = new ObjectMapper().writeValueAsString(task);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTask))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="jRambo", password = "abcdefgh")
    public void return_all_persisted_task_for_jRambo() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].taskName", is("first_task")))
                .andExpect(jsonPath("$[0].done", is(false)))
                .andExpect(jsonPath("$[1].taskName", is("second_task")))
                .andExpect(jsonPath("$[1].done", is(true)));
    }

    @Test
    @WithMockUser(username="jRambo", password = "abcdefgh")
    public void updated_task_with_new_status() throws Exception {
        Task task = taskRepository.findByName("first_task").iterator().next();
        task.setDone(true);

        String jsonTask = new ObjectMapper().writeValueAsString(task);

        mockMvc.perform(put("/api/tasks/"+task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTask))
                .andExpect(status().isOk());
    }

    @Test
    public void update_task_with_not_logged_user_return_unauthorized() throws Exception {
        Task task = taskRepository.findByName("first_task").iterator().next();
        task.setDone(true);

        String jsonTask = new ObjectMapper().writeValueAsString(task);

        mockMvc.perform(put("/api/tasks/"+task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTask))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="jRambo", password = "abcdefgh")
    public void update_not_existing_task_return_notfound() throws Exception {
        Task task = new Task("someTask",true);
        String jsonTask = new ObjectMapper().writeValueAsString(task);

        mockMvc.perform(put("/api/tasks/"+5702354)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTask))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="jRambo", password = "abcdefgh")
    public void delete_task() throws Exception {
        Task t = taskRepository.findByName("first_task").iterator().next();

        mockMvc.perform(delete("/api/tasks/"+t.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username="jRambo", password = "abcdefgh")
    public void deleted_not_existing_task_return_no_content() throws Exception {
        mockMvc.perform(delete("/api/tasks/"+242335))
                .andExpect(status().isNoContent());
    }
}