package rest.database;

import rest.model.User;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private static Map<Long,User> users = new HashMap<Long,User>();

    public static Map<Long, User> getUsers() {
        return users;
    }
}
