package rest.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import rest.user.User;

import javax.persistence.*;

@Entity
@Table
public class Task {

    @Id
    @Generated(GenerationTime.INSERT)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String taskName;
    private boolean done;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

    public Task() {
    }

    public Task(String taskName, boolean done) {
        this.taskName = taskName;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Task copyTask(Task task){
        Task newTask = new Task();
        newTask.id = task.getId();
        newTask.taskName = task.getTaskName();
        newTask.done = task.done;
        newTask.user = task.user;

        return newTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        if (done != task.done) return false;
        return taskName.equals(task.taskName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + taskName.hashCode();
        result = 31 * result + (done ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", done=" + done +
                '}';
    }
}
