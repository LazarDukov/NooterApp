package apps.nooterapp.model.entities;

import apps.nooterapp.model.enums.NoteType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table
@Entity(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private NoteType type;

    @Column
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Note() {
    }

    public Note(Long id, String title, String description, NoteType type, boolean active, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.active = active;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Note setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Note setDescription(String description) {
        this.description = description;
        return this;
    }

    public NoteType getType() {
        return type;
    }

    public Note setType(NoteType type) {
        this.type = type;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Note setActive(boolean active) {
        this.active = active;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Note setUser(User user) {
        this.user = user;
        return this;
    }
}
