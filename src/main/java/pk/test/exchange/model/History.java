package pk.test.exchange.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenerationTime;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "history")
@DynamicInsert
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "initial_currency_id")
    private Currency initialCurrency;
    @ManyToOne
    @JoinColumn(name = "target_currency_id")
    private Currency targetCurrency;
    private BigDecimal initialValue;
    private BigDecimal targetValue;
    private LocalDate date;

    public History() {
    }

    public History(User user, Currency initialCurrency, Currency targetCurrency, BigDecimal initialValue, BigDecimal targetValue) {
        this.user = user;
        this.initialCurrency = initialCurrency;
        this.targetCurrency = targetCurrency;
        this.initialValue = initialValue;
        this.targetValue = targetValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Currency getInitialCurrency() {
        return initialCurrency;
    }

    public void setInitialCurrency(Currency initialCurrency) {
        this.initialCurrency = initialCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public BigDecimal getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(BigDecimal targetValue) {
        this.targetValue = targetValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
