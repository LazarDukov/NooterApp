package apps.nooterapp.services;

import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.RecordType;
import apps.nooterapp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailsServiceTests {
    // TODO: implement all test for this and other services
    private final String EXISTING_USERNAME = "lazardukov";
    private final String EXISTING_EMAIL = "lazardukov@abv.bg";
    private final String EXISTING_PASSWORD = "123456";

    private ApplicationUserDetailsService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new ApplicationUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserFound() {
        User testUser = new User().setUsername(EXISTING_USERNAME).setEmail(EXISTING_EMAIL).setPassword(EXISTING_PASSWORD);
        Record testRecord = new Record().setTitle("taskOne").setDescription("Its important").setType(RecordType.NOTE).setUser(testUser);
        testUser.setNotes(List.of(testRecord));
        when(mockUserRepository.getUserByUsername(EXISTING_USERNAME)).thenReturn(Optional.of(testUser));
        UserDetails userDetails = toTest.loadUserByUsername(EXISTING_USERNAME);
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(EXISTING_USERNAME, userDetails.getUsername());
        Assertions.assertEquals(EXISTING_PASSWORD, userDetails.getPassword());
    }

    @Test
    void testRegisterNewUser() {

    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> {
                    toTest.loadUserByUsername("this username not exist!");
                });
    }
}
