package apps.nooterapp.model.dtos;

import apps.nooterapp.util.PasswordMatcherInterface;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Size(min = 6, max = 15, message = "Username input length should be between 6 and 15 letters!")
    private String username;

    @Email
    private String email;
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




