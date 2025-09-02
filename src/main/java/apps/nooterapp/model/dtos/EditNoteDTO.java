package apps.nooterapp.model.dtos;

import apps.nooterapp.model.enums.RecordType;
import jakarta.persistence.Enumerated;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class EditNoteDTO {
    private String title;
    private String description;

    @Enumerated
    private RecordType type;

    private boolean active;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime reminderTime;

    public EditNoteDTO() {
    }

    public EditNoteDTO(String title, String description, RecordType type, boolean active, LocalDateTime reminderTime) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.active = active;
        this.reminderTime = reminderTime;
    }

    public String getTitle() {
        return title;
    }

    public EditNoteDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditNoteDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public RecordType getType() {
        return type;
    }

    public EditNoteDTO setType(RecordType type) {
        this.type = type;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public EditNoteDTO setActive(boolean active) {
        this.active = active;
        return this;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public EditNoteDTO setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
        return this;
    }
}
