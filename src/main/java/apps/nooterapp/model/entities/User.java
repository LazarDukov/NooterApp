package apps.nooterapp.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @OneToMany
    private List<Note> notes;


    public User() {
    }

    public User(Long id, String username, String password, List<Note> notes) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public User setNotes(List<Note> notes) {
        this.notes = notes;
        return this;
    }
}
