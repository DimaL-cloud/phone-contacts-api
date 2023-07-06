package ua.dmytrolutsyuk.phonecontactsapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class EmailsAlreadyExistException extends RuntimeException {
    private final HttpStatusCode errorCode = HttpStatus.CONFLICT;

    public EmailsAlreadyExistException() {
        super("Contact with provided emails already exists");
    }
}