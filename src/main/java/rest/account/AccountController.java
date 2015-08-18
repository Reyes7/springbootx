package rest.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rest.user.UserRepository;
import rest.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = POST)
    @ResponseStatus(CREATED)
    public void addUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("Trying to register user {}", userDTO.getLogin());
        if (userRepository.findOneByLogin(userDTO.getLogin()).isPresent()) {
            log.warn("User with login {} already exists", userDTO.getLogin());
            throw new LoginAlreadyInUse();
        }
        userService.createUser(userDTO.getLogin(), userDTO.getPassword(), userDTO.getFirstName(), userDTO.getLastName());
    }

    @RequestMapping(value = "/authenticate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String isAuthenticated(HttpServletRequest request) {
        String remoteUser = request.getRemoteUser();
        log.debug("REST request to check if the current user is authenticated");
        return remoteUser;
    }
}
