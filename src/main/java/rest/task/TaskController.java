package rest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.user.User;
import rest.user.UserService;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "{login}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Task> getTasksForLogin(@PathVariable String login) {
        return taskService.getTasksByUserLogin(login);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Task> getTasks() {
        return taskService.getAllTasks();
    }

    @RequestMapping(value = "{login}",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> addTask(@PathVariable String login, @RequestBody Task task) {
        User user = userService.getUserForLogin(login);
        task.setUser(user);

        taskService.addTask(task);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable("id") int id) {
        taskService.removeTask(id);
    }

}
