package apps.nooterapp.services;

import apps.nooterapp.model.dtos.AddRecordDTO;
import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.model.enums.RecordType;
import apps.nooterapp.repositories.RecordRepository;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
}
