package rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public Iterable<User> getUsers(){
        return userService.getAllUsers();
    }//http://localhost:8080/users

    @RequestMapping(value="/users", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user){
        String password = user.getPassword();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        user.setPassword(hashedPassword);

        userService.addUser(user);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }//  {"firstName": "New","lastName": "User"}

    @RequestMapping(value = "/loggin",method = RequestMethod.POST)
    public ResponseEntity<User> logging(@RequestParam(value = "login") String login,
                        @RequestParam(value = "password") String password){
        User user = userService.getUserForLogin(login);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isDataValid = passwordEncoder.matches(password,user.getPassword());

        if(isDataValid){
            return new ResponseEntity<User>(user,HttpStatus.OK);
        }
        return new ResponseEntity<User>(new User(),HttpStatus.NOT_FOUND);
    }
}
