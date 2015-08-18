package rest.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserForLogin(String login){
        return userRepository.findByLogin(login);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(UserHelper userHelper){
        userRepository.updateUser(userHelper.getFirstName(), userHelper.getLastName(),
                              userHelper.getNewPassword(), userHelper.getLogin());
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(int id){
        userRepository.delete(id);
    }

    public User createUser(String login, String password, String firstName, String lastName) {
        log.debug("Creating user {}", login);
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setLogin(login);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }
}
