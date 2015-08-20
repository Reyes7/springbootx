package rest.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.user.User;
import rest.user.UserService;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/tasks")
public class TaskController {

    private final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Task> getTasks(Principal principal) {
        String login = principal.getName();
        log.debug("getting tasks for user {}", login);
        return taskService.getTasksByUserLogin(login);
    }

    @RequestMapping(value = "{login}",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> addTask(@PathVariable String login, @RequestBody Task task) {
        User user = userService.getUserForLogin(login);
        if(user == null)
            return new ResponseEntity<Task>(new Task(), HttpStatus.NOT_FOUND);

        task.setUser(user);
        taskService.addTask(task);
        log.debug("added task '{}' for user {}", task.getTaskName(), login);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> updateTask(@PathVariable("id") int id) {
        taskService.updateTask(id);
        Task task = taskService.getTask(id);
        if(task == null)
            return new ResponseEntity<Task>(new Task(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable("id") int id) {
        taskService.removeTask(id);
    }
}
