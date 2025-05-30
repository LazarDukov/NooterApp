package apps.nooterapp.services;

import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private EmailSenderService emailSenderService;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
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



    public void sendNewPassword(String email, String newPassword) {
        User user = userRepository.findUserByEmail(email);
        emailSenderService.sendNewPassword(user.getEmail(), newPassword);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
