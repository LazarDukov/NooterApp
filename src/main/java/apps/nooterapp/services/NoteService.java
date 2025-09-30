package apps.nooterapp.services;

import apps.nooterapp.model.dtos.AddRecordDTO;
import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.RecordType;
import apps.nooterapp.repositories.RecordRepository;
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
    private RecordRepository recordRepository;
    private UserService userService;


    public NoteService(UserRepository userRepository, RecordRepository recordRepository, UserService userService) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.userService = userService;
    }

    public void allNotesDone(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        List<Record> notes = loggedUser.getNotes().stream().filter(note -> note.getType().equals(RecordType.NOTE)).toList();
        notes.forEach(note -> note.setActive(false));
        recordRepository.saveAll(notes);
    }



    public void allNotesDelete(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        List<Record> notes = new ArrayList<>(loggedUser.getNotes().stream().filter(note -> note.getType().equals(RecordType.NOTE)).toList());
        int notesSize = notes.size();
        while (notesSize > 0) {
            Record record = recordRepository.findRecordById(notes.get(0).getId());
            loggedUser.getNotes().remove(record);
            notes.remove(0);
            notesSize--;
        }
        userRepository.save(loggedUser);

    }







}
