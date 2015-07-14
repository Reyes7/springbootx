package rest.task;

public class TaskBuilder {

    public Task getInstanceOfTask(long id, String task, boolean done){
        return new Task(id,task,done);
    }

    public Task getInstanceOfTask(long id, Task task){
        String taskName = task.getTask();
        boolean done  = task.isDone();

        return new Task(id,taskName,done);
    }

    public Task getCopyOfTask(Task task){
        long id = task.getId();
        String taskName = task.getTask();
        boolean done  = task.isDone();

        return new Task(id,taskName,done);
    }
}
