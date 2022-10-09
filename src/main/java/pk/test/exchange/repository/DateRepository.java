package pk.test.exchange.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Repository
public class DateRepository {
    private final EntityManager entityManager;

    public DateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public LocalDate getDate() {
        var sqlDate = entityManager.createNativeQuery("SELECT now()\\:\\:date").getSingleResult();
        return ((java.sql.Date) sqlDate).toLocalDate();
    }
}
