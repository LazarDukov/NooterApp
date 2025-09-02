package apps.nooterapp.web;


import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Controller
public class IndexController {
    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/my-profile")
    public String myProfilePage(Principal principal, Model model) {
        User loggedUser = userService.loggedUser(principal);
        String email = loggedUser.getEmail();
        String notesNumber = String.valueOf(loggedUser.getNotes().stream().filter(Record::isActive).filter(note -> note.getType().toString().equals("NOTE")).toList().size());
        String taskNumber = String.valueOf(loggedUser.getNotes().stream().filter(Record::isActive).filter(note -> note.getType().toString().equals("TASK")).toList().size());
        String totalArchived = String.valueOf(loggedUser.getNotes().stream().filter(note -> (!note.isActive())).toList().size());

        model.addAttribute("loggedUser", loggedUser.getUsername());
        model.addAttribute("email", email);
        model.addAttribute("notesNumber", notesNumber);
        model.addAttribute("taskNumber", taskNumber);
        model.addAttribute("totalArchived", totalArchived);
        model.addAttribute("registerDate", loggedUser.getRegisterDate());
        return "my-profile1";
    }

    @PostMapping("/my-profile/edit-email")
    public String emailChange(Principal principal, @RequestParam("newEmail") String newEmail) {
        userService.changeEmail(principal, newEmail);
        return "redirect:/my-profile";
    }
}
