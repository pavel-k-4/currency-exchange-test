package pk.test.exchange.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class BigDecimalUtils {

    private static final DecimalFormat fromCbr;
    private static final DecimalFormat toUser;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');

        String cbrPattern = "####.####";
        fromCbr = new DecimalFormat(cbrPattern, symbols);
        fromCbr.setParseBigDecimal(true);

        String userPattern = "# ##0.00";
        toUser = new DecimalFormat(userPattern, symbols);
        toUser.setParseBigDecimal(true);
    }

    public static BigDecimal parse(String source) throws ParseException {
        if (source.indexOf('.') != -1) {
            return (BigDecimal) fromCbr.parse(source.replace('.', ','));
        }
        return (BigDecimal) fromCbr.parse(source);
    }

    public static String format(BigDecimal number) {
        return toUser.format(number);
    }
}
