package apps.nooterapp.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = TaskReminderDateValidation.class)
public @interface TaskReminderDateValidationInterface {

    String message() default "You should choose time after now!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
