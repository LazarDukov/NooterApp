package apps.nooterapp.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class PasswordMatcher implements ConstraintValidator<PasswordMatcherInterface, Object> {
    private String password;
    private String confirmPassword;

    @Override
    public void initialize(PasswordMatcherInterface constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidator) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        Object passwordValue = beanWrapper.getPropertyValue(this.password);
        Object confirmPasswordValue = beanWrapper.getPropertyValue(this.confirmPassword);
        boolean isValid = passwordValue.equals(confirmPasswordValue);
        if (!isValid) {
            constraintValidator.disableDefaultConstraintViolation();
            constraintValidator.buildConstraintViolationWithTemplate("Passwords doesnt matches!")
                    .addPropertyNode(confirmPassword)
                    .addConstraintViolation();
        }
        return isValid;
    }
}
