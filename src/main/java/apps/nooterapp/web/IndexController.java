package apps.nooterapp.web;


import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.stream.Collectors;


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
        String notesNumber = String.valueOf(loggedUser.getNotes().stream().filter(note -> note.getType().toString().equals("NOTE")).toList().size());
        String taskNumber = String.valueOf(loggedUser.getNotes().stream().filter(note -> note.getType().toString().equals("TASK")).toList().size());
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("email", email);
        model.addAttribute("notesNumber", notesNumber);
        model.addAttribute("taskNumber", taskNumber);
        return "my-profile";
    }

}
