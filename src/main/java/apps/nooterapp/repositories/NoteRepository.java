package apps.nooterapp.repositories;

import apps.nooterapp.model.entities.Note;
import apps.nooterapp.model.enums.NoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Note findNoteById(Long id);
    List<Note> findAllByTypeAndActiveOrderByReminderTime(NoteType type, boolean isActive);


}
