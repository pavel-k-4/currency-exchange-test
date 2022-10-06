package pk.test.exchange.dto;

import java.util.ArrayList;
import java.util.List;

public class HistoryEntriesDto {
    private List<HistoryEntryDto> entries = new ArrayList<>();

    public HistoryEntriesDto() {
    }

    public HistoryEntriesDto(List<HistoryEntryDto> entries) {
        this.entries = entries;
    }

    public static class HistoryEntryDto {
        private CurrencyDto initialCurrency;
        private CurrencyDto targetCurrency;
        private String initialValue;
        private String targetValue;
        private String date;

        public HistoryEntryDto() {
        }

        public HistoryEntryDto(CurrencyDto initialCurrency, CurrencyDto targetCurrency, String initialValue, String targetValue, String date) {
            this.initialCurrency = initialCurrency;
            this.targetCurrency = targetCurrency;
            this.initialValue = initialValue;
            this.targetValue = targetValue;
            this.date = date;
        }

        public CurrencyDto getInitialCurrency() {
            return initialCurrency;
        }

        public void setInitialCurrency(CurrencyDto initialCurrency) {
            this.initialCurrency = initialCurrency;
        }

        public CurrencyDto getTargetCurrency() {
            return targetCurrency;
        }

        public void setTargetCurrency(CurrencyDto targetCurrency) {
            this.targetCurrency = targetCurrency;
        }

        public String getInitialValue() {
            return initialValue;
        }

        public void setInitialValue(String initialValue) {
            this.initialValue = initialValue;
        }

        public String getTargetValue() {
            return targetValue;
        }

        public void setTargetValue(String targetValue) {
            this.targetValue = targetValue;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public static class CurrencyDto {
            private String code;
            private String hint;

            public CurrencyDto() {
            }

            public CurrencyDto(String code, String hint) {
                this.code = code;
                this.hint = hint;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getHint() {
                return hint;
            }

            public void setHint(String hint) {
                this.hint = hint;
            }
        }
    }

    public List<HistoryEntryDto> getEntries() {
        return entries;
    }
}
