package apps.nooterapp.model.dtos;

import apps.nooterapp.model.enums.NoteType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public class AddNoteDTO {
    private String title;
    private String description;

    @Enumerated
    private NoteType type;

    private boolean active;

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
