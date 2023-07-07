package ua.dmytrolutsyuk.phonecontactsapi.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.dmytrolutsyuk.phonecontactsapi.validation.Password;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.matches(PASSWORD_REGEX);
    }
}