package rest.task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rest.Application;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JdbcTemplate template;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        taskRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void newly_created_repository_is_empty() throws Exception {
        assertEquals(0, taskRepository.count());
    }

    @Test
    public void check_added_task_is_in_repository() throws Exception {
        Task task = new Task("New Task", true);
        taskRepository.save(task);

        Task taskFromRepository = taskRepository.findByName("New Task");

        assertTrue(task.equals(taskFromRepository));
    }

    @Test
    public void deleted_task_is_no_longer_in_repository() throws Exception {
        // Arrange | Given
        taskRepository.save(new Task("Task name ", false));
        // Act     | When
        taskRepository.delete(1);
        // Assert  | Then
        assertFalse(taskRepository.exists(1));
    }

//    @Test
//    public void return_all_persisted_tasks_from_json () throws Exception {
//        taskRepository.save(new Task("first_task", false));
//        taskRepository.save(new Task("second_task", true));
//
//        mockMvc.perform(get("/tasks"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].taskName", is("first_task")))
//                .andExpect(jsonPath("$[0].done", is(false)))
//                .andExpect(jsonPath("$[1].taskName", is("second_task")))
//                .andExpect(jsonPath("$[1].done", is(true)));
//    }
}