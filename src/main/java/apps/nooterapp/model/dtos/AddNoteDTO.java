package apps.nooterapp.model.dtos;

import apps.nooterapp.model.enums.NoteType;

public class AddNoteDTO {
    private String title;
    private String description;

    private String type;

    private boolean active;

    public AddNoteDTO() {
    }

    public AddNoteDTO(String title, String description, String type, boolean active) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.active = active;
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

    public String getType() {
        return type;
    }

    public AddNoteDTO setType(String type) {
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
}
