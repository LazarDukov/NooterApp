package apps.nooterapp.web;


import apps.nooterapp.model.dtos.AddNoteDTO;
import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.NoteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.w3c.dom.Node;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService notesService) {
        this.noteService = notesService;
    }


    @GetMapping("/my-notes")
    public String myNotesPage(Principal principal, Model model) {
        User loggedUser = noteService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login"; // or show an error
        }
        List<Note> noteList = loggedUser.getNotes().stream().filter(Note::isActive).collect(Collectors.toList());
        model.addAttribute("myNotes", noteList);
        return "my-notes";
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

    @GetMapping("/my-notes/{id}")
    public String archiveNoteOrTask(@PathVariable Long id, Principal principal) {
        noteService.archiveNoteOrTask(id, principal);
        return "redirect:/my-notes";
    }
}
