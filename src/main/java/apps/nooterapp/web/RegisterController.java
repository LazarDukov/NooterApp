package apps.nooterapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }


    @PostMapping("/register")
    public
}
