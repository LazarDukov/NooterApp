package apps.nooterapp.services;

import apps.nooterapp.model.dtos.RegisterDTO;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;



    @Autowired
    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUser(RegisterDTO registerDTO) {
        System.out.println("Im in register");
        User newUser = new User();
        newUser.setUsername(registerDTO.getUsername());
        System.out.println("register username: " + newUser.getUsername());
        newUser.setEmail(registerDTO.getEmail());
        System.out.println("register email: " + newUser.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        System.out.println("register service and new user filled");
        userRepository.save(newUser);
    }
}
