package pk.test.exchange.repository;

import org.springframework.data.repository.CrudRepository;
import pk.test.exchange.model.History;
import pk.test.exchange.model.User;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Long> {
    List<History> findAllByUserOrderByDateDesc(User user);
    List<History> findAllByUserAndDateOrderByDateDesc(User user, LocalDate date);
}
