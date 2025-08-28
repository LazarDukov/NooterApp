package apps.nooterapp.web;


import apps.nooterapp.model.dtos.AddNoteDTO;
import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.NoteService;
import apps.nooterapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
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
    public String myNotesPage(@RequestParam(defaultValue = "desc") String sort, Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login"; // or show an error
        }
        List<Note> noteList = loggedUser.getNotes().stream().filter(Note::isActive).filter(note -> note.getType().toString().equals("NOTE"))
                .collect(Collectors.toList());
        if (sort.equals("asc")) {
            Collections.reverse(noteList);
            String sortedByNewest = "Sorted by newest";
            model.addAttribute("myNotes", noteList);
            model.addAttribute("sortedByNewest", sortedByNewest);
            model.addAttribute("sort", sort);
        } else if (sort.equals("desc")) {
            String sortedByOldest = "Sorted by oldest";
            model.addAttribute("myNotes", noteList);
            model.addAttribute("sortedByOldest", sortedByOldest);
            model.addAttribute("sort", sort);

        }
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

    @ModelAttribute("editNoteDTO")
    public EditNoteDTO editNoteDTO() {
        return new EditNoteDTO();
    }

    @PostMapping("/add-note")
    public String addNotePost(@Valid @ModelAttribute("addNoteDTO") AddNoteDTO addNoteDTO, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("ERRORS APPEARS!");
            redirectAttributes.addFlashAttribute("addNoteDTO", addNoteDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addNoteDTO", bindingResult);
            return "add-note";
        }
        System.out.println("NO ERRORS");
        noteService.addNote(principal, addNoteDTO);
        return "redirect:/my-profile";
    }

    @GetMapping("/my-notes/finish/{id}")
    public String archiveNote(@PathVariable Long id, @RequestParam(defaultValue = "desc") String sort) {
        noteService.archiveNoteOrTask(id);
        return "redirect:/my-notes?sort=" + sort;
    }


    @GetMapping("/my-notes/view/{id}")
    @ResponseBody
    public ResponseEntity<Note> viewNote(@PathVariable Long id) {
        Note note = noteService.viewNoteOrTask(id);
        return ResponseEntity.ok(note);
    }


    @GetMapping("/my-notes/edit-note/{id}")
    public String showEditNotePage(@PathVariable Long id, Model model) {
        Note note = noteService.viewNoteOrTask(id);
        model.addAttribute("editNoteDTO", note);
        return "edit-note";
    }


    @PostMapping("/my-notes/edit-note/{id}")
    public String postEditNotePage(@PathVariable Long id, @ModelAttribute EditNoteDTO editNoteDTO) {
        noteService.editNoteOrTask(id, editNoteDTO);
        return "redirect:/my-notes";

    }


    @GetMapping("/my-notes/delete-note/{id}")
    public String deleteNote(@PathVariable Long id, @RequestParam(defaultValue = "desc") String sort) {
        noteService.deleteNote(id);
        return "redirect:/my-notes?sort=" + sort;
    }

    @GetMapping("/my-notes/all-done")
    public String allNotesDone(Principal principal) {
        noteService.allNotesDone(principal);
        System.out.println("Im in controller for all done notes");
        return "redirect:/my-notes";
    }

    @GetMapping("/my-notes/all-delete")
    public String allNotesDelete(Principal principal) {
        noteService.allNotesDelete(principal);
        return "redirect:/my-notes";
    }


}
