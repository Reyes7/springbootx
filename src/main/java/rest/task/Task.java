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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task1 = (Task) o;

        if (id != task1.id) return false;
        if (done != task1.done) return false;
        return !(task != null ? !task.equals(task1.task) : task1.task != null);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (task != null ? task.hashCode() : 0);
        result = 31 * result + (done ? 1 : 0);
        return result;
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
