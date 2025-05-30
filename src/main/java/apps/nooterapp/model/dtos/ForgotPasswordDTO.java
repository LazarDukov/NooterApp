package apps.nooterapp.model.dtos;

import apps.nooterapp.util.EmailValidationInterface;
import apps.nooterapp.util.UsernameAndEmailMatcherInterface;
import apps.nooterapp.util.UsernameValidationInterface;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@UsernameAndEmailMatcherInterface(username = "username", email = "email")
public class ForgotPasswordDTO {
    @NotNull

    private String username;
    @NotNull
    private String email;


    public ForgotPasswordDTO() {
    }



    public String getUsername() {
        return username;
    }

    public ForgotPasswordDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ForgotPasswordDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
