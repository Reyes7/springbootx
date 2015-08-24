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
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
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

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> addTask(@RequestBody Task task, Principal principal) {
        String login = principal.getName();
        User user = userService.getUserForLogin(login);
        if (user == null)
            return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);

        task.setUser(user);
        taskService.addTask(task);
        log.debug("added task '{}' for user {}", task.getTaskName(), login);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(
            @PathVariable("id") int id,
            @RequestBody Task _task,
            Principal principal
    ) {
        String login = principal.getName();

        Optional<Task> oTask = taskService.getOneTask(id);
        if (oTask.isPresent()) {
            Task dbTask = oTask.get();
            if (dbTask.getUser().getLogin().equals(login)) {
                dbTask.setTaskName(_task.getTaskName());
                dbTask.setDone(_task.isDone());

                Task updatedTask = taskService.addTask(dbTask);
                return new ResponseEntity<>(updatedTask, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable("id") int id, Principal principal) {
        String login = principal.getName();
        log.debug("removing task {} owned by {}", id, login);

        Optional<Task> task = taskService.getOneTask(id);
        if (task.isPresent()) {
            if (task.get().getUser().getLogin().equals(login)) {
                taskService.removeTask(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
