package apps.nooterapp.services;

import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.enums.RecordType;
import apps.nooterapp.repositories.NoteRepository;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private UserRepository userRepository;
    private NoteRepository noteRepository;
    private UserService userService;


    public TaskService(UserRepository userRepository, NoteRepository noteRepository, UserService userService) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public List<Record> findAllActiveTasks() {
        return noteRepository.findAllByTypeAndActiveOrderByReminderTime(RecordType.TASK, true);
    }

    public Record archiveCurrentTask(String title, LocalDateTime localDateTime) {
        System.out.println("in archive current task method in noteService and title is: " + title + " Local date time is " + localDateTime);
        return noteRepository.findNoteByTitleAndReminderTime(title, localDateTime);

    }
}
