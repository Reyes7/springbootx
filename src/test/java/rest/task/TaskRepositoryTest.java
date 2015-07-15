package rest.task;

import org.junit.Before;
import org.junit.Test;

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
        taskRepository.add(task);

        Task taskFromRepository = taskRepository.getById(0L).get();

        assertEquals(task, taskFromRepository);
    }

    @Test
    public void is_updated_task_in_repository() throws Exception {
        Task task = new Task(0L, "Task name", false);
        taskRepository.add(task);
        Task updateTask = new Task(0L, "New task name", true);

        taskRepository.update(0L,updateTask);

        assertEquals(updateTask,taskRepository.getById(0L).get());
    }

    @Test(expected = TaskNotFound.class)
    public void updated_not_existing_task_throw_exception() throws Exception {
        Task updateTask = new Task(0L, "New task name", true);

        taskRepository.update(10L,updateTask);
    }

    @Test
    public void deleted_task_is_no_longer_in_repository() throws Exception {
        // Arrange | Given
        taskRepository.add(new Task(0L, "Task name ", false));
        // Act     | When
        taskRepository.remove(0L);
        // Assert  | Then
        assertFalse(taskRepository.getById(0L).isPresent());
    }
}