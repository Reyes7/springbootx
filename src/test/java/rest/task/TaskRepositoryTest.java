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
    public void check_added_task_is_in_repository() throws Exception {
        TaskRepository taskRepository = new TaskRepository();

        Task.Builder builder = new Task.Builder();
        Task task = new Task(0L, "Task name", false);

        Task taskFromRepository = taskRepository.add(task);

        assertNotEquals(0, taskRepository.getAll().size());
        assertEquals(task.getId(), taskFromRepository.getId());
        assertEquals(task.getTask(), taskFromRepository.getTask());
        assertEquals(task.isDone(), taskFromRepository.isDone());
    }

    @Test
    public void force_update_without_rest_not_working() throws Exception {
        TaskRepository taskRepository = new TaskRepository();
        Task.Builder builder = new Task.Builder();

        for (int i = 0; i < 3; i++) {
            Task task = new Task((long) i, "Task name " + i, false);
            taskRepository.add(task);

            Task updtatedTask = new Task((long) i, "New ask name " + i, true);

            assertNotEquals(updtatedTask, taskRepository.getById(i));
        }
    }

    @Test
    public void check_delete_task_works_well() throws Exception {
        TaskRepository taskRepository = new TaskRepository();
        Task.Builder builder = new Task.Builder();

        Task task = new Task(0L, "Task name ", false);

        assertEquals(true, taskRepository.getAll().isEmpty());
        taskRepository.add(task);
        assertEquals(false, taskRepository.getAll().isEmpty());
        taskRepository.remove(0L);
        assertEquals(true, taskRepository.getAll().isEmpty());
    }

    @Test
    public void all_added_tasks_indexes_is_correct() throws Exception {
        TaskRepository taskRepository = new TaskRepository();
        Task.Builder builder = new Task.Builder();

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