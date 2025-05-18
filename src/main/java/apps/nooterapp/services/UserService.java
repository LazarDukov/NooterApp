package apps.nooterapp.services;

import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User loggedUser(Principal principal) {
        return userRepository.findUserByUsername(principal.getName());
    }

    public void changeEmail(Principal principal, String newEmail) {
        User loggedUser = loggedUser(principal);
        loggedUser.setEmail(newEmail);
        userRepository.save(loggedUser);
    }

    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

}
