package apps.nooterapp.web;

import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.ArchiveService;
import apps.nooterapp.services.NoteService;
import apps.nooterapp.services.RecordService;
import apps.nooterapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ArchiveController {
    private UserService userService;
    private NoteService noteService;
    private RecordService recordService;
    private ArchiveService archiveService;

    public ArchiveController(UserService userService, NoteService noteService, RecordService recordService, ArchiveService archiveService) {
        this.userService = userService;
        this.noteService = noteService;
        this.recordService = recordService;
        this.archiveService = archiveService;
    }

    @GetMapping("/archived-records")
    public String archivedNotesPage(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        if (loggedUser == null) {
            return "redirect:/login";
        }
        List<Record> archivedRecords = loggedUser.getNotes().stream().filter(note -> !note.isActive()).collect(Collectors.toList());
        model.addAttribute("archivedRecords", archivedRecords);
        return "archived-records";

    }
    @GetMapping("/archived-records/view/{id}")
    @ResponseBody
    public ResponseEntity<Record> viewArchivedNoteOrTask(@PathVariable Long id) {
        Record record = recordService.viewRecord(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/archived-records/delete-all")
    public String deleteAllArchived(Principal principal) {
        archiveService.deleteArchived(principal);
        return "redirect:/my-profile";
    }



    @GetMapping("/archived-records/restore/{id}")
    public String restoreNoteOrTask(@PathVariable Long id) {
        System.out.println("Im in CONTROLLER FOR RESTORE METHOD");
        recordService.restoreRecord(id);
        System.out.println("I ALREADY RETURN ARCHIVED NOTES");
        return "redirect:/archived-records";
    }

    @GetMapping("/archived-records/restore-all")
    public String restoreAll() {
        recordService.restoreAll();
        return "redirect:/archived-records";
    }
}
