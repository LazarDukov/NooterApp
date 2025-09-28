package apps.nooterapp.services;

import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.enums.RecordType;
import apps.nooterapp.repositories.RecordRepository;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Record archiveCurrentTask(String title, LocalDateTime localDateTime) {
        System.out.println("in archive current task method in noteService and title is: " + title + " Local date time is " + localDateTime);
        return recordRepository.findRecordByTitleAndReminderTime(title, localDateTime);

    }

    public void archiveExpiredReminder(Record expiredReminder) {
        Record record = recordRepository.findRecordByTitleAndReminderTime(expiredReminder.getTitle(), expiredReminder.getReminderTime()).setActive(false);
        recordRepository.save(record);
    }
}
