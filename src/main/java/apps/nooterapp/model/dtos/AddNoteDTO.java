package apps.nooterapp.model.dtos;

import apps.nooterapp.model.enums.NoteType;
import apps.nooterapp.util.TaskReminderDateValidationInterface;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.aspectj.lang.annotation.Before;

import java.time.LocalDateTime;


public class AddNoteDTO {
    @NotNull
    @Size(min = 3, max = 15, message = "Title should be with length minimum 3 and maximum 15 letters!")
    private String title;
    @NotNull
    @Size(min = 3, max = 255,message = "Description should be with length minimum 3 and maximum 255 letters!")
    private String description;

    @Enumerated
    @NotNull
    private NoteType type;

    private boolean active;
    @NotNull(message = "It cannot be empty! You should choose a date!")
    @TaskReminderDateValidationInterface
    private LocalDateTime reminderTime;

    public AddNoteDTO() {
    }

    public AddNoteDTO(String title, String description, NoteType type, boolean active, LocalDateTime reminderTime) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.active = active;
        this.reminderTime = reminderTime;
    }


    public String getTitle() {
        return title;
    }

    public AddNoteDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddNoteDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public NoteType getType() {
        return type;
    }

    public AddNoteDTO setType(NoteType type) {
        this.type = type;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public AddNoteDTO setActive(boolean active) {
        this.active = active;
        return this;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public AddNoteDTO setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
        return this;
    }
}

