package ua.dmytrolutsyuk.phonecontactsapi.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.dmytrolutsyuk.phonecontactsapi.exception.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(value = {ContactNotFoundException.class})
    public ErrorResponse handleContactNotFoundException(ContactNotFoundException e) {
        return new ErrorResponseImpl(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = {ContactAlreadyExistsException.class})
    public ErrorResponse handleContactAlreadyExistsException(ContactAlreadyExistsException e) {
        return new ErrorResponseImpl(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        return new ErrorResponseImpl(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = {UserNotEnabledException.class})
    public ErrorResponse handleUserNotEnabledException(UserNotEnabledException e) {
        return new ErrorResponseImpl(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ErrorResponse handleProvidedEmailExistsException(UserAlreadyExistsException e) {
        return new ErrorResponseImpl(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = {ConfirmationTokenNotFoundException.class})
    public ErrorResponse handleConfirmationTokenNotFoundException(ConfirmationTokenNotFoundException e) {
        return new ErrorResponseImpl(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = {ImageUploadException.class})
    public ErrorResponse handleImageUploadException(ImageUploadException e) {
        return new ErrorResponseImpl(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = {ImageNotFoundException.class})
    public ErrorResponse handleImageNotFoundException(ImageNotFoundException e) {
        return new ErrorResponseImpl(e.getErrorCode(), e.getMessage());
    }
}