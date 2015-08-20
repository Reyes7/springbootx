package rest.task;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepository extends CrudRepository<Task,Integer>{

    @Query("SELECT t FROM Task t where t.taskName=:name")
    Iterable <Task> findByName(@Param("name") String name);

    @Query("SELECT t FROM Task t where t.user.login=:login")
    Iterable <Task> findByUserLogin(@Param("login") String login);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.done=:arg where t.id=:id")
    void updateTask(@Param("arg") Boolean arg,
                    @Param("id") int id);
}
