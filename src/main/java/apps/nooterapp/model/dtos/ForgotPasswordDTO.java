package apps.nooterapp.model.dtos;

public class ForgotPasswordDTO {
    private String username;
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
