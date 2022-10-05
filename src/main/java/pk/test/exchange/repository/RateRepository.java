package pk.test.exchange.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pk.test.exchange.model.Rate;

import java.util.Optional;

public interface RateRepository extends CrudRepository<Rate, Long> {
    @Query(
        value = "SELECT * " +
                "FROM rate r " +
                "WHERE r.currency_id = ?1 AND (r.date = now()\\:\\:date OR r.date IS NULL) " +
                "FETCH FIRST 1 ROWS ONLY",
        nativeQuery = true
    )
    Optional<Rate> findByCurrencyIdToday(String currencyId);
}
