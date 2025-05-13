package apps.nooterapp.util;

import apps.nooterapp.model.entities.Note;
import apps.nooterapp.services.NoteService;
import apps.nooterapp.services.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class EmailSenderScheduler {
    private UserService userService;
    private NoteService noteService;


    public EmailSenderScheduler(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkIfTaskExist() {
        List<Note> allTasks = noteService.findAllActiveTasks();
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        for (Note task : allTasks) {
            LocalDateTime reminder = task.getReminderTime();
            if (reminder == null) continue;

            LocalDateTime taskTime = reminder.truncatedTo(ChronoUnit.MINUTES);

            if (taskTime.isAfter(now)) {
                // No more tasks can match, because the list is sorted
                break;
            } else if (taskTime.equals(now)) {
                // 🎯 Found a task that matches current time
                System.out.println("Reminder! Task: " + task.getTitle());
            }
        }
    }

}
