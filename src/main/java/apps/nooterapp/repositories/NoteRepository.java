package apps.nooterapp.repositories;

import apps.nooterapp.model.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Note findNoteById(Long id);
}
