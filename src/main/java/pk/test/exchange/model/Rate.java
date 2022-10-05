package pk.test.exchange.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;
    private Long nominal;
    private BigDecimal value;
    private Date date;

    public Rate() {
    }

    public Rate(Currency currency, long nominal, BigDecimal value, Date date) {
        this.currency = currency;
        this.nominal = nominal;
        this.value = value;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getNominal() {
        return nominal;
    }

    public void setNominal(Long nominal) {
        this.nominal = nominal;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
