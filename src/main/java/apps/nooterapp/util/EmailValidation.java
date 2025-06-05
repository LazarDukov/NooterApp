package apps.nooterapp.util;

import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import apps.nooterapp.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

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
        System.out.println("Is valid email 1");
        Optional<User> user = userRepository.getUserByEmail(email);
        System.out.println("check user in isValid");
        if (user.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
