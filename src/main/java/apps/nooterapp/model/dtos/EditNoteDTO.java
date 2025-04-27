package apps.nooterapp.model.dtos;

import apps.nooterapp.model.enums.NoteType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public class EditNoteDTO {
    private String title;
    private String description;

    @Enumerated
    private NoteType type;

    private boolean active;

    private LocalDateTime reminderTime;

    public EditNoteDTO() {
    }

    public EditNoteDTO(String title, String description, NoteType type, boolean active, LocalDateTime reminderTime) {
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

    public NoteType getType() {
        return type;
    }

    public EditNoteDTO setType(NoteType type) {
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
