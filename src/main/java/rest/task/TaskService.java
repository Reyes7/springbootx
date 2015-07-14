package rest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public Optional<Task> getTask(long id) {
        return taskRepository.getById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAll();
    }

    public Task addTask(Task task) {
        return taskRepository.add(task);
    }

    public Optional<Task> updateTask(Task task) {
        return Optional.ofNullable(taskRepository.update(task));
    }

    public void removeTask(long id) {
        taskRepository.remove(id);
    }

}
