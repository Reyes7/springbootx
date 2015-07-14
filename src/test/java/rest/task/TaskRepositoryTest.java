package rest.task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class TaskRepositoryTest {

    private TaskRepository taskRepository;

    @Before
    public void setUp() throws Exception {
        taskRepository = new TaskRepository();
    }

    @Test
    public void newly_created_repository_is_empty() throws Exception {
        assertEquals(0, taskRepository.getAll().size());
    }

    @Test
    public void check_added_task_is_in_repository() throws Exception {
        Task task = new Task(0L, "Task name", false);

        Task taskFromRepository = taskRepository.add(task);

        assertNotEquals(0, taskRepository.getAll().size());
        assertEquals(task.getId(), taskFromRepository.getId());
        assertEquals(task.getTask(), taskFromRepository.getTask());
        assertEquals(task.isDone(), taskFromRepository.isDone());
    }

    @Test
    public void check_delete_task_works_well() throws Exception {
        Task task = new Task(0L, "Task name ", false);

        assertEquals(true, taskRepository.getAll().isEmpty());
        taskRepository.add(task);
        assertEquals(false, taskRepository.getAll().isEmpty());
        taskRepository.remove(0L);
        assertEquals(true, taskRepository.getAll().isEmpty());
    }

    @Test
    public void all_added_tasks_indexes_is_correct() throws Exception {
        for (int i = 0; i < 3; i++) {
            Task task = new Task((long) i, "Task name " + i, false);
            taskRepository.add(task);
        }

        assertEquals(3, taskRepository.getAll().size());
        taskRepository.remove(2);
        assertEquals(2, taskRepository.getAll().size());

        Task newTask = new Task((long) 4, "Task name " + 4, false);

        taskRepository.add(newTask);

        int size = taskRepository.getAll().size() - 1;

        Task taskfromRepository = taskRepository.getAll().get(size);

        assertEquals(newTask.getTask(), taskfromRepository.getTask());
        assertEquals(newTask.isDone(), taskfromRepository.isDone());
    }
}