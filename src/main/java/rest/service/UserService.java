package rest.service;

import rest.database.Database;
import rest.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService {

    private Map<Long, User> users = Database.getUsers();

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
