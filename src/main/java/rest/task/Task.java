package rest.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {
    private final long id;
    private final String task;
    private final boolean done;

    @JsonCreator
    public Task(
            @JsonProperty("id")
            long id,
            @JsonProperty("task")
            String task,
            @JsonProperty("done")
            boolean done
    ) {
        this.id = id;
        this.task = task;
        this.done = done;
    }

    @JsonGetter
    public long getId() {
        return id;
    }

    @JsonGetter
    public String getTask() {
        return task;
    }

    @JsonGetter
    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", done=" + done +
                '}';
    }

    public static class Builder {

        public Builder() {
        }

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
}
