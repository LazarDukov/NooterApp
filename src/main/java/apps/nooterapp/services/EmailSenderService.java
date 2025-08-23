package apps.nooterapp.services;


import apps.nooterapp.model.entities.Note;
import apps.nooterapp.util.CustomPasswordGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    private final NoteService noteService;


    public EmailSenderService(JavaMailSender javaMailSender, @Lazy  NoteService noteService) {
        this.javaMailSender = javaMailSender;
        this.noteService = noteService;


    }

    @Value("$(nooter.application)")
    private String fromEmailId;

    public void sendReminder(String recipient, String title, String body) {
        Note expiredReminder = noteService.archiveCurrentTask(title, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        noteService.archiveExpiredReminder(expiredReminder);

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
