package ua.dmytrolutsyuk.phonecontactsapi.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private final HttpStatusCode errorCode = HttpStatus.FORBIDDEN;

    public UserAlreadyExistsException() {
        super("User with provided login or email already exists");
    }
}
