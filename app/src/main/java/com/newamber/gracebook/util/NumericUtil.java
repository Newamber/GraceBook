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
    private static final String CURRENCY_PATTERN_CHART = ",##0.0";
    private static final String AMOUNT_PATTERN = "0.00";


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
        String currencySymbol = LocalStorage.getString(GlobalConstant.CURRENCY_SYMBOL,
                GlobalConstant.CURRENCY_SYMBOL_CHS);
        return currencySymbol + decimalFormat.format(number);
    }

    public static String formatCurrencyForChart(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat(CURRENCY_PATTERN_CHART);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(number);
    }

    public static String formatAmount(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat(AMOUNT_PATTERN);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(number);
    }

}
