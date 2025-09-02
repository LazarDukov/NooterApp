package apps.nooterapp.web;

import apps.nooterapp.model.dtos.ChangePasswordDTO;
import apps.nooterapp.model.dtos.ForgotPasswordDTO;
import apps.nooterapp.services.UserService;
import apps.nooterapp.util.CustomPasswordGenerator;
import jakarta.validation.Valid;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {
    private UserService userService;
    private CustomPasswordGenerator customPasswordGenerator;


    public LoginController(UserService userService, CustomPasswordGenerator customPasswordGenerator) {
        this.userService = userService;
        this.customPasswordGenerator = customPasswordGenerator;

    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", "");
        }
        return "login";
    }

    @GetMapping("/login-error")
    public String loginUserError(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("bad_credentials", true);
        return "redirect:/login";
    }

    @ModelAttribute("forgotPasswordDTO")
    public ForgotPasswordDTO forgotPasswordDTO() {
        return new ForgotPasswordDTO();
    }

    @GetMapping("/forgot-log")
    public String forgotLog() {
        return "forgot-log";
    }

    @PostMapping("/forgot-log")
    public String forgotLog(@Valid @ModelAttribute ForgotPasswordDTO forgotPasswordDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println("I'm in forgot log post mapping");
        if (bindingResult.hasErrors()) {
            System.out.println("there are an errors");
            redirectAttributes.addFlashAttribute("forgotPasswordDTO", forgotPasswordDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.forgotPasswordDTO", bindingResult);
            return "redirect:/forgot-log";
        }
        System.out.println("no errors in forgot post mapping");
        String newPassword = customPasswordGenerator.generatePassayPassword();
        System.out.println("generated passwrd = " + newPassword);
        userService.sendNewPassword(forgotPasswordDTO.getEmail(), newPassword);
        return "redirect:/login";
    }

    @ModelAttribute("changePasswordDTO")
    public ChangePasswordDTO changePasswordDTO() {
        return new ChangePasswordDTO();
    }
    @GetMapping("/change-password")
    public String changePassword() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute @Valid ChangePasswordDTO changePasswordDTO,BindingResult bindingResult, Principal principal,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("Now have errors");
            redirectAttributes.addFlashAttribute("changePasswordDTO", changePasswordDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordDTO", bindingResult);
            return "change-password";
        }
        userService.changePassword(principal, changePasswordDTO);
        return "redirect:/my-profile";
    }

}
