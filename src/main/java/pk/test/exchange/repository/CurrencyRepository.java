package pk.test.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.test.exchange.model.Currency;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
