package apps.nooterapp.services;

import apps.nooterapp.model.dtos.AddNoteDTO;
import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.NoteType;
import apps.nooterapp.repositories.NoteRepository;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Note> findAllActiveTasks() {
        return noteRepository.findAllByTypeAndActiveOrderByReminderTime(NoteType.TASK, true);
    }

    public void deleteNote(Long id) {
        Note note = noteRepository.findNoteById(id);
        User user = userService.getUserByNote(note.getId());
        user.getNotes().remove(note);
        userRepository.save(user);
        noteRepository.delete(note);
    }

    public void deleteArchived(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        loggedUser.getNotes().clear();
        userRepository.save(loggedUser);
        noteRepository.deleteAll();
    }

    public void allNotesDone(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        List<Note> notes = loggedUser.getNotes().stream().filter(note -> note.getType().equals(NoteType.NOTE)).toList();
        notes.forEach(note -> note.setActive(false));
        noteRepository.saveAll(notes);
    }

    public void allTasksDone(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        List<Note> tasks = loggedUser.getNotes().stream().filter(note -> note.getType().equals(NoteType.TASK)).toList();
        tasks.forEach(task -> task.setActive(false));
        noteRepository.saveAll(tasks);
    }
}
