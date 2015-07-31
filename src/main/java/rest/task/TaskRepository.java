package rest.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends CrudRepository<Task,Integer>{

    @Query("SELECT t FROM Task t where t.taskName=:name")
    public Task findByName(@Param("name") String name);
}
