package apps.nooterapp.services;

import apps.nooterapp.model.dtos.RegisterDTO;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTests {
    private RegisterService registerService;
    private final String USERNAME = "LazarDukov";
    private final String PASSWORD = "123456";
    private final String EMAIL = "LazarDukov@abv.bg";

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    void setUp() {
        registerService = new RegisterService(mockUserRepository, mockPasswordEncoder);
    }

    @Test
    void testRegisterNewUser() {
        String encodedPassword = "encoded_password";
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(USERNAME);
        registerDTO.setPassword(PASSWORD);
        registerDTO.setEmail(EMAIL);
        when(mockPasswordEncoder.encode(registerDTO.getPassword()))
                .thenReturn(encodedPassword);
        registerService.registerNewUser(registerDTO);
        verify(mockUserRepository).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();
        Assertions.assertEquals(registerDTO.getUsername(), savedUser.getUsername());
        Assertions.assertEquals(registerDTO.getEmail(), savedUser.getEmail());
        Assertions.assertEquals(encodedPassword, savedUser.getPassword());
    }
}
