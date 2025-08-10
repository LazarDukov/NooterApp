package apps.nooterapp.model.dtos;

import apps.nooterapp.util.PasswordMatcherInterface;
import jakarta.validation.constraints.NotNull;

@PasswordMatcherInterface(password = "newPassword", confirmPassword = "newPasswordConfirm", message = "New password doesn't matches!")
public class ChangePasswordDTO {
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
    private String newPasswordConfirm;

    public ChangePasswordDTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public ChangePasswordDTO setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ChangePasswordDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public ChangePasswordDTO setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
        return this;
    }
}
