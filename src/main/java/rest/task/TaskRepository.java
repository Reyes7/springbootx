package rest.task;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskRepository {

    private static Map<Long,Task> tasks = new HashMap<Long,Task>();

    public static Map<Long, Task> getTasks() {
        return tasks;
    }

    public Task getById(long id) {
        Task task = tasks.get(id);
        return task;
    }

    public List<Task> getAll() {
        return new ArrayList<Task>(tasks.values());
    }

    public Task add(Task task) {
        task.setId(tasks.size() + 1);
        tasks.put(task.getId(), task);
        return task;
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
