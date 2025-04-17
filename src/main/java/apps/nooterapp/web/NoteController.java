package apps.nooterapp.web;


import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.NoteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.Node;

import java.security.Principal;
import java.util.List;

@Controller
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService notesService) {
        this.noteService = notesService;
    }


    @GetMapping("/my-notes")
    public String myNotesPage(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // or show an error
        }
        List<Note> notes = user.getNotes();
        model.addAttribute("myNotes", notes);
        return "my-notes";
    }
}
