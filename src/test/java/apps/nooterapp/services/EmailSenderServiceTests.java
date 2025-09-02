package apps.nooterapp.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailSenderServiceTests {
    private EmailSenderService emailSenderService;
    private JavaMailSender javaMailSender;
    private NoteService noteService;
    private TaskService taskService;


    @BeforeEach
    void setUp() {
        javaMailSender = mock(JavaMailSender.class);
        noteService = mock(NoteService.class);
        taskService = mock(TaskService.class);
        emailSenderService = new EmailSenderService(javaMailSender, noteService, taskService);
        try {
            java.lang.reflect.Field field = EmailSenderService.class.getDeclaredField("fromEmailId");
            field.setAccessible(true);
            field.set(emailSenderService, "no-reply@nooter.com");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSendReminder() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        emailSenderService.sendReminder("recipient@abv.bg", "title", "description");

        verify(javaMailSender, times(1)).send(captor.capture());
        SimpleMailMessage smm = captor.getValue();
        Assertions.assertEquals("no-reply@nooter.com", smm.getFrom());
        Assertions.assertEquals("recipient@abv.bg", smm.getTo()[0]);
        Assertions.assertEquals("title", smm.getSubject());
        Assertions.assertEquals("description", smm.getText());
    }

    @Test
    void testSendNewPassword() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        emailSenderService.sendNewPassword("recipient@abv.bg", "newPass");

        verify(javaMailSender, times(1)).send(captor.capture());
        SimpleMailMessage smm = captor.getValue();
        Assertions.assertEquals("no-reply@nooter.com", smm.getFrom());
        Assertions.assertEquals("recipient@abv.bg", smm.getTo()[0]);
        Assertions.assertEquals("New password", smm.getSubject());
        Assertions.assertEquals("newPass", smm.getText());
    }
}
