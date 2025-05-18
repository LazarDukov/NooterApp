package apps.nooterapp.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private JavaMailSender javaMailSender;


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

}
