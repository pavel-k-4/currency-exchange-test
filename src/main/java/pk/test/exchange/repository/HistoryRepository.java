package pk.test.exchange.repository;

import org.springframework.data.repository.CrudRepository;
import pk.test.exchange.model.History;

public interface HistoryRepository extends CrudRepository<History, Long> {
}
