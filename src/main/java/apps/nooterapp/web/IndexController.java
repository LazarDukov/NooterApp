package apps.nooterapp.web;


import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        model.addAttribute("loggedUser", loggedUser);
        return "my-profile";
    }

}
