package apps.nooterapp.web;

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
public class TaskController {
    private UserService userService;
    private NoteService noteService;

    public TaskController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("/my-tasks-created-desc")
    public String myTasksCreatedDesc(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login"; // or show an error
        }
        List<Note> taskList = loggedUser.getNotes().stream().filter(Note::isActive).filter(note -> note.getType().toString().equals("TASK")).collect(Collectors.toList());
        Collections.reverse(taskList);
        model.addAttribute("myTasks", taskList);
        model.addAttribute("sortedByNewest", "Sorted by newest");
        return "my-tasks";
    }

    @GetMapping("/my-tasks-created-asc")
    public String myTasksCreatedAsc(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login"; // or show an error
        }
        List<Note> taskList = loggedUser.getNotes().stream().filter(Note::isActive).filter(note -> note.getType().toString().equals("TASK"))
               .collect(Collectors.toList());
        model.addAttribute("myTasks", taskList);
        model.addAttribute("sortedByOldest", "Sorted by oldest");
        return "my-tasks";
    }

    @GetMapping("/my-tasks-reminder-sooner")
    public String myTasksReminderSooner(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login"; // or show an error
        }
        List<Note> taskList = loggedUser.getNotes().stream().filter(Note::isActive).filter(note -> note.getType().toString().equals("TASK"))
                .sorted(Comparator.comparing(Note::getReminderTime)).collect(Collectors.toList());
        model.addAttribute("myTasks", taskList);
        model.addAttribute("sortedByReminderSooner", "Sorted by reminder sooner");
        return "my-tasks";
    }


    @GetMapping("/my-tasks-reminder-further")
    public String myTasksReminderFurther(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login"; // or show an error
        }
        List<Note> taskList = loggedUser.getNotes().stream().filter(Note::isActive).filter(note -> note.getType().toString().equals("TASK"))
                .sorted(Comparator.comparing(Note::getReminderTime).reversed()).collect(Collectors.toList());
        model.addAttribute("myTasks", taskList);
        model.addAttribute("sortedByReminderFurther", "Sorted by reminder further");
        return "my-tasks";
    }


    @GetMapping("/my-tasks/finish/{id}")
    public String archiveTask(@PathVariable Long id) {
        noteService.archiveNoteOrTask(id);
        return "redirect:/my-tasks";
    }

    @GetMapping("/my-tasks/view/{id}")
    @ResponseBody
    public ResponseEntity<Note> viewTask(@PathVariable Long id) {
        Note note = noteService.viewNoteOrTask(id);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/my-tasks/edit-task/{id}")
    public String showEditTaskPage(@PathVariable Long id, Model model) {
        Note note = noteService.viewNoteOrTask(id);
        model.addAttribute("editNoteDTO", note);
        return "edit-task";
    }

    @PostMapping("/my-tasks/edit-task/{id}")
    public String postEditTaskPage(@PathVariable Long id, @ModelAttribute EditNoteDTO editNoteDTO) {
        noteService.editNoteOrTask(id, editNoteDTO);
        return "redirect:/my-tasks";

    }

    @GetMapping("/my-tasks/delete-task/{id}")
    public String deleteTask(@PathVariable Long id) {
        noteService.deleteNote(id);
        return "redirect:/my-tasks";
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

}
