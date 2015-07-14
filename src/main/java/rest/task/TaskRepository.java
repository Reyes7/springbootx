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
        Task newTask = task.toBuilder().setId(id).create();
        tasks.put(id, newTask);

        id++;

        return newTask;
    }

    public Task update(Long id, Task task) {
        Task oldTask = tasks.get(id);
        if (oldTask == null)
            throw new TaskNotFound();
        Task updatedTask = oldTask.toBuilder()
                .setTask(task.getTask())
                .setDone(task.isDone())
                .create();
        tasks.put(id, updatedTask);
        return updatedTask;
    }

    public void remove(long id) {
        tasks.remove(id);
    }
}
