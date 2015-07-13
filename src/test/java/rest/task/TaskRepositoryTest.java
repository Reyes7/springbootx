package rest.task;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskRepositoryTest {

    @Test
    public void newly_created_repository_is_empty() throws Exception {
        TaskRepository taskRepository = new TaskRepository();
        assertEquals(0,taskRepository.getAll().size());
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskRepository taskRepository = new TaskRepository();

        Task task = new Task();
        task.setTask("Name");
        task.setDone(false);

        assertEquals(true, taskRepository.getAll().isEmpty());
        assertEquals(task, taskRepository.add(task));
        assertEquals(false, taskRepository.getAll().isEmpty());
        assertEquals(1,taskRepository.getById(1L).getId());
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskRepository taskRepository = new TaskRepository();

        Task task = new Task();
        task.setTask("Name");
        task.setDone(false);

        taskRepository.add(task);

        Task updatedTask = new Task(1L,"Updated Task",true);

        assertEquals(false, taskRepository.getAll().isEmpty());
        assertEquals(task, taskRepository.update(task));
    }

    @Test
    public void testDeleteTask() throws Exception {
        TaskRepository taskRepository = new TaskRepository();

        Task task = new Task();
        task.setTask("Name");
        task.setDone(false);

        assertEquals(true, taskRepository.getAll().isEmpty());
        taskRepository.add(task);
        assertEquals(false, taskRepository.getAll().isEmpty());
        taskRepository.remove(1L);
        assertEquals(true, taskRepository.getAll().isEmpty());
    }
}