package rest.task;

public class Task {
    private final long id;
    private final String task;
    private final boolean done;

    public Task(long id, String task, boolean done) {
        this.id = id;
        this.task = task;
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

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
}
