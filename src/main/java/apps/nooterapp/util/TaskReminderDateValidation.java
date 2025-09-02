package apps.nooterapp.util;

import apps.nooterapp.model.dtos.AddRecordDTO;
import apps.nooterapp.model.enums.RecordType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class TaskReminderDateValidation implements ConstraintValidator<TaskReminderDateValidationInterface, AddRecordDTO> {


    @Override
    public void initialize(TaskReminderDateValidationInterface constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AddRecordDTO addRecordDTO, ConstraintValidatorContext context) {
        if (addRecordDTO.getType().equals(RecordType.NOTE)) {
            return true;
        }

        if (addRecordDTO.getReminderTime() == null || !addRecordDTO.getReminderTime().isAfter(LocalDateTime.now())) {
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
