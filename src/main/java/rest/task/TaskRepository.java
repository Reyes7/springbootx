package rest.task;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TaskRepository {

    private static Map<Long,Task> tasks = new HashMap<Long,Task>();

    public static Map<Long, Task> getTasks() {
        return tasks;
    }
}
