package apps.nooterapp.services;

import apps.nooterapp.model.dtos.AddRecordDTO;
import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.RecordType;
import apps.nooterapp.repositories.RecordRepository;
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
public class RecordServiceTests {
    private NoteService noteService;
    private TaskService taskService;
    private RecordService recordService;
    private ArchiveService archiveService;
    private AddRecordDTO addRecordDTO;
    private Record testRecord;
    private User testUser;
    @Mock
    private RecordRepository mockRecordRepository;
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserService mockUserService;


    @Captor
    private ArgumentCaptor<Record> noteArgumentCaptor;



    @BeforeEach
    void setUp() {
        noteService = new NoteService(mockUserRepository, mockRecordRepository, mockUserService);

        testRecord = new Record();
        testRecord.setId(1L);
        testRecord.setTitle("TEST TITLE");
        testRecord.setDescription("TEST DESCRIPTION");
        testRecord.setType(RecordType.NOTE);
        testRecord.setActive(true);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("test user");
        testUser.setEmail("test email");
        testUser.setPassword("123456");
        testUser.setNotes(new ArrayList<>());
        testUser.getNotes().add(testRecord);
    }

    @Test
    void testAddNote() {
        Principal mockPrincipal = mock(Principal.class);
        User mockUser = new User();
        mockUser.setNotes(new ArrayList<>());
        when(mockUserService.loggedUser(mockPrincipal)).thenReturn(mockUser);
        addRecordDTO = new AddRecordDTO();
        addRecordDTO.setTitle("Most important task today");
        addRecordDTO.setDescription("I should run today!");
        addRecordDTO.setActive(true);
        addRecordDTO.setType(RecordType.NOTE);
        recordService.addRecord(mockPrincipal, addRecordDTO);
        verify(mockRecordRepository).save(noteArgumentCaptor.capture());
        Record savedRecord = noteArgumentCaptor.getValue();
        Assertions.assertEquals(addRecordDTO.getTitle(), savedRecord.getTitle());
        Assertions.assertEquals(addRecordDTO.getType(), savedRecord.getType());
        Assertions.assertEquals(addRecordDTO.getDescription(), savedRecord.getDescription());
        Assertions.assertEquals(addRecordDTO.getTitle(), mockUser.getNotes().get(0).getTitle());
        Assertions.assertEquals(1, mockUser.getNotes().size());
        Assertions.assertNotNull(addRecordDTO);
        // Assertions.assertEquals(1, savedNote.getId());
    }

    @Test
    void testArchiveNoteOrTask() {
        when(mockRecordRepository.findRecordById(testRecord.getId())).thenReturn(testRecord);
        archiveService.archiveRecord(testRecord.getId());
        verify(mockRecordRepository).save(noteArgumentCaptor.capture());
        Record savedRecord = noteArgumentCaptor.getValue();
        Assertions.assertFalse(savedRecord.isActive(), "Note should be archived");
    }

    @Test
    void testViewNoteOrTask() {
        when(mockRecordRepository.findRecordById(testRecord.getId())).thenReturn(testRecord);
        Record result = recordService.viewRecord(testRecord.getId());
        Assertions.assertSame(testRecord, result);
    }

    @Test
    void testEditNoteOrTask() {
        when(mockRecordRepository.findRecordById(testRecord.getId())).thenReturn(testRecord);
        EditNoteDTO editNoteDTO = new EditNoteDTO();
        editNoteDTO.setTitle("EDIT TITLE");
        editNoteDTO.setDescription("EDIT DESCRIPTION");
        editNoteDTO.setType(RecordType.NOTE);
        editNoteDTO.setActive(true);
        recordService.editRecord(testRecord.getId(), editNoteDTO);
        verify(mockRecordRepository).save(noteArgumentCaptor.capture());
        Record savedRecord = noteArgumentCaptor.getValue();
        Assertions.assertEquals(editNoteDTO.getTitle(), savedRecord.getTitle());
        Assertions.assertEquals(editNoteDTO.getDescription(), savedRecord.getDescription());
        Assertions.assertEquals(editNoteDTO.getType(), savedRecord.getType());
        Assertions.assertTrue(savedRecord.isActive(), "Yes, this is active!");
    }

    @Test
    void testFindAllActiveTasks() {
        Record record1 = new Record();
        record1.setTitle("TEST TASK 1");
        record1.setDescription("I should run today!");
        record1.setActive(true);
        record1.setType(RecordType.TASK);
        record1.setReminderTime(LocalDateTime.parse("2035-12-03T10:30:30"));

        Record record2 = new Record();
        record2.setTitle("TEST TASK 2");
        record2.setDescription("You shouldnt run today!");
        record2.setActive(true);
        record2.setType(RecordType.TASK);
        record2.setReminderTime(LocalDateTime.parse("2035-12-03T10:15:30"));

        Record record3 = new Record();
        record3.setTitle("TEST TASK 3");
        record3.setDescription("You should run today!");
        record3.setActive(true);
        record3.setType(RecordType.NOTE);
        record3.setReminderTime(LocalDateTime.parse("2045-12-03T10:35:30"));
        List<Record> records = List.of(record1, record2);

        when(mockRecordRepository.findAllByTypeAndActiveOrderByReminderTime(RecordType.TASK, true)).thenReturn(records);
        List<Record> savedRecords = taskService.findAllActiveTasks();

        Assertions.assertEquals(savedRecords.size(), records.size());
        Assertions.assertTrue(savedRecords.stream().allMatch(n -> n.getType() == RecordType.TASK));
        Assertions.assertTrue(savedRecords.stream().allMatch(Record::isActive));
    }

    @Test
    void testDeleteNote() {
        Long noteId = 1L;

        Record testRecord = new Record();
        testRecord.setId(noteId);
        testRecord.setTitle("Test Note");

        User testUser = new User();
        testUser.setId(10L);
        testUser.setNotes(new ArrayList<>(List.of(testRecord)));

        when(mockRecordRepository.findRecordById(noteId)).thenReturn(testRecord);
        when(mockUserService.getUserByNote(noteId)).thenReturn(testUser);

        // Act
        recordService.deleteRecord(noteId);

        // Assert
        Assertions.assertFalse(testUser.getNotes().contains(testRecord), "Note should be removed from user's notes");
        verify(mockUserRepository).save(testUser);
        verify(mockRecordRepository).delete(testRecord);

    }

    @Test
    void testDeleteArchived() {
        Principal mockPrincipal = mock(Principal.class);
        Record testRecord = new Record();
        Record testRecord2 = new Record();
        User testUser = new User();
        testUser.setNotes(new ArrayList<>(List.of(testRecord, testRecord2)));
        when(mockUserService.loggedUser(mockPrincipal)).thenReturn(testUser);
        archiveService.deleteArchived(mockPrincipal);
        Assertions.assertFalse(testUser.getNotes().size() > 0, "All notes should be removed.");
        verify(mockUserRepository).save(testUser);
        verify(mockRecordRepository).deleteAll();
    }

    @Test
    void testAllNotesDone() {
        Principal mockPrincipal = mock(Principal.class);
        User testUser = new User();
        testUser.setNotes(new ArrayList<>());
        Record testRecord = new Record();
        testRecord.setType(RecordType.NOTE);
        testRecord.setActive(true);
        testUser.getNotes().add(testRecord);
        when(mockUserService.loggedUser(mockPrincipal)).thenReturn(testUser);
        noteService.allNotesDone(mockPrincipal);
        Assertions.assertFalse(testUser.getNotes().get(0).isActive(), "Notes should be with isActive status false.");
        verify(mockRecordRepository).saveAll(List.of(testRecord));
    }
    @Test
    void testAllTasksDone() {
        Principal mockPrincipal = mock(Principal.class);
        User testUser = new User();
        testUser.setNotes(new ArrayList<>());
        Record testRecord = new Record();
        testRecord.setType(RecordType.TASK);
        testRecord.setActive(true);
        testUser.getNotes().add(testRecord);
        when(mockUserService.loggedUser(mockPrincipal)).thenReturn(testUser);
        taskService.allTasksDone(mockPrincipal);
        Assertions.assertFalse(testUser.getNotes().get(0).isActive(), "Tasks should be with isActive status false.");
        verify(mockRecordRepository).saveAll(List.of(testRecord));
    }

}
