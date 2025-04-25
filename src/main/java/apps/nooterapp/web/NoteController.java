package apps.nooterapp.web;


import apps.nooterapp.model.dtos.AddNoteDTO;
import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.NoteService;
import apps.nooterapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService notesService, UserService userService) {
        this.noteService = notesService;
        this.userService = userService;
    }


    @GetMapping("/my-notes")
    public String myNotesPage(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login"; // or show an error
        }
        List<Note> noteList = loggedUser.getNotes().stream().filter(Note::isActive).collect(Collectors.toList());
        model.addAttribute("myNotes", noteList);
        return "my-notes";
    }

    @GetMapping("/archived-notes")
    public String archivedNotesPage(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login";
        }
        List<Note> archivedNotes = loggedUser.getNotes().stream().filter(note -> !note.isActive()).toList();
        model.addAttribute("archivedNotes", archivedNotes);
        return "archived-notes";

    }

    @GetMapping("/add-note")
    public String addNotePage() {
        return "add-note";
    }

    @ModelAttribute("addNoteDTO")
    public AddNoteDTO addNoteDTO() {
        return new AddNoteDTO();
    }

    @PostMapping("/add-note")
    public String addNotePost(AddNoteDTO addNoteDTO, Principal principal) {
        noteService.addNote(principal, addNoteDTO);
        return "redirect:/my-notes";
    }

    @GetMapping("/my-notes/finish/{id}")
    public String archiveNoteOrTask(@PathVariable Long id) {
        noteService.archiveNoteOrTask(id);
        return "redirect:/my-notes";
    }

    @GetMapping("/my-notes/view/{id}")
    @ResponseBody
    public ResponseEntity<Note> viewNoteOrTask(@PathVariable Long id) {
        Note note = noteService.viewNoteOrTask(id);
        return ResponseEntity.ok(note);
    }
    @GetMapping("/archived-notes/view/{id}")
    @ResponseBody
    public ResponseEntity<Note> viewArchivedNoteOrTask(@PathVariable Long id) {
        Note note = noteService.viewNoteOrTask(id);
        return ResponseEntity.ok(note);
    }

}
