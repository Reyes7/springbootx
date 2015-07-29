package rest.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {
    private final long id;
    private final String taskName;
    private final boolean done;

    @JsonCreator
    public Task(
            @JsonProperty("id")
            long id,
            @JsonProperty("taskName")
            String taskName,
            @JsonProperty("done")
            boolean done
    ) {
        this.id = id;
        this.taskName = taskName;
        this.done = done;
    }

    private Task(Builder builder) {
        this(
                builder.id,
                builder.taskName,
                builder.done
        );
    }

    @JsonGetter
    public long getId() {
        return id;
    }

    @JsonGetter
    public String getTaskName() {
        return taskName;
    }

    @JsonGetter
    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
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
        return !(taskName != null ? !taskName.equals(task1.taskName) : task1.taskName != null);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (done ? 1 : 0);
        return result;
    }

    public Builder toBuilder() {
        return new Builder()
                .setId(id)
                .setTaskName(taskName)
                .setDone(done);
    }

    public static class Builder {

        private long id;
        private String taskName;
        private boolean done;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTaskName(String taskName) {
            this.taskName = taskName;
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
