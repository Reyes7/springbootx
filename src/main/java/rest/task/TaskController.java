package rest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Task getTask(@PathVariable("id") Long id) {
        Optional<Task> task = taskService.getTask(id);
        return task.orElseThrow(TaskNotFound::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }//{"task" : "new task", "done" : true}

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Task updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
        TaskBuilder taskBuilder = new TaskBuilder();
        task = taskBuilder.getInstanceOfTask(id,task);

        Optional<Task> updatedTask = taskService.updateTask(task);
        return updatedTask.orElseThrow(TaskNotFound::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable("id") Long id) {
        taskService.removeTask(id);
    }

}
