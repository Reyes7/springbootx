package rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*
@Service
public class UserService {
    @Autowired
    UserRepository repository;

    private Map<Long, User> users = UserRepository.getUsers();

    public UserService(){
        users.put(1L, new User(1L,"John", "Rambo"));
        users.put(2L, new User(2L,"Johny", "Bravo"));
    }

    public List<User> getAllUsers() {
        return new ArrayList<User>(users.values());
    }

    public User addUser(User user) {
        user.setId(users.size() + 1);
        users.put(user.getId(), user);
        return user;
    }

}
*/