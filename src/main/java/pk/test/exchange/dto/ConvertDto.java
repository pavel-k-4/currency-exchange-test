package pk.test.exchange.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ConvertDto {
    @NotNull
    @NotEmpty
    private String initialCurrency;
    @NotNull
    @NotEmpty
    private String targetCurrency;
    @NotNull
    @Pattern(regexp="^[0-9]+([,.][0-9]*)?$")
    private String initialValue;

    public String getInitialCurrency() {
        return initialCurrency;
    }

    public void setInitialCurrency(String initialCurrency) {
        this.initialCurrency = initialCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(String initialValue) {
        this.initialValue = initialValue;
    }
}
