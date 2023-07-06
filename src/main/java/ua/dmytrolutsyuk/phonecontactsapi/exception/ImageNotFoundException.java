package ua.dmytrolutsyuk.phonecontactsapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class ImageNotFoundException extends RuntimeException {
    private final HttpStatusCode errorCode = HttpStatus.NOT_FOUND;

    public ImageNotFoundException() {
        super("Image not found");
    }
}