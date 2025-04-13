package apps.nooterapp.services;

import apps.nooterapp.model.dtos.RegisterDTO;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private UserRepository userRepository;

    @Autowired
    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerNewUser(RegisterDTO registerDTO) {
        User newUser = new User();
        newUser.setUsername(registerDTO.getUsername());
        newUser.setPassword(registerDTO.getPassword());

        userRepository.save(newUser);
    }
}
