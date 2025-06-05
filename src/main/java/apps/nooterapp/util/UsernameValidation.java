package apps.nooterapp.util;

import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import apps.nooterapp.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

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
        Optional<User> user = userRepository.getUserByEmail(username);
        if (user.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
