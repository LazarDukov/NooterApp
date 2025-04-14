package apps.nooterapp.services;

import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
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

    private User getUser(Principal principal) {
        return userRepository.findUserByUsername(principal.getName()).orElse(null);
    }

    public List<Note> getNotes(Principal principal) {
        User user = getUser(principal);
        return user.getNotes();
    }
}
