package apps.nooterapp.web;

import apps.nooterapp.model.dtos.RegisterDTO;
import apps.nooterapp.services.RegisterService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    private RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @ModelAttribute("registerDTO")
    public RegisterDTO initRegisterDTO() {
        return new RegisterDTO();
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegisterDTO registerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);
            return "redirect:/register";

        }
        registerService.registerNewUser(registerDTO);
        return "redirect:/login";
    }
}
