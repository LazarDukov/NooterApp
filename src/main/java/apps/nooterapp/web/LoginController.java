package apps.nooterapp.web;

import apps.nooterapp.model.dtos.LoginDTO;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.LoginService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @ModelAttribute("loginDTO")
    public LoginDTO initLoginDTO() {
        return new LoginDTO();
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute LoginDTO loginDTO, Model model) {
        User user = loginService.loginUser(loginDTO);
        if (user != null && user.getUsername() != null) {
            return "redirect:/";
        }
        model.addAttribute("loginError", "Invalid username or password");
        return "login";
    }

}
