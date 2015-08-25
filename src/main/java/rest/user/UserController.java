package rest.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getUsers() {
        log.debug("get all users");
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<User> upadateUser(@RequestBody UserHelper userHelper) {
        log.debug("update user");
        String login = userHelper.getLogin();
        String password = userHelper.getOldPassword();

        User user = userService.getUserForLogin(login);

        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isDataValid = passwordEncoder.matches(password, user.getPassword());
        if (isDataValid) {
            String newPasswordEncrypt = passwordEncoder.encode(userHelper.getNewPassword());
            userHelper.setNewPassword(newPasswordEncrypt);
            userService.updateUser(userHelper);
            User updatedUser = userService.getUserForLogin(login);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(Principal principal) {
        log.debug("get user");
        String login = principal.getName();
        User user = userService.getUserForLogin(login);
        if (user == null)
            return new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteUser(Principal principal) {
        log.debug("delete user");
        String login = principal.getName();
        User user = userService.getUserForLogin(login);
        userService.deleteUser(user.getId());
    }
}
