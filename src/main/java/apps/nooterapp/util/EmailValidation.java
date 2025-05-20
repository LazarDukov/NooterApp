package apps.nooterapp.util;

import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailValidation implements ConstraintValidator<EmailValidationInterface, String> {

    private final UserRepository userRepository;

    @Autowired
    public EmailValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(EmailValidationInterface constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return true;
        } else {
            return false;
        }
    }
}
