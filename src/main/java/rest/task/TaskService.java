package rest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public Task getTask(int id) {
        return taskRepository.findOne(id);
    }

    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

//    public Task updateTask(int id, Task task) {
//        return taskRepository.update(id, task);
//    }

    public void removeTask(int id) {
        taskRepository.delete(id);
    }

}
