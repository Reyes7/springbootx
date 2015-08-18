package rest.account;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class LoginAlreadyInUse extends RuntimeException {
    public LoginAlreadyInUse() {
        super("Login already in use");
    }
}
