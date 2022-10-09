package pk.test.exchange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pk.test.exchange.dto.NewUserCreationError;
import pk.test.exchange.dto.NewUserDto;
import pk.test.exchange.model.User;
import pk.test.exchange.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

import static pk.test.exchange.dto.NewUserCreationError.*;

@Service
public class UserService {
    private final static Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Transactional
    public Optional<NewUserCreationError> createUser(NewUserDto dto) {
        if (!dto.getPassword().equals(dto.getMatchingPassword())) {
            return Optional.of(PASSWORDS_DONT_MATCH);
        }

        var previouslyExistingUser = userRepository.findByUsername(dto.getUsername());
        if (previouslyExistingUser.isPresent()) {
            return Optional.of(USERNAME_OCCUPIED);
        }

        try {
           userRepository.save(new User(
                dto.getUsername(),
                encoder.encode(dto.getPassword())
            ));
        } catch (Exception e) {
            log.error("Exception while creating user {}", dto, e);
            return Optional.of(DATABASE_ERROR);
        }
        log.info("Successfully created user {}", dto.getUsername());
        return Optional.empty();
    }
}
