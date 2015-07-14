package rest.task;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepository {

    private Map<Long, Task> tasks = new HashMap<Long, Task>();
    private long id;

    public Optional<Task> getById(long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public List<Task> getAll() {
        return new ArrayList<Task>(tasks.values());
    }

    public Task add(Task task) {
        Task.Builder builder = new Task.Builder();
        Task newTask = builder.getInstanceOfTask(id,task);
        tasks.put(id, newTask);

        id++;

        return newTask;
    }

    public Task update(Task task) {
        if (task.getId() <= 0) {
            return null;
        }
        tasks.put(task.getId(), task);
        return task;
    }

    public void remove(long id) {
        tasks.remove(id);
    }
}
