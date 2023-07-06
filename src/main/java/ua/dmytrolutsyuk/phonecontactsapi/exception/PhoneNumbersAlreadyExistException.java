package ua.dmytrolutsyuk.phonecontactsapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class PhoneNumbersAlreadyExistException extends RuntimeException {
    private final HttpStatusCode errorCode = HttpStatus.CONFLICT;

    public PhoneNumbersAlreadyExistException() {
        super("Contact with provided phone numbers already exists");
    }
}