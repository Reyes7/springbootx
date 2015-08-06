package rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    public User addUser(User user) {
        return repository.save(user);
    }

    public User getUserForLogin(String login){
        return repository.findByLogin(login);
    }

    public User getUserForId(int id){
        return repository.findById(id);
    }

    public void deleteUser(int id){
        repository.delete(id);
    }
}
