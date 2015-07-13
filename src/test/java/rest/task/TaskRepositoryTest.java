package rest.task;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskRepositoryTest {

    @Test
    public void newly_created_repository_is_empty() throws Exception {
        TaskRepository taskRepository = new TaskRepository();
        assertEquals(0, taskRepository.getAll().size());
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
        assertEquals(0, taskRepository.getById(0L).getId());
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
        taskRepository.remove(0L);
        assertEquals(true, taskRepository.getAll().isEmpty());
    }

    @Test
    public void testRepositorySize() throws Exception {
        TaskRepository taskRepository = new TaskRepository();

        for (int i = 0; i < 3; i++) {
            Task task = new Task();
            task.setTask("Name: " + i);
            task.setDone(false);

            taskRepository.add(task);
        }


        assertEquals(3, taskRepository.getAll().size());
        taskRepository.remove(2);
        assertEquals(2, taskRepository.getAll().size());

        Task task = new Task();
        task.setTask("Name: " + 4);
        task.setDone(false);

        taskRepository.add(task);

        int size = taskRepository.getAll().size() - 1;

        Task taskfromRepository = taskRepository.getAll().get(size);

        assertEquals(task.getTask(), taskfromRepository.getTask());
        assertEquals(task.isDone(), taskfromRepository.isDone());
    }
}