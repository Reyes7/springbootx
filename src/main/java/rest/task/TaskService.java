package rest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    private Map<Long, Task> tasks = TaskRepository.getTasks();

    public TaskService(){
        tasks.put(1L, new Task(1L,"Test task",false));
    }

    public Task getTask(long id) {
        Task task = tasks.get(id);
        if(task == null ){
            return null;
        }
        return task;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<Task>(tasks.values());
    }

    public Task addTask(Task task) {
        task.setId(tasks.size() + 1);
        tasks.put(task.getId(), task);
        return task;
    }

    public Task updateTask(Task task) {
        if (task.getId() <= 0) {
            return null;
        }
        tasks.put(task.getId(), task);
        return task;
    }

    public Task removeTask(long id) {
        return tasks.remove(id);
    }
}
