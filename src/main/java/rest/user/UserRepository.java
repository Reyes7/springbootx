package rest.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer>{

    List <User> findByLastName(String lastName);
}
