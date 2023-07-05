package ua.dmytrolutsyuk.phonecontactsapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class ContactAlreadyExistsException extends RuntimeException {
    private final HttpStatusCode errorCode = HttpStatus.CONFLICT;

    public ContactAlreadyExistsException(String name) {
        super("Contact already exists with name " + name);
    }
}