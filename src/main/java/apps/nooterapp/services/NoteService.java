package apps.nooterapp.services;

import apps.nooterapp.model.dtos.AddRecordDTO;
import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.RecordType;
import apps.nooterapp.repositories.NoteRepository;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private UserRepository userRepository;
    private NoteRepository noteRepository;
    private UserService userService;


    public NoteService(UserRepository userRepository, NoteRepository noteRepository, UserService userService) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.userService = userService;
    }


    public void addRecord(Principal principal, AddRecordDTO addRecordDTO) {
        User loggedUser = userService.loggedUser(principal);
        Record record = new Record();
        record.setTitle(addRecordDTO.getTitle());
        record.setDescription(addRecordDTO.getDescription());
        record.setType(addRecordDTO.getType());
        record.setActive(true);
        record.setUser(loggedUser);
        if (record.getType().equals(RecordType.TASK)) {
            record.setReminderTime(addRecordDTO.getReminderTime().truncatedTo(ChronoUnit.MINUTES));
        }
        record.setDateCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        loggedUser.getNotes().add(record);
        noteRepository.save(record);
        userRepository.save(loggedUser);


    }

    public void archiveRecord(Long id) {
        Record record = noteRepository.findNoteById(id);
        record.setActive(false);
        noteRepository.save(record);
    }

    public Record viewRecord(Long id) {
        return noteRepository.findNoteById(id);

    }

    public void editRecord(Long id, EditNoteDTO editNoteDTO) {
        Record record = noteRepository.findNoteById(id);
        record.setTitle(editNoteDTO.getTitle());
        record.setDescription(editNoteDTO.getDescription());
        record.setType(editNoteDTO.getType());
        record.setActive(true);
        if (record.getType().equals(RecordType.TASK)) {
            record.setReminderTime(editNoteDTO.getReminderTime().truncatedTo(ChronoUnit.MINUTES));
        }
        noteRepository.save(record);
    }



    public void deleteRecord(Long id) {
        Record record = noteRepository.findNoteById(id);
        User user = userService.getUserByNote(record.getId());
        user.getNotes().remove(record);
        userRepository.save(user);
        noteRepository.delete(record);
    }

    public void deleteArchived(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        loggedUser.getNotes().clear();
        userRepository.save(loggedUser);
        noteRepository.deleteAll();
    }

    public void allNotesDone(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        List<Record> notes = loggedUser.getNotes().stream().filter(note -> note.getType().equals(RecordType.NOTE)).toList();
        notes.forEach(note -> note.setActive(false));
        noteRepository.saveAll(notes);
    }

    public void allTasksDone(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        List<Record> tasks = loggedUser.getNotes().stream().filter(note -> note.getType().equals(RecordType.TASK)).toList();
        tasks.forEach(task -> task.setActive(false));
        noteRepository.saveAll(tasks);
    }

    public void allNotesDelete(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        List<Record> notes = new ArrayList<>(loggedUser.getNotes().stream().filter(note -> note.getType().equals(RecordType.NOTE)).toList());
        int notesSize = notes.size();
        while (notesSize > 0) {
            Record record = noteRepository.findNoteById(notes.get(0).getId());
            loggedUser.getNotes().remove(record);
            notes.remove(0);
            notesSize--;
        }
        userRepository.save(loggedUser);

    }

    public void allTasksDelete(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        List<Record> tasks = new ArrayList<>(loggedUser.getNotes().stream().filter(task -> task.getType().equals(RecordType.TASK)).toList());
        int tasksSize = tasks.size();
        while (tasksSize > 0) {
            Record task = noteRepository.findNoteById(tasks.get(0).getId());
            loggedUser.getNotes().remove(task);
            tasks.remove(0);
            tasksSize--;
        }
        userRepository.save(loggedUser);
    }

    public void restoreRecord(Long id) {

        User user = userService.getUserByNote(id);


        Record record = noteRepository.findNoteById(id);
        record.setActive(true);
        if (record.getType().equals(RecordType.TASK)) {
            record.setReminderTime(LocalDateTime.now().plusHours(24));
        }

        userRepository.save(user);
        noteRepository.save(record);

    }

    public void restoreAll() {
        List<Record> records = noteRepository.findAll()
                .stream()
                .filter(record -> (!record.isActive())).toList();
        records
                .stream()
                .filter(task -> task.getType().equals(RecordType.TASK))
                .forEach(task -> task.setReminderTime(LocalDateTime.now().plusHours(24)));
        records.forEach(noteOrTask -> noteOrTask.setActive(true));
        noteRepository.saveAll(records);
    }

    public void archiveExpiredReminder(Record expiredReminder) {
        Record record = noteRepository.findNoteByTitleAndReminderTime(expiredReminder.getTitle(), expiredReminder.getReminderTime()).setActive(false);
        noteRepository.save(record);
    }
}
