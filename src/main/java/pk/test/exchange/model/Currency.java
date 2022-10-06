package pk.test.exchange.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @Column(name = "cbr_id")
    private String id;
    private int numCode;
    private String charCode;
    private String name;

    public Currency() {
    }

    public Currency(String id, int numCode, String charCode, String name) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.name = name;
    }

    public String toSimpleString() {
        return "%s (%s)".formatted(charCode, name);
    }
    public String getHint() { return "(%s)".formatted(name); }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
