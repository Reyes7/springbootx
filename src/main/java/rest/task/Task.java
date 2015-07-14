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

    private Task(Builder builder) {
        this(
                builder.id,
                builder.task,
                builder.done
        );
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

    public Builder toBuilder() {
        return new Builder()
                .setId(id)
                .setTask(task)
                .setDone(done);
    }

    public static class Builder {

        private long id;
        private String task;
        private boolean done;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTask(String task) {
            this.task = task;
            return this;
        }

        public Builder setDone(boolean done) {
            this.done = done;
            return this;
        }

        public Task create() {
            return new Task(this);
        }
    }
}
