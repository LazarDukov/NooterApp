package apps.nooterapp.repositories;

import apps.nooterapp.model.entities.Record;
import apps.nooterapp.model.enums.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Record findRecordById(Long id);

    List<Record> findAllByTypeAndActiveOrderByReminderTime(RecordType type, boolean isActive);

    Record findRecordByTitleAndReminderTime(String title, LocalDateTime localDateTime);

}
