package rest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> getTask(@PathVariable("id") Long id) {

        Task task = taskService.getTask(id);

        if (task == null) {
            return new ResponseEntity<Task>(task, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Task> addTaks(@RequestBody Task task) {
        taskService.addTask(task);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }//{"task" : "new task", "done" : true}

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
        task.setId(id);
        Task updatedTask = taskService.updateTask(task);
        if (updatedTask == null) {
            return new ResponseEntity<Task>(updatedTask, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable("id") Long id) {
        taskService.removeTask(id);
    }

}
