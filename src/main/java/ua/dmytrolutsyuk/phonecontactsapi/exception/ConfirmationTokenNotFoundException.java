package ua.dmytrolutsyuk.phonecontactsapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class ConfirmationTokenNotFoundException extends RuntimeException {
    private final HttpStatusCode errorCode = HttpStatus.NOT_FOUND;

    public ConfirmationTokenNotFoundException(String token) {
        super("Confirmation token " + token + " not found");
    }
}
