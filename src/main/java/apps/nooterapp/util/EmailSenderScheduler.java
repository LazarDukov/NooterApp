package apps.nooterapp.util;

import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.services.EmailSenderService;
import apps.nooterapp.services.NoteService;
import apps.nooterapp.services.TaskService;
import apps.nooterapp.services.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class EmailSenderScheduler {
    private UserService userService;
    private TaskService taskService;
    private NoteService noteService;
    private EmailSenderService senderService;


    public EmailSenderScheduler(UserService userService, TaskService taskService, NoteService noteService, EmailSenderService senderService) {
        this.userService = userService;
        this.taskService = taskService;
        this.noteService = noteService;
        this.senderService = senderService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkIfTaskExist() {
        List<Record> allTasks = taskService.findAllActiveTasks();
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        for (Record task : allTasks) {
            LocalDateTime reminder = task.getReminderTime();
            if (reminder == null) continue;

            LocalDateTime taskTime = reminder.truncatedTo(ChronoUnit.MINUTES);

            if (taskTime.isAfter(now)) {
                break;
            } else if (taskTime.equals(now)) {
                String username = task.getUser().getUsername();
                User userOfGivenTask = userService.getUserByUsername(username);
                String email = userOfGivenTask.getEmail();
                String taskTitle = task.getTitle();
                String taskDescription = task.getDescription();
                senderService.sendReminder(email, taskTitle, taskDescription);

            }
        }
    }

}
