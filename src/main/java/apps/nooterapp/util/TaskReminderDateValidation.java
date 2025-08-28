package apps.nooterapp.util;

import apps.nooterapp.model.dtos.AddNoteDTO;
import apps.nooterapp.model.enums.NoteType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class TaskReminderDateValidation implements ConstraintValidator<TaskReminderDateValidationInterface, AddNoteDTO> {


    @Override
    public void initialize(TaskReminderDateValidationInterface constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AddNoteDTO addNoteDTO, ConstraintValidatorContext context) {
        if (addNoteDTO.getType().equals(NoteType.NOTE)) {
            return true;
        }

        if (addNoteDTO.getReminderTime() == null || !addNoteDTO.getReminderTime().isAfter(LocalDateTime.now())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("reminderTime")
                    .addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
