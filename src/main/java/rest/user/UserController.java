package rest.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public Iterable<User> getUsers(){
        log.debug("get all users");
        return userService.getAllUsers();
    }//http://localhost:8080/users

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        log.debug("add user");
        String login = user.getLogin();
        User findedUser = userService.getUserForLogin(login);

        if (findedUser == null) {
            String password = user.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);
            userService.addUser(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/users",method = RequestMethod.PUT)
    public ResponseEntity<User> upadateUser(@RequestBody UserHelper userHelper){
        log.debug("update user");
        String login = userHelper.getLogin();
        String password = userHelper.getOldPassword();

        User user = userService.getUserForLogin(login);

        if(user == null)
            return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isDataValid = passwordEncoder.matches(password,user.getPassword());
        if(isDataValid){
            String newPasswordEncrypt = passwordEncoder.encode(userHelper.getNewPassword());
            userHelper.setNewPassword(newPasswordEncrypt);
            userService.updateUser(userHelper);
            User updatedUser = userService.getUserForLogin(login);
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/loggin",method = RequestMethod.POST)
    public ResponseEntity<User> logging(@RequestParam(value = "login") String login,
                                        @RequestParam(value = "password") String password){
        log.debug("logging");
        User user = userService.getUserForLogin(login);
        if(user == null)
            return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isDataValid = passwordEncoder.matches(password,user.getPassword());
        if(isDataValid){
            return new ResponseEntity<User>(user,HttpStatus.OK);
        }
        return new ResponseEntity<User>(new User(),HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #login)")
    @RequestMapping(value = "/user/{login}",method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable String login){
        log.debug("get user");
        User user = userService.getUserForLogin(login);
        if(user == null)
            return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #login)")
    @RequestMapping(value = "/user/{login}",method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String login){
        log.debug("delete user");
        User user = userService.getUserForLogin(login);
        userService.deleteUser(user.getId());
    }
}
