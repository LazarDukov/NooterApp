package apps.nooterapp.util;

import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class UsernameAndEmailMatcher implements ConstraintValidator<UsernameAndEmailMatcherInterface, Object> {
    private final UserRepository userRepository;
    private String username;
    private String email;

    @Autowired
    public UsernameAndEmailMatcher(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public void initialize(UsernameAndEmailMatcherInterface constraintAnnotation) {
        this.username = constraintAnnotation.username();
        this.email = constraintAnnotation.email();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
        Object username = beanWrapper.getPropertyValue(this.username);
        Object email = beanWrapper.getPropertyValue(this.email);
        boolean isValid = isValid(username, email);
        System.out.println(isValid);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username with this email doesn't exist!")
                    .addConstraintViolation();
        }
        return isValid;
    }

    private boolean isValid(Object username, Object email) {
        User user = userRepository.findUserByUsername(username.toString());
        if (user == null) {
            System.out.println("user not exist");
            return false;
        } else {
            System.out.println("I will equal two emails");
            return user.getEmail().equals(email.toString());
        }
    }


}
