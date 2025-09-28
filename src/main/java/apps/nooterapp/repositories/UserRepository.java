package apps.nooterapp.repositories;

import apps.nooterapp.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.records n WHERE n.id = :noteId")
    Optional<User> findUserByRecordsId(@Param("recordId") Long noteId);



}
