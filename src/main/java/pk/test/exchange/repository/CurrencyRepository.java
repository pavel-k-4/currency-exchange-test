package pk.test.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.test.exchange.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
