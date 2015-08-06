package rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User getUserForLogin(String login){
        return repository.findByLogin(login);
    }

    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    public void updateUser(UserHelper userHelper){
        repository.updateUser(userHelper.getFirstName(),userHelper.getLastName(),
                              userHelper.getNewPassword(), userHelper.getLogin());
    }

    public User addUser(User user) {
        return repository.save(user);
    }

    public void deleteUser(int id){
        repository.delete(id);
    }
}
