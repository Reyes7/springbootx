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

    public Iterable<Task> getTasksByName(String name){
        return taskRepository.findByName(name);
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

    public Task updateTask(int id){
        Task task = taskRepository.findOne(id);
        if(task==null) return null;

        Task newTask = Task.copyTask(task);
        if(task.isDone()) newTask.setDone(false);
        else newTask.setDone(true);
        taskRepository.delete(id);
        return taskRepository.save(newTask);
    }

}
