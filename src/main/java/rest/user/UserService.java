package rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void init(){

    }

    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    public User addUser(User user) {
        return repository.save(user);
    }
}
