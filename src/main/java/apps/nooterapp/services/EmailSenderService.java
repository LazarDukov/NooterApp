package apps.nooterapp.services;


import apps.nooterapp.util.CustomPasswordGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private final JavaMailSender javaMailSender;



    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;



    }

    @Value("$(nooter.application)")
    private String fromEmailId;

    public void sendReminder(String recipient, String title, String body) {

        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(fromEmailId);
        smm.setTo(recipient);
        smm.setText(body);
        smm.setSubject(title);
        javaMailSender.send(smm);
    }

    public void sendNewPassword(String recipient, String newPassword) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(fromEmailId);
        smm.setTo(recipient);
        smm.setSubject("New password");
        smm.setText(newPassword);
        javaMailSender.send(smm);


    }

}
