package apps.nooterapp.services;

import apps.nooterapp.model.dtos.ChangePasswordDTO;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        if (principal == null) {
            throw new NullPointerException("There is not logged user!");
        }
        return userRepository.getUserByUsername(principal.getName())
                .orElseThrow(() -> new NullPointerException("User with username" + principal.getName() + " not found!"));
    }

    public void changeEmail(Principal principal, String newEmail) {
        User loggedUser = loggedUser(principal);
        loggedUser.setEmail(newEmail);
        userRepository.save(loggedUser);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    public void sendNewPassword(String email, String newPassword) {
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));
        emailSenderService.sendNewPassword(user.getEmail(), newPassword);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public User getUserByRecord(Long id) {
        return userRepository.findUserByRecordsId(id).orElseThrow(() -> new UsernameNotFoundException("User of this note is not found!"));

    }

    public void changePassword(Principal principal, ChangePasswordDTO changePasswordDTO) {
        User user = loggedUser(principal);
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

}
