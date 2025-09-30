package apps.nooterapp.services;

import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.RecordRepository;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ArchiveService {
    private UserRepository userRepository;
    private RecordRepository recordRepository;
    private UserService userService;


    public ArchiveService(UserRepository userRepository, RecordRepository recordRepository, UserService userService) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.userService = userService;
    }

    public void archiveRecord(Long id) {
        Record record = recordRepository.findRecordById(id);
        record.setActive(false);
        recordRepository.save(record);
    }

    public void deleteArchived(Principal principal) {
        User loggedUser = userService.loggedUser(principal);
        loggedUser.getNotes().clear();
        userRepository.save(loggedUser);
        recordRepository.deleteAll();
    }



}