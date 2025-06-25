package apps.nooterapp.services;

import apps.nooterapp.model.dtos.AddNoteDTO;
import apps.nooterapp.model.dtos.EditNoteDTO;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTests {
    private NoteService noteService;
    private AddNoteDTO addNoteDTO;
    private Note testNote;
    private User testUser;
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

        testNote = new Note();
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
        addNoteDTO = new AddNoteDTO();
        addNoteDTO.setTitle("Most important task today");
        addNoteDTO.setDescription("I should run today!");
        addNoteDTO.setActive(true);
        addNoteDTO.setType(NoteType.NOTE);
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
    void testArchiveNoteOrTask() {
        when(mockNoteRepository.findNoteById(testNote.getId())).thenReturn(testNote);
        noteService.archiveNoteOrTask(testNote.getId());
        verify(mockNoteRepository).save(noteArgumentCaptor.capture());
        Note savedNote = noteArgumentCaptor.getValue();
        Assertions.assertFalse(savedNote.isActive(), "Note should be archived");
    }

    @Test
    void testViewNoteOrTask() {
        when(mockNoteRepository.findNoteById(testNote.getId())).thenReturn(testNote);
        Note result = noteService.viewNoteOrTask(testNote.getId());
        Assertions.assertSame(testNote, result);
    }

    @Test
    void testEditNoteOrTask() {
        when(mockNoteRepository.findNoteById(testNote.getId())).thenReturn(testNote);
        EditNoteDTO editNoteDTO = new EditNoteDTO();
        editNoteDTO.setTitle("EDIT TITLE");
        editNoteDTO.setDescription("EDIT DESCRIPTION");
        editNoteDTO.setType(NoteType.NOTE);
        editNoteDTO.setActive(true);
        noteService.editNoteOrTask(testNote.getId(), editNoteDTO);
        verify(mockNoteRepository).save(noteArgumentCaptor.capture());
        Note savedNote = noteArgumentCaptor.getValue();
        Assertions.assertEquals(editNoteDTO.getTitle(), savedNote.getTitle());
        Assertions.assertEquals(editNoteDTO.getDescription(), savedNote.getDescription());
        Assertions.assertEquals(editNoteDTO.getType(), savedNote.getType());
        Assertions.assertTrue(savedNote.isActive(), "Yes, this is active!");
    }

    @Test
    void testFindAllActiveTasks() {
        Note note1 = new Note();
        note1.setTitle("TEST TASK 1");
        note1.setDescription("I should run today!");
        note1.setActive(true);
        note1.setType(NoteType.TASK);
        note1.setReminderTime(LocalDateTime.parse("2035-12-03T10:30:30"));

        Note note2 = new Note();
        note2.setTitle("TEST TASK 2");
        note2.setDescription("You shouldnt run today!");
        note2.setActive(true);
        note2.setType(NoteType.TASK);
        note2.setReminderTime(LocalDateTime.parse("2035-12-03T10:15:30"));

        Note note3 = new Note();
        note3.setTitle("TEST TASK 3");
        note3.setDescription("You should run today!");
        note3.setActive(true);
        note3.setType(NoteType.NOTE);
        note3.setReminderTime(LocalDateTime.parse("2045-12-03T10:35:30"));
        List<Note> notes = List.of(note1, note2);

        when(mockNoteRepository.findAllByTypeAndActiveOrderByReminderTime(NoteType.TASK, true)).thenReturn(notes);
        List<Note> savedNotes = noteService.findAllActiveTasks();

        Assertions.assertEquals(savedNotes.size(), notes.size());
        Assertions.assertTrue(savedNotes.stream().allMatch(n -> n.getType() == NoteType.TASK));
        Assertions.assertTrue(savedNotes.stream().allMatch(Note::isActive));
    }

    @Test
    void testDeleteNote() {
        Long noteId = 1L;

        Note testNote = new Note();
        testNote.setId(noteId);
        testNote.setTitle("Test Note");

        User testUser = new User();
        testUser.setId(10L);
        testUser.setNotes(new ArrayList<>(List.of(testNote)));

        when(mockNoteRepository.findNoteById(noteId)).thenReturn(testNote);
        when(mockUserService.getUserByNote(noteId)).thenReturn(testUser);

        // Act
        noteService.deleteNote(noteId);

        // Assert
        Assertions.assertFalse(testUser.getNotes().contains(testNote), "Note should be removed from user's notes");
        verify(mockUserRepository).save(testUser);
        verify(mockNoteRepository).delete(testNote);

    }

    @Test
    void testDeleteArchived() {
        Principal mockPrincipal = mock(Principal.class);
        Note testNote = new Note();
        Note testNote2 = new Note();
        User testUser = new User();
        testUser.setNotes(new ArrayList<>(List.of(testNote, testNote2)));
        when(mockUserService.loggedUser(mockPrincipal)).thenReturn(testUser);
        noteService.deleteArchived(mockPrincipal);
        Assertions.assertFalse(testUser.getNotes().size() > 0, "All notes should be removed.");
        verify(mockUserRepository).save(testUser);
        verify(mockNoteRepository).deleteAll();
    }

    @Test
    void testAllNotesDone() {
        Principal mockPrincipal = mock(Principal.class);
        User testUser = new User();
        testUser.setNotes(new ArrayList<>());
        Note testNote = new Note();
        testNote.setType(NoteType.NOTE);
        testNote.setActive(true);
        testUser.getNotes().add(testNote);
        when(mockUserService.loggedUser(mockPrincipal)).thenReturn(testUser);
        noteService.allNotesDone(mockPrincipal);
        Assertions.assertFalse(testUser.getNotes().get(0).isActive(), "Notes should be with isActive status false.");
        verify(mockNoteRepository).saveAll(List.of(testNote));
    }
    @Test
    void testAllTasksDone() {
        Principal mockPrincipal = mock(Principal.class);
        User testUser = new User();
        testUser.setNotes(new ArrayList<>());
        Note testNote = new Note();
        testNote.setType(NoteType.TASK);
        testNote.setActive(true);
        testUser.getNotes().add(testNote);
        when(mockUserService.loggedUser(mockPrincipal)).thenReturn(testUser);
        noteService.allTasksDone(mockPrincipal);
        Assertions.assertFalse(testUser.getNotes().get(0).isActive(), "Tasks should be with isActive status false.");
        verify(mockNoteRepository).saveAll(List.of(testNote));
    }

}
