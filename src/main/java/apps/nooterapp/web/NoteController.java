package apps.nooterapp.web;


import apps.nooterapp.model.dtos.AddNoteDTO;
import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.NoteService;
import apps.nooterapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/my-notes-created-desc")
    public String myNotesPage(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login"; // or show an error
        }
        List<Note> noteList = loggedUser.getNotes().stream().filter(Note::isActive).filter(note -> note.getType().toString().equals("NOTE"))
                .collect(Collectors.toList());
        Collections.reverse(noteList);
        model.addAttribute("myNotes", noteList);
        return "my-notes";
    }

    @GetMapping("/my-notes-created-asc")
    public String myNotesSortedDesc(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login";
        }
        List<Note> noteListSortedAsc = loggedUser.getNotes().stream()
                .filter(Note::isActive)
                .filter(note -> note.getType().toString().equals("NOTE"))
                .toList();
        model.addAttribute("myNotes", noteListSortedAsc);
        return "my-notes";
    }

    @GetMapping("/my-tasks")
    public String myTasksPage(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login"; // or show an error
        }
        List<Note> taskList = loggedUser.getNotes().stream().filter(Note::isActive).filter(note -> note.getType().toString().equals("TASK"))
                .sorted(Comparator.comparing(Note::getReminderTime).reversed()).collect(Collectors.toList());
        model.addAttribute("myTasks", taskList);
        return "my-tasks";
    }

    @GetMapping("/archived-notes")
    public String archivedNotesPage(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login";
        }
        List<Note> archivedNotes = loggedUser.getNotes().stream().filter(note -> !note.isActive()).collect(Collectors.toList());
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

    @ModelAttribute("editNoteDTO")
    public EditNoteDTO editNoteDTO() {
        return new EditNoteDTO();
    }

    @PostMapping("/add-note")
    public String addNotePost(AddNoteDTO addNoteDTO, Principal principal) {
        noteService.addNote(principal, addNoteDTO);
        return "redirect:/my-profile";
    }

    @GetMapping("/my-notes/finish/{id}")
    public String archiveNote(@PathVariable Long id) {
        noteService.archiveNoteOrTask(id);
        return "redirect:/my-notes";
    }

    @GetMapping("/my-tasks/finish/{id}")
    public String archiveTask(@PathVariable Long id) {
        noteService.archiveNoteOrTask(id);
        return "redirect:/my-tasks";
    }

    @GetMapping("/my-notes/view/{id}")
    @ResponseBody
    public ResponseEntity<Note> viewNote(@PathVariable Long id) {
        Note note = noteService.viewNoteOrTask(id);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/my-tasks/view/{id}")
    @ResponseBody
    public ResponseEntity<Note> viewTask(@PathVariable Long id) {
        Note note = noteService.viewNoteOrTask(id);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/archived-notes/view/{id}")
    @ResponseBody
    public ResponseEntity<Note> viewArchivedNoteOrTask(@PathVariable Long id) {
        Note note = noteService.viewNoteOrTask(id);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/my-notes/edit-note/{id}")
    public String showEditNotePage(@PathVariable Long id, Model model) {
        Note note = noteService.viewNoteOrTask(id);
        model.addAttribute("editNoteDTO", note);
        return "edit-note";
    }

    @GetMapping("/my-tasks/edit-task/{id}")
    public String showEditTaskPage(@PathVariable Long id, Model model) {
        Note note = noteService.viewNoteOrTask(id);
        model.addAttribute("editNoteDTO", note);
        return "edit-task";
    }

    @PostMapping("/my-notes/edit-note/{id}")
    public String postEditNotePage(@PathVariable Long id, @ModelAttribute EditNoteDTO editNoteDTO) {
        noteService.editNoteOrTask(id, editNoteDTO);
        return "redirect:/my-notes";

    }


    @PostMapping("/my-tasks/edit-task/{id}")
    public String postEditTaskPage(@PathVariable Long id, @ModelAttribute EditNoteDTO editNoteDTO) {
        noteService.editNoteOrTask(id, editNoteDTO);
        return "redirect:/my-tasks";

    }

    @GetMapping("/my-notes/delete-note/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return "redirect:/my-notes";
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

    @GetMapping("/my-tasks/delete-task/{id}")
    public String deleteTask(@PathVariable Long id) {
        noteService.deleteNote(id);
        return "redirect:/my-tasks";
    }

    @GetMapping("/archived-notes/delete-all")
    public String deleteAllArchived(Principal principal) {
        noteService.deleteArchived(principal);
        return "redirect:/my-profile";
    }

    @GetMapping("/my-tasks/all-done")
    public String allTasksDone(Principal principal) {
        noteService.allTasksDone(principal);
        return "redirect:/my-tasks";
    }

    @GetMapping("/my-tasks/all-delete")
    public String allTasksDelete(Principal principal) {
        noteService.allTasksDelete(principal);
        return "redirect:/my-tasks";
    }

    @GetMapping("/archived-notes/restore/{id}")
    public String restoreNoteOrTask(@PathVariable Long id) {
        System.out.println("Im in CONTROLLER FOR RESTORE METHOD");
        noteService.restoreNoteOrTask(id);
        System.out.println("I ALREADY RETURN ARCHIVED NOTES");
        return "redirect:/archived-notes";
    }

    @GetMapping("/archived-notes/restore-all")
    public String restoreAll() {
        noteService.restoreAll();
        return "redirect:/archived-notes";
    }

}
