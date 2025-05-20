package apps.nooterapp.model.dtos;

import apps.nooterapp.util.EmailValidationInterface;
import apps.nooterapp.util.PasswordMatcherInterface;
import apps.nooterapp.util.UsernameValidationInterface;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@PasswordMatcherInterface(password = "password", confirmPassword = "confirmPassword")
public class RegisterDTO {
    @NotNull
    @Size(min = 3, max = 30, message = "Username input length should be between 3 and 30 letters!")
    @UsernameValidationInterface
    private String username;

    @Email(message = "This should be your valid email address!")
    @NotNull
    @Size(min = 3, max = 40, message = "Email size should be between 3 and 40 letters!")
    @EmailValidationInterface
    private String email;
    @NotNull
    @Size(min = 6, max = 30, message = "Password input length should be between 6 and 30 symbols!")
    private String password;

    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public RegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public RegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}




