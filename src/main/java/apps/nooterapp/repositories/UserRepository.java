package apps.nooterapp.repositories;

import apps.nooterapp.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

}
