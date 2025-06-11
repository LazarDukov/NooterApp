package apps.nooterapp.services;

import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.NoteType;
import apps.nooterapp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailsServiceTests {
    // TODO: implement all test for this and other services
    private final String EXISTING_USERNAME = "lazardukov";
    private final String EXISTING_EMAIL = "lazardukov@abv.bg";
    private final String EXISTING_PASSWORD = "123456";
    private final String NON_EXISTING_USERNAME = "lazardukov";
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
        Note testNote = new Note().setTitle("taskOne").setDescription("Its important").setType(NoteType.NOTE).setUser(testUser);
        testUser.setNotes(List.of(testNote));
        when(mockUserRepository.getUserByUsername(EXISTING_USERNAME)).thenReturn(Optional.of(testUser));
        UserDetails userDetails = toTest.loadUserByUsername(EXISTING_USERNAME);
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(EXISTING_USERNAME, userDetails.getUsername());
        Assertions.assertEquals(EXISTING_PASSWORD, userDetails.getPassword());
Assertions.assertEquals(1,testUser.getNotes().size());
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> {
                    toTest.loadUserByUsername("not existent this username");
                });
    }
}
