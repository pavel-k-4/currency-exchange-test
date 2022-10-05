package pk.test.exchange.repository;

import org.springframework.data.repository.CrudRepository;
import pk.test.exchange.model.Rate;

public interface RateRepository extends CrudRepository<Rate, Long> {
}
