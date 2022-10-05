package pk.test.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.test.exchange.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
