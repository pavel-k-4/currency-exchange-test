package pk.test.exchange.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "history")
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
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
