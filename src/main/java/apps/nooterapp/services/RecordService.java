package apps.nooterapp.services;

import apps.nooterapp.model.dtos.AddRecordDTO;
import apps.nooterapp.model.dtos.EditNoteDTO;
import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.RecordType;
import apps.nooterapp.repositories.RecordRepository;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RecordService {
    private UserRepository userRepository;
    private RecordRepository recordRepository;
    private UserService userService;


    public RecordService(UserRepository userRepository, RecordRepository recordRepository, UserService userService) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.userService = userService;
    }

    public void addRecord(Principal principal, AddRecordDTO addRecordDTO) {
        User loggedUser = userService.loggedUser(principal);
        Record record = new Record();
        record.setTitle(addRecordDTO.getTitle());
        record.setDescription(addRecordDTO.getDescription());
        record.setType(addRecordDTO.getType());
        record.setActive(true);
        record.setUser(loggedUser);
        if (record.getType().equals(RecordType.TASK)) {
            record.setReminderTime(addRecordDTO.getReminderTime().truncatedTo(ChronoUnit.MINUTES));
        }
        record.setDateCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        loggedUser.getNotes().add(record);
        recordRepository.save(record);
        userRepository.save(loggedUser);


    }
    public Record viewRecord(Long id) {
        return recordRepository.findRecordById(id);

    }
    public void editRecord(Long id, EditNoteDTO editNoteDTO) {
        Record record = recordRepository.findRecordById(id);
        record.setTitle(editNoteDTO.getTitle());
        record.setDescription(editNoteDTO.getDescription());
        record.setType(editNoteDTO.getType());
        record.setActive(true);
        if (record.getType().equals(RecordType.TASK)) {
            record.setReminderTime(editNoteDTO.getReminderTime().truncatedTo(ChronoUnit.MINUTES));
        }
        recordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        Record record = recordRepository.findRecordById(id);
        User user = userService.getUserByRecord(record.getId());
        user.getNotes().remove(record);
        userRepository.save(user);
        recordRepository.delete(record);
    }
    public void restoreRecord(Long id) {
        System.out.println("find userBy record");
        User user = userService.getUserByRecord(id);
        System.out.println("FIND RECORD BY GIVEN ID");

        Record record = recordRepository.findRecordById(id);
        record.setActive(true);
        if (record.getType().equals(RecordType.TASK)) {
            record.setReminderTime(LocalDateTime.now().plusHours(24));
        }

        userRepository.save(user);
        recordRepository.save(record);

    }

    public void restoreAll() {
        List<Record> records = recordRepository.findAll()
                .stream()
                .filter(record -> (!record.isActive())).toList();
        records
                .stream()
                .filter(task -> task.getType().equals(RecordType.TASK))
                .forEach(task -> task.setReminderTime(LocalDateTime.now().plusHours(24)));
        records.forEach(noteOrTask -> noteOrTask.setActive(true));
        recordRepository.saveAll(records);
    }

}
