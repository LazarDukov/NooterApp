package apps.nooterapp.web;

import apps.nooterapp.model.dtos.AddRecordDTO;
import apps.nooterapp.services.NoteService;
import apps.nooterapp.services.RecordService;
import apps.nooterapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class RecordController {
    private NoteService noteService;
    private UserService userService;
    private RecordService recordService;

    public RecordController(NoteService notesService,RecordService recordService, UserService userService) {
        this.noteService = notesService;
        this.recordService = recordService;
        this.userService = userService;
    }

    @ModelAttribute("addRecordDTO")
    public AddRecordDTO addRecordDTO() {
        return new AddRecordDTO();
    }

    @GetMapping("/add-record")
    public String addNotePage() {
        return "add-note";
    }

    @PostMapping("/add-record")
    public String addRecordPost(@Valid @ModelAttribute("addRecordDTO") AddRecordDTO addRecordDTO, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addRecordDTO", addRecordDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addRecordDTO", bindingResult);
            return "add-note";
        }
        recordService.addRecord(principal, addRecordDTO);
        return "redirect:/my-profile";
    }

}
