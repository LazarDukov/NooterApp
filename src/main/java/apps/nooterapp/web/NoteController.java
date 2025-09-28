package apps.nooterapp.web;


import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.ArchiveService;
import apps.nooterapp.services.NoteService;
import apps.nooterapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NoteController {
    private NoteService noteService;
    private UserService userService;
    private ArchiveService archiveService;

    public NoteController(NoteService notesService, UserService userService, ArchiveService archiveService) {
        this.noteService = notesService;
        this.userService = userService;
        this.archiveService = archiveService;
    }


    @GetMapping("/my-records")
    public String myNotesPage(@RequestParam(defaultValue = "desc") String sort, Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login";
        }
        List<Record> noteList = loggedUser.getNotes().stream().filter(Record::isActive).filter(note -> note.getType().toString().equals("NOTE"))
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



    @GetMapping("/my-notes/finish/{id}")
    public String archiveNote(@PathVariable Long id, @RequestParam(defaultValue = "desc") String sort) {
        archiveService.archiveRecord(id);
        return "redirect:/my-notes?sort=" + sort;
    }


    @GetMapping("/my-notes/view/{id}")
    @ResponseBody
    public ResponseEntity<Record> viewNote(@PathVariable Long id) {
        Record record = noteService.viewRecord(id);
        return ResponseEntity.ok(record);
    }


    @ModelAttribute("editNoteDTO")
    public EditNoteDTO editNoteDTO() {
        return new EditNoteDTO();
    }

    @GetMapping("/my-notes/edit-note/{id}")
    public String showEditNotePage(@PathVariable Long id, Model model) {
        Record record = noteService.viewRecord(id);
        model.addAttribute("editNoteDTO", record);
        return "edit-note";
    }


    @PostMapping("/my-notes/edit-note/{id}")
    public String postEditNotePage(@PathVariable Long id, @ModelAttribute EditNoteDTO editNoteDTO) {
        noteService.editRecord(id, editNoteDTO);
        return "redirect:/my-notes";

    }


    @GetMapping("/my-notes/delete-note/{id}")
    public String deleteNote(@PathVariable Long id, @RequestParam(defaultValue = "desc") String sort) {
        noteService.deleteRecord(id);
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
