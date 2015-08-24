package rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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


import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(value = "tests")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                    .apply(springSecurity())
                    .addFilter(springSecurityFilterChain)
                    .build();

        User firstUser = new User("Martin","Bravo","mBravo","bravo123");
        User secondUser = new User("Johny","Bravo","jBravo","johny123");
        userRepository.save(firstUser);
        userRepository.save(secondUser);
    }

    @After
    public void destroy() throws Exception{
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username="mBravo", password = "bravo123")
    public void return_all_persisted_users() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$[0].firstName", is("Martin")))
                .andExpect(jsonPath("$[0].lastName", is("Bravo")))
                .andExpect(jsonPath("$[1].firstName", is("Johny")))
                .andExpect(jsonPath("$[1].lastName", is("Bravo")));
    }

    @Test
    public void create_user() throws Exception {
        User user = new User("Test","Test","TestLogin","abcdefghij");
        String jsonUser = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser))
                .andExpect(status().isCreated());
        userRepository.delete(userRepository.findByLogin("TestLogin"));
    }

    @Test
    public void create_users_with_the_same_login_retun_bad_request() throws Exception {
        User user = new User("Martin","Bravo","jBravo","bravo123");

        String jsonUser2 = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser2))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username="mBravo", password = "bravo123")
    public void update_user() throws Exception {
        User user = new User("Test","Test","TestLogin","abcdefghij");
        String jsonUser = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser))
                .andExpect(status().isCreated());

        UserHelper userHelper = new UserHelper("TestLogin", "Updated", "Updated", "abcdefghij", "zxcvbn");
        String jsonUserHelper = new ObjectMapper().writeValueAsString(userHelper);
        mockMvc.perform(put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUserHelper))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="mBravo", password = "bravo123")
    public void update_not_existing_user_return_404() throws Exception {
        UserHelper userHelper = new UserHelper("Test", "Test", "Test", "Test", "Test");
        String jsonUserHelper = new ObjectMapper().writeValueAsString(userHelper);

        mockMvc.perform(put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUserHelper))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="mBravo", password = "bravo123")
    public void update_user_with_incorrect_password_return_400() throws Exception {
        UserHelper userHelper = new UserHelper("jBravo", "Rambo", "John", "Bad Password", "john456");
        String jsonUserHelper = new ObjectMapper().writeValueAsString(userHelper);

        mockMvc.perform(put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUserHelper))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void login_user() throws Exception {
        mockMvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("login", "jBravo")
                .param("password", "johny123"))
                .andExpect(status().isOk());
    }

    @Test
    public void login_user_with_not_existing_login_return_401() throws Exception {
        mockMvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("login", "someLogin")
                .param("password", "somePassword"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void login_user_with_bad_password_return_401() throws Exception {
        mockMvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("login", "jBravo")
                .param("password", "abcdefghij"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="jBravo", password = "johny123")
    public void return_user() throws Exception {
        mockMvc.perform(get("/api/users/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.firstName", is("Johny")))
                .andExpect(jsonPath("$.lastName", is("Bravo")));
    }

    @Test
    @WithMockUser(username="jBravo", password = "johny123")
    public void delete_user() throws Exception {
        mockMvc.perform(delete("/api/users"))
                .andExpect(status().isOk());
    }
}