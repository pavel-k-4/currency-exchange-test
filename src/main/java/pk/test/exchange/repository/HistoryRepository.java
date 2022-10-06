package pk.test.exchange.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pk.test.exchange.model.History;
import pk.test.exchange.model.User;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Long> {
    List<History> findAllByUserOrderByDateDesc(User user);

    @Query(
            value = "SELECT * " +
                    "FROM history h " +
                    "WHERE h.user_id = ?1 and h.date\\:\\:date = ?2 " +
                    "ORDER BY h.date DESC",
            nativeQuery = true
    )
    List<History> findAllByUserAndDateOrderByDateDesc(User user, LocalDate date);
}
