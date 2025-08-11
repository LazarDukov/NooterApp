package apps.nooterapp.util;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Constraint(validatedBy = OldPasswordValidator.class)
public @interface OldPasswordValidatorInterface {
    String oldPassword();

    String message() default "Old password doesn't match for this username!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
