package rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import rest.user.UserRepository;

import java.util.Arrays;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.debug("Authenticating {}", login);
        return repository.findOneByLogin(login)
                .map(user -> new User(
                                user.getLogin(),
                                user.getPassword(),
                                Arrays.asList(new SimpleGrantedAuthority(AuthoritiesConstants.USER))
                        )
                )
                .orElseThrow(() -> new UsernameNotFoundException("User " + login + " was not found in the database"));
    }
}
