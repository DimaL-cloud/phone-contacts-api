package ua.dmytrolutsyuk.phonecontactsapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final HttpStatusCode errorCode = HttpStatus.NOT_FOUND;

    public UserNotFoundException() {
        super("User not found with provided credentials");
    }
}
