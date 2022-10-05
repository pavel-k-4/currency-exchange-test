package pk.test.exchange.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class BigDecimalParser {
    public static BigDecimal parse(String source) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');
        String pattern = "####.####";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        return (BigDecimal) decimalFormat.parse(source);
    }
}
