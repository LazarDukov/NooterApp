package apps.nooterapp.services;

import apps.nooterapp.model.dtos.AddNoteDTO;
import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.NoteType;
import apps.nooterapp.repositories.NoteRepository;
import apps.nooterapp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTests {
    private NoteService noteService;
    private AddNoteDTO addNoteDTO;
    private Note testNote;
    private User testUser;
    private final String TITLE = "Most important task today";
    private final String DESCRIPTION = "I should run today!";
    @Mock
    private NoteRepository mockNoteRepository;
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserService mockUserService;


    @Captor
    private ArgumentCaptor<Note> noteArgumentCaptor;

    @BeforeEach
    void setUp() {
        noteService = new NoteService(mockUserRepository, mockNoteRepository, mockUserService);
        addNoteDTO = new AddNoteDTO();
        addNoteDTO.setTitle(TITLE);
        addNoteDTO.setDescription(DESCRIPTION);
        addNoteDTO.setActive(true);
        addNoteDTO.setType(NoteType.NOTE);
        testNote = new Note(1L, addNoteDTO.getTitle(), addNoteDTO.getDescription(), addNoteDTO.getType(), addNoteDTO.isActive(), null, null);
        testNote.setId(1L);
        testNote.setTitle("TEST TITLE");
        testNote.setDescription("TEST DESCRIPTION");
        testNote.setType(NoteType.NOTE);
        testNote.setActive(true);
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("test user");
        testUser.setEmail("test email");
        testUser.setPassword("123456");
        testUser.setNotes(new ArrayList<>());
        testUser.getNotes().add(testNote);
    }

    @Test
    void testAddNote() {
        Principal mockPrincipal = mock(Principal.class);
        User mockUser = new User();
        mockUser.setNotes(new ArrayList<>());
        when(mockUserService.loggedUser(mockPrincipal)).thenReturn(mockUser);
        noteService.addNote(mockPrincipal, addNoteDTO);
        verify(mockNoteRepository).save(noteArgumentCaptor.capture());
        Note savedNote = noteArgumentCaptor.getValue();
        Assertions.assertEquals(addNoteDTO.getTitle(), savedNote.getTitle());
        Assertions.assertEquals(addNoteDTO.getType(), savedNote.getType());
        Assertions.assertEquals(addNoteDTO.getDescription(), savedNote.getDescription());
        Assertions.assertEquals(addNoteDTO.getTitle(), mockUser.getNotes().get(0).getTitle());
        Assertions.assertEquals(1, mockUser.getNotes().size());
        Assertions.assertNotNull(addNoteDTO);
        // Assertions.assertEquals(1, savedNote.getId());
    }

    @Test
    void archiveNoteOrTask() {

    }

}
