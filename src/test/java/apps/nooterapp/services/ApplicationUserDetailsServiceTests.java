package apps.nooterapp.services;

import apps.nooterapp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailsServiceTests {
    // TODO: implement all test for this and other services
    private ApplicationUserDetailsService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new ApplicationUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserFound() {

    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> { toTest.loadUserByUsername("not existent this username");
        });
    }
}
