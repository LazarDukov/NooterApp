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
        System.out.println("Im in add note method");
        User loggedUser = userRepository.findUserByUsername(principal.getName());
        System.out.println("logged user found");
        Note note = new Note();
        note.setTitle(addNoteDTO.getTitle());
        note.setDescription(addNoteDTO.getDescription());
        note.setType(NoteType.NOTE);
        note.setActive(true);
        note.setUser(loggedUser);
        System.out.println("note created");
        loggedUser.getNotes().add(note);
        System.out.println(loggedUser.getNotes().size());
        noteRepository.save(note);
        userRepository.save(loggedUser);
        System.out.println("method done");

    }
}
