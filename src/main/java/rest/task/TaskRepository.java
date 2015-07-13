package rest.task;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskRepository {

    private Map<Long, Task> tasks = new HashMap<Long, Task>();
    private long id;

    public Task getById(long id) {
        Map<Long, Task> copyOfTasks = new HashMap<Long, Task>();
        for (Map.Entry<Long, Task> taskEntry : tasks.entrySet()) {
            long key = taskEntry.getKey();
            Task task = new Task();

            task.setId(taskEntry.getValue().getId());
            task.setTask(taskEntry.getValue().getTask());
            task.setDone(taskEntry.getValue().isDone());

            copyOfTasks.put(key, task);
        }

        Task taskCopy = copyOfTasks.get(id);
        return taskCopy;
    }

    public List<Task> getAll() {
        Map<Long, Task> copyOfTasks = new HashMap<Long, Task>();
        for (Map.Entry<Long, Task> taskEntry : tasks.entrySet()) {
            long key = taskEntry.getKey();
            Task task = new Task();

            task.setId(taskEntry.getValue().getId());
            task.setTask(taskEntry.getValue().getTask());
            task.setDone(taskEntry.getValue().isDone());

            copyOfTasks.put(key, task);
        }

        return new ArrayList<Task>(copyOfTasks.values());
    }

    public Task add(Task task) {
        task.setId(id);
        id++;

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
