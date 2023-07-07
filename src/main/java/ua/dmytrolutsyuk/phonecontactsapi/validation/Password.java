package ua.dmytrolutsyuk.phonecontactsapi.validation;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import ua.dmytrolutsyuk.phonecontactsapi.validation.validator.PasswordValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Password must contain minimum eight characters, at least one letter and one number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}