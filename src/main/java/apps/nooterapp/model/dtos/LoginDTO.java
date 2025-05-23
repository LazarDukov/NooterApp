package apps.nooterapp.model.dtos;

import lombok.*;

public class LoginDTO {
    private String username;

    private String password;

    public LoginDTO() {
    }



    public String getUsername() {
        return username;
    }

    public LoginDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
