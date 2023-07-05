package ua.dmytrolutsyuk.phonecontactsapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class UserNotEnabledException extends RuntimeException {
    private final HttpStatusCode errorCode = HttpStatus.FORBIDDEN;

    public UserNotEnabledException() {
        super("User is not enabled, please check your email");
    }
}
