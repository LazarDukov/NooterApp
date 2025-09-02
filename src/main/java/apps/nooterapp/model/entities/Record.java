package apps.nooterapp.model.entities;

import apps.nooterapp.model.enums.RecordType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table
@Entity(name = "notes")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private RecordType type;

    @Column
    private boolean active;

    @Column
    private LocalDateTime reminderTime;

    @Column
    private LocalDateTime dateCreated;



    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private User user;

    public Record() {
    }

    public Record(Long id, String title, String description, RecordType type, boolean active, LocalDateTime reminderTime, LocalDateTime dateCreated, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.active = active;
        this.user = user;
        this.reminderTime = reminderTime;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public Record setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Record setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Record setDescription(String description) {
        this.description = description;
        return this;
    }

    public RecordType getType() {
        return type;
    }

    public Record setType(RecordType type) {
        this.type = type;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Record setActive(boolean active) {
        this.active = active;
        return this;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public Record setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
        return this;
    }
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public Record setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
    public User getUser() {
        return user;
    }

    public Record setUser(User user) {
        this.user = user;
        return this;
    }
}
