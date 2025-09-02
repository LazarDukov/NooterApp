package apps.nooterapp.model.dtos;

import apps.nooterapp.model.enums.RecordType;
import apps.nooterapp.util.TaskReminderDateValidationInterface;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@TaskReminderDateValidationInterface(message = "You should choose a date and it should be after now!")
public class AddRecordDTO {
    @NotNull
    @Size(min = 3, max = 15, message = "Title should be with length minimum 3 and maximum 15 letters!")
    private String title;
    @NotNull
    @Size(min = 3, max = 255, message = "Description should be with length minimum 3 and maximum 255 letters!")
    private String description;

    @Enumerated
    @NotNull
    private RecordType type;

    private boolean active;

    private LocalDateTime reminderTime;

    public AddRecordDTO() {
    }

    public AddRecordDTO(String title, String description, RecordType type, boolean active, LocalDateTime reminderTime) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.active = active;
        this.reminderTime = reminderTime;
    }


    public String getTitle() {
        return title;
    }

    public AddRecordDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddRecordDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public RecordType getType() {
        return type;
    }

    public AddRecordDTO setType(RecordType type) {
        this.type = type;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public AddRecordDTO setActive(boolean active) {
        this.active = active;
        return this;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public AddRecordDTO setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
        return this;
    }
}

