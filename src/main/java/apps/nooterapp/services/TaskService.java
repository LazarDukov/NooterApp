package apps.nooterapp.services;

import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.RecordType;
import apps.nooterapp.repositories.RecordRepository;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private UserRepository userRepository;
    private RecordRepository recordRepository;
    private UserService userService;


    public TaskService(UserRepository userRepository, RecordRepository recordRepository, UserService userService) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.userService = userService;
    }


    public List<Record> findAllActiveTasks() {
        return recordRepository.findAllByTypeAndActiveOrderByReminderTime(RecordType.TASK, true);
    }
    public void allTasksDone(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        List<Record> tasks = loggedUser.getNotes().stream().filter(note -> note.getType().equals(RecordType.TASK)).toList();
        tasks.forEach(task -> task.setActive(false));
        recordRepository.saveAll(tasks);
    }
    public void allTasksDelete(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        List<Record> tasks = new ArrayList<>(loggedUser.getNotes().stream().filter(task -> task.getType().equals(RecordType.TASK)).toList());
        int tasksSize = tasks.size();
        while (tasksSize > 0) {
            Record task = recordRepository.findRecordById(tasks.get(0).getId());
            loggedUser.getNotes().remove(task);
            tasks.remove(0);
            tasksSize--;
        }
        userRepository.save(loggedUser);
    }

    public Record archiveCurrentTask(String title, LocalDateTime localDateTime) {
        return recordRepository.findRecordByTitleAndReminderTime(title, localDateTime);

    }

    public void archiveExpiredReminder(Record expiredReminder) {
        Record record = recordRepository.findRecordByTitleAndReminderTime(expiredReminder.getTitle(), expiredReminder.getReminderTime()).setActive(false);
        recordRepository.save(record);
    }
}
