package apps.nooterapp.services;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.data.relational.core.sql.When.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    private UserService userService;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private EmailSenderService mockEmailSenderService;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    void setUp() {
        userService = new UserService(mockUserRepository, mockPasswordEncoder, mockEmailSenderService);

    }

    @Test
    void testLoggedUser() {
        Principal principal = mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("Mitko");

        User user = new User();
        user.setUsername("Mitko");

        Mockito.when(mockUserRepository.getUserByUsername("Mitko")).thenReturn(Optional.of(user));
        User savedUser = userService.loggedUser(principal);

        Assertions.assertEquals("Mitko", savedUser.getUsername());
        verify(mockUserRepository).getUserByUsername("Mitko");

    }

    @Test
    void testChangeEmail() {
        Principal mockPrincipal = mock(Principal.class);
        User user = new User();
        user.setEmail("Mitko@abv.bg");
        Mockito.when(mockUserRepository.getUserByUsername(mockPrincipal.getName())).thenReturn(Optional.of(user));

        userService.changeEmail(mockPrincipal, user.getEmail());

        Assertions.assertEquals("Mitko@abv.bg", user.getEmail());
        verify(mockUserRepository).getUserByUsername(mockPrincipal.getName());
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        user.setUsername("Lazar");
        Mockito.when(mockUserRepository.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        User savedUser = userService.getUserByUsername("Lazar");
        Assertions.assertEquals(user.getUsername(), savedUser.getUsername());
        verify(mockUserRepository).getUserByUsername(user.getUsername());
    }


}
