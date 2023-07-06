package ua.dmytrolutsyuk.phonecontactsapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class ImageUploadException extends RuntimeException {
    private final HttpStatusCode errorCode = HttpStatus.NOT_FOUND;

    public ImageUploadException(String imageName) {
        super("Error uploading image " + imageName);
    }
}
