package apps.nooterapp.web;

import apps.nooterapp.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailSenderController {

    private EmailSenderService senderService;

    public EmailSenderController(EmailSenderService senderService) {
        this.senderService = senderService;
    }

    @GetMapping("sendEmail")
    public String sendEmail() {
        senderService.sendReminder("lazarrumenowdukow@gmail.com", "Test body", "Test subject");
        return "Sent successfully";
    }
}
