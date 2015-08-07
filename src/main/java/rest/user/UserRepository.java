package rest.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Integer>{

//    @Query("SELECT u FROM User u where u.login=:login")
    User findByLogin(/*@Param("login") */String login);

    List<User> findByLastName(String lastName);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.firstName=:firstName, u.lastName=:lastName," +
            "u.password=:password where u.login=:login")
    void updateUser(@Param("firstName") String firstName,
                    @Param("lastName") String lastName,
                    @Param("password") String password,
                    @Param("login") String login);
}
