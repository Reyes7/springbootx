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
        TaskBuilder taskBuilder = new TaskBuilder();

        Task taskCopy = taskBuilder.getCopyOfTask(tasks.get(id));
        return taskCopy;
    }

    public List<Task> getAll() {
        TaskBuilder taskBuilder = new TaskBuilder();

        ArrayList<Task> tasksValues = new ArrayList<Task>();

        for (Task task : tasks.values()) {
            tasksValues.add(taskBuilder.getCopyOfTask(task));
        }

        return tasksValues;
    }

    public Task add(Task task) {
        TaskBuilder taskBuilder = new TaskBuilder();
        Task newTask = taskBuilder.getInstanceOfTask(id,task);
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
