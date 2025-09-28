package apps.nooterapp.web;

import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.RecordRepository;
import apps.nooterapp.services.ArchiveService;
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
    private ArchiveService archiveService;


    private RecordRepository recordRepository;

    public TaskController(UserService userService, NoteService noteService, ArchiveService archiveService, RecordRepository recordRepository) {
        this.userService = userService;
        this.noteService = noteService;
        this.archiveService = archiveService;
        this.recordRepository = recordRepository;
    }

    @GetMapping("/my-tasks")
    public String getMyTasks(@RequestParam(defaultValue = "desc") String sort, Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login";
        }
        if (sort.equals("desc") || sort.equals("asc")) {
            List<Record> taskListByCreatedDate = loggedUser.getNotes().stream().filter(Record::isActive).filter(note -> note.getType().toString().equals("TASK")).collect(Collectors.toList());
            if (sort.equals("asc")) {
                Collections.reverse(taskListByCreatedDate);
                model.addAttribute("myTasks", taskListByCreatedDate);
                model.addAttribute("sort", sort);
                model.addAttribute("sortedByNewest", "Sorted by newest");
            } else if (sort.equals("desc")) {
                model.addAttribute("myTasks", taskListByCreatedDate);
                model.addAttribute("sort", sort);
                model.addAttribute("sortedByOldest", "Sorted by oldest");
            }
        } else if (sort.equals("sooner") || sort.equals("later")) {
            List<Record> taskListByReminderTime = loggedUser.getNotes().stream().filter(Record::isActive).filter(note -> note.getType().toString().equals("TASK"))
                    .sorted(Comparator.comparing(Record::getReminderTime)).collect(Collectors.toList());
            if (sort.equals("sooner")) {
                model.addAttribute("myTasks", taskListByReminderTime);
                model.addAttribute("sort", sort);
                model.addAttribute("sortedBySooner", "Sorted by sooner");
            } else if (sort.equals("later")) {
                Collections.reverse(taskListByReminderTime);
                model.addAttribute("myTasks", taskListByReminderTime);
                model.addAttribute("sort", sort);
                model.addAttribute("sortedByLater", "Sorted by later");
            }
        }
        return "my-tasks";
    }


    @GetMapping("/my-tasks/finish/{id}")
    public String archiveTask(@PathVariable Long id, @RequestParam (defaultValue = "desc") String sort) {
        archiveService.archiveRecord(id);
        return "redirect:/my-tasks?sort=" + sort;
    }

    @GetMapping("/my-tasks/view/{id}")
    @ResponseBody
    public ResponseEntity<Record> viewTask(@PathVariable Long id) {
        Record record = noteService.viewRecord(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/my-tasks/edit-task/{id}")
    public String showEditTaskPage(@PathVariable Long id, Model model) {
        Record record = noteService.viewRecord(id);
        model.addAttribute("editNoteDTO", record);
        return "edit-task";
    }

    @PostMapping("/my-tasks/edit-task/{id}")
    public String postEditTaskPage(@PathVariable Long id, @ModelAttribute EditNoteDTO editNoteDTO) {
        noteService.editRecord(id, editNoteDTO);
        return "redirect:/my-tasks";

    }

    @GetMapping("/my-tasks/delete-task/{id}")
    public String deleteTask(@PathVariable Long id, @RequestParam(defaultValue = "desc") String sort) {
        noteService.deleteRecord(id);
        return "redirect:/my-tasks?sort=" + sort;
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
