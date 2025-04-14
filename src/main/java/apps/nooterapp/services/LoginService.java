package apps.nooterapp.services;

import apps.nooterapp.model.dtos.LoginDTO;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class LoginService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private Model model;


    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User loginUser(LoginDTO loginDTO) {
        Optional<User> optionalUser = userRepository.findUserByUsername(loginDTO.getUsername());
        if (optionalUser.isPresent()) {
            String userPassword = optionalUser.get().getPassword();
            String credentialPassword = loginDTO.getPassword();
            if (passwordEncoder.matches(credentialPassword, userPassword)) {
                return optionalUser.get();
            }
        }
        return null;
    }
}
