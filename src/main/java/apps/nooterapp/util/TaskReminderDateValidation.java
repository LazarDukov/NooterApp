package apps.nooterapp.util;

import apps.nooterapp.model.dtos.AddNoteDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class TaskReminderDateValidation implements ConstraintValidator<TaskReminderDateValidationInterface, LocalDateTime> {

    @Override
    public void initialize(TaskReminderDateValidationInterface constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext context) {
        if (localDateTime.isAfter(LocalDateTime.now())) {
            return true;
        } else {
            return false;
        }
    }
}
