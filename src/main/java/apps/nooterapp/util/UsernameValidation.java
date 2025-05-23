package apps.nooterapp.util;

import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;

public class UsernameValidation implements ConstraintValidator<UsernameValidationInterface, String> {
    private final UserRepository userRepository;

@Autowired
    public UsernameValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UsernameValidationInterface constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        User user = this.userRepository.findUserByUsername(username);
        if (user == null) {
            return true;
        } else {
            return false;
        }
    }
}
