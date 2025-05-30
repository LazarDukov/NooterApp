package apps.nooterapp.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UsernameAndEmailMatcher.class)
public @interface UsernameAndEmailMatcherInterface {
    String username();
    String email();

    String message() default "Username with this email doesn't exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}