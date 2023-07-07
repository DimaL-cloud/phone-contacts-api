package ua.dmytrolutsyuk.phonecontactsapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ua.dmytrolutsyuk.phonecontactsapi.validation.validator.PhoneNumberValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "Invalid phone number format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}