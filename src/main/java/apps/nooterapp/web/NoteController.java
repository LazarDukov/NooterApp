package apps.nooterapp.web;


import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class NoteController {
    private NoteService notesService;

    public NoteController(NoteService notesService) {
        this.notesService = notesService;
    }


    @GetMapping("/my-notes")
    public String myNotesPage(Principal principal, Model model) {
        model.addAttribute("myNotes", this.notesService.getNotes(principal));
        return "my-notes";
    }
}
