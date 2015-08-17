package rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import rest.user.User;
import rest.user.UserService;

/**
 * Created by Reyes on 2015-08-16.
 */

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.getUserForLogin(login);
        if(user == null) throw new UsernameNotFoundException("User not found");
        else return new AccountUserDetails(user);
    }
}
