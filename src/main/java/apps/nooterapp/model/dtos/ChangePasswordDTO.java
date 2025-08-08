package apps.nooterapp.model.dtos;

import jakarta.validation.constraints.NotNull;

public class ChangePasswordDTO {
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
    @NotNull
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
