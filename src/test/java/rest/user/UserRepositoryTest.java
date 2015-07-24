package rest.user;

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

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JdbcTemplate template;

    @Autowired
    WebApplicationContext webApplicationContext;


    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void return_all_persisted_users () throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int) 1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Rambo")))
                .andExpect(jsonPath("$[1].id", is((int) 2)))
                .andExpect(jsonPath("$[1].firstName", is("Johny")))
                .andExpect(jsonPath("$[1].lastName", is("Bravo")));
    }

//    @Test
//    public void return_all_users_for_lastName() throws Exception {
//        userRepository.save(new User("Martin", "Bravo"));
//
//        String lastName = "Bravo";
//        List<User> users = userRepository.findByLastName(lastName);
//
//        assertEquals(2, users.size());
//        User user0 = users.get(0);
//        User user1 = users.get(1);
//
//        assertEquals("Johny", user0.getFirstName());
//        assertEquals("Martin", user1.getFirstName());
//
//        assertEquals(lastName, user0.getLastName());
//        assertEquals(lastName, user1.getLastName());
//    }


    @Test
    public void test_FlyWay_initscript_data_count_is_two() throws Exception {
        assertEquals(new Integer(3), this.template.queryForObject(
                "SELECT COUNT(*) from USER", Integer.class));
    }
}