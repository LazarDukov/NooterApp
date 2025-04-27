package apps.nooterapp.services;

import apps.nooterapp.model.dtos.AddNoteDTO;
import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.NoteType;
import apps.nooterapp.repositories.NoteRepository;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

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


    public void addNote(Principal principal, AddNoteDTO addNoteDTO) {
        User loggedUser = userService.loggedUser(principal);
        Note note = new Note();
        note.setTitle(addNoteDTO.getTitle());
        note.setDescription(addNoteDTO.getDescription());
        note.setType(addNoteDTO.getType());
        note.setActive(true);
        note.setUser(loggedUser);
        if (note.getType().equals(NoteType.TASK)) {
            note.setReminderTime(addNoteDTO.getReminderTime());
        }
        loggedUser.getNotes().add(note);
        noteRepository.save(note);
        userRepository.save(loggedUser);


    }

    public void archiveNoteOrTask(Long id) {
        Note note = noteRepository.findNoteById(id);
        note.setActive(false);
        noteRepository.save(note);
    }

    public Note viewNoteOrTask(Long id) {
        return noteRepository.findNoteById(id);

    }

    public void editNoteOrTask(Long id, EditNoteDTO editNoteDTO) {
        Note note = noteRepository.findNoteById(id);
        note.setTitle(editNoteDTO.getTitle());
        note.setDescription(editNoteDTO.getDescription());
        note.setType(editNoteDTO.getType());
        note.setActive(true);
        if (note.getType().equals(NoteType.TASK)) {
            note.setReminderTime(editNoteDTO.getReminderTime());
        }
        noteRepository.save(note);
    }
}
