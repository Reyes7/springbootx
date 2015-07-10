package rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.model.User;
import rest.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value="/users")
public class UserController {

    private UserService userService = new UserService();

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers(){
        return userService.getAllUsers();
    }//http://localhost:8080/users

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }//  {"firstName": "New","lastName": "User"}
}
