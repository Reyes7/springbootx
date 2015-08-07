package rest.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
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
public class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebApplicationContext webApplicationContext;


    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
//        userRepository.deleteAll();
        userRepository.save(new User("Martin", "Bravo","mBravo","bravo123"));
        userRepository.save(new User("Johny", "Bravo", "jBravo", "johny123"));

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void return_all_persisted_users () throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2))); // size =>2
//                .andExpect(jsonPath("$[0].id", is((int) 1)))
//                .andExpect(jsonPath("$[0].firstName", is("Martin")))
//                .andExpect(jsonPath("$[0].lastName", is("Bravo")))
//                .andExpect(jsonPath("$[1].id", is((int) 2)))
//                .andExpect(jsonPath("$[1].firstName", is("Johny")))
//                .andExpect(jsonPath("$[1].lastName", is("Bravo")));
    }

    @Test
    public void create_user() throws Exception {
        User user = new User("TestFirstName","TestLastName",
                             "TestLogin","TestPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser))
                .andExpect(status().isOk());
    }
}