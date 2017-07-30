package com.newamber.gracebook.util;

import android.support.annotation.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Description: Offer accurate arithmetic.<br>
 *
 * Created by Newamber at 2017/7/17.
 */
@SuppressWarnings("unused")
public class NumericUtil {

    private static final  int NUMERIC_PRECISION = 5;
    // CHS is the abbreviation of Chinese Simplified.
    private static final String CURRENCY_PATTERN_CHS = ",##0.00";

    // operator
    // equals  "+"
    @NonNull
    public static Double add(Double num_1, Double num_2) {
        return BigDecimal.valueOf(num_1)
                .add(BigDecimal.valueOf(num_2))
                .doubleValue();
    }

    // equals  "-"
    @NonNull
    public static Double subtract(Double num_1, Double num_2) {
        return BigDecimal.valueOf(num_1)
                .subtract(BigDecimal.valueOf(num_2))
                .doubleValue();
    }

    // equals  "*"
    @NonNull
    public static Double multiply(Double num_1, Double num_2) {
        return BigDecimal.valueOf(num_1)
                .multiply(BigDecimal.valueOf(num_2))
                .doubleValue();
    }

    // equals  "/"
    @NonNull
    public static Double divide(Double num_1, Double num_2) {
        return BigDecimal.valueOf(num_1)
                .divide(BigDecimal.valueOf(num_2), NUMERIC_PRECISION, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    public static String formatCurrency(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat(CURRENCY_PATTERN_CHS);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return GlobalConstant.CURRENCY_SYMBOL + decimalFormat.format(number);
    }

}
