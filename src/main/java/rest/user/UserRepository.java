package rest.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private static Map<Long,User> users = new HashMap<Long,User>();

    public static Map<Long, User> getUsers() {
        return users;
    }
}
