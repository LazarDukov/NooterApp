package apps.nooterapp.services;

import apps.nooterapp.model.dtos.AddNoteDTO;
import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.NoteType;
import apps.nooterapp.repositories.NoteRepository;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class NoteService {
    private UserRepository userRepository;
    private NoteRepository noteRepository;

    public NoteService(UserRepository userRepository, NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
    }


    public User loggedUser(Principal principal) {
        return userRepository.findUserByUsername(principal.getName());
    }

    public void addNote(Principal principal, AddNoteDTO addNoteDTO) {
        User loggedUser = userRepository.findUserByUsername(principal.getName());
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
}
