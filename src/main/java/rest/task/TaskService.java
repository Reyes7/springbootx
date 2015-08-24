package rest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public Optional<Task> getOneTask(int id) {
        return taskRepository.findOneById(id);
    }

    public Iterable<Task> getTasksByUserLogin(String login){
        return taskRepository.findByUserLogin(login);
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public void removeTask(int id) {
        taskRepository.delete(id);
    }
}
