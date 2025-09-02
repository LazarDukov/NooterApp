package apps.nooterapp.model.entities;

import jakarta.persistence.*;

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
    private String email;
    @Column
    private String password;

    @Column
    private String registerDate;



    @OneToMany(fetch = FetchType.EAGER)
    private List<Record> records;


    public User() {
    }

    public User(Long id, String username, String email, String password, String registerDate, List<Record> records) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.registerDate = registerDate;
        this.records = records;
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

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRegisterDate() {
        return registerDate;
    }
    public User setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
        return this;
    }



    public List<Record> getNotes() {
        return records;
    }

    public User setNotes(List<Record> records) {
        this.records = records;
        return this;
    }
}
