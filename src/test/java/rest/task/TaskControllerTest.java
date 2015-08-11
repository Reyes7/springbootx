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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles(value = "tests")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TaskControllerTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        List<User> users = new ArrayList<User>();
        users.add(new User("John", "Rambo", "jRambo", "abcdefgh"));
        users.add(new User("Johny", "Bravo", "jBravo", "johny123"));
        for (User user : users) {
            String jsonUser = new ObjectMapper().writeValueAsString(user);
            mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUser));
        }

        List<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("first_task", false));
        tasks.add(new Task("second_task", true));
        for (Task task : tasks) {
            String jsonTask = new ObjectMapper().writeValueAsString(task);
            mockMvc.perform(post("/tasks/jBravo")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonTask));
        }

        Task task = new Task("thrid_task", false);
        String jsonTask = new ObjectMapper().writeValueAsString(task);

        mockMvc.perform(post("/tasks/jRambo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTask))
                .andExpect(status().isOk());
    }

    @After
    public void destroy() throws Exception{
        userRepository.delete(userRepository.findByLogin("jBravo"));
        userRepository.delete(userRepository.findByLogin("jRambo"));
    }

    @Test
    public void check_added_task_is_in_repository() throws Exception {
        Task t = taskRepository.findByName("first_task").iterator().next();

        mockMvc.perform(get("/tasks/"+t.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.taskName", is("first_task")))
                .andExpect(jsonPath("$.done", is(true)));
    }

    @Test
    public void add_task_to_not_existing_user_return_404() throws Exception {
        Task task = new Task("New Task", true);
        String jsonTask = new ObjectMapper().writeValueAsString(task);

        mockMvc.perform(post("/tasks/someUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTask))
                .andExpect(status().isNotFound());
    }

    @Test
    public void return_all_persisted_task() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].taskName", is("first_task")))
                .andExpect(jsonPath("$[0].done", is(false)))
                .andExpect(jsonPath("$[1].taskName", is("second_task")))
                .andExpect(jsonPath("$[1].done", is(true)))
                .andExpect(jsonPath("$[2].taskName", is("thrid_task")))
                .andExpect(jsonPath("$[2].done", is(false)));
    }

    @Test
    @Ignore
    public void return_all_persisted_tasks_for_user() throws Exception {
        mockMvc.perform(get("/tasks/jBravo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].taskName", is("first_task")))
                .andExpect(jsonPath("$[0].done", is(false)))
                .andExpect(jsonPath("$[1].taskName", is("second_task")))
                .andExpect(jsonPath("$[1].done", is(true)));
    }

    @Test
    public void update_task_change_status_false_to_true() throws Exception {
        Task t = taskRepository.findByName("first_task").iterator().next();
        mockMvc.perform(get("/tasks/"+t.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.taskName", is("first_task")))
                .andExpect(jsonPath("$.done", is(true)));
    }

    @Test
    public void update_task_change_status_true_to_false() throws Exception {
        Task t = taskRepository.findByName("second_task").iterator().next();
        mockMvc.perform(get("/tasks/"+t.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.taskName", is("second_task")))
                .andExpect(jsonPath("$.done", is(false)));
    }

    @Test
    public void update_not_existing_task_return_404() throws Exception {
        mockMvc.perform(get("/tasks/11111"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleted_task_not_existing_in_database() throws Exception {
        Task t = taskRepository.findByName("first_task").iterator().next();

        mockMvc.perform(delete("/tasks/"+t.getId()));
        mockMvc.perform(get("/tasks/"+t.getId()))
                .andExpect(status().isNotFound());
    }
}