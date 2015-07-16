package rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void init(){
        //repository.save(new User("John", "Rambo"));
       // repository.save(new User("Johny", "Bravo"));
        System.out.println("init");
    }

    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    public User addUser(User user) {
        return repository.save(user);
    }
}
