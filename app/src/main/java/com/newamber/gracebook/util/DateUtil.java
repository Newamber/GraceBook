package com.newamber.gracebook.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Description:.<br>
 * <p>more specific...</p>
 * <p>
 * Created by Newamber at 2017/7/17.
 */

public class DateUtil {

    private static final SimpleDateFormat YEAR_MONTH_DAY_WEEK_CHS_FORMAT = new SimpleDateFormat
            ("yyyy年MM月dd日，EEEE", Locale.CHINA);

    private static final SimpleDateFormat YEAR_MONTH_DAY_CHS_FORMAT = new SimpleDateFormat
            ("yyyy年MM月dd日", Locale.CHINA);

    private static final SimpleDateFormat YEAR_MONTH_DAY_FORMAT = new SimpleDateFormat
            ("yyyy-MM-dd", Locale.CHINA);

    private static final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat
            ("yyyy-MM", Locale.CHINA);

    private static final SimpleDateFormat MONTH_DAY_FORMAT = new SimpleDateFormat
            ("MM-dd", Locale.CHINA);

    private static final SimpleDateFormat HOUR_MIN_FORMAT = new SimpleDateFormat
            ("HH:mm", Locale.CHINA);


    public static String getTodayInCHS() {
        return YEAR_MONTH_DAY_CHS_FORMAT.format(Calendar.getInstance().getTime());
    }

    public static String getTodayWithWeekInCHS() {
        return YEAR_MONTH_DAY_WEEK_CHS_FORMAT.format(Calendar.getInstance().getTime());
    }

    public static String getYearMonthDayInCHS(Calendar calendar) {
        return YEAR_MONTH_DAY_CHS_FORMAT.format(calendar.getTime());
    }

    public static String getYearMonthDay(Calendar calendar) {
        return YEAR_MONTH_DAY_FORMAT.format(calendar.getTime());
    }

    public static String getYearMonth(Calendar calendar) {
        return YEAR_MONTH_FORMAT.format(calendar.getTime());
    }

    public static String getMonthDay(Calendar calendar) {
        return MONTH_DAY_FORMAT.format(calendar.getTime());
    }

    public static String getHourMin(Calendar calendar) {
        return HOUR_MIN_FORMAT.format(calendar.getTime());
    }

    public static Calendar getFirstDayThisWeek() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_WEEK, today.getActualMinimum(Calendar.DAY_OF_WEEK));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.add(Calendar.DAY_OF_MONTH, - 6);

        return today;
    }

    public static Calendar getLastDayThisWeek() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_WEEK, today.getActualMaximum(Calendar.DAY_OF_WEEK));
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        today.add(Calendar.DAY_OF_MONTH, - 6);

        return today;
    }

    public static Calendar getFirstDayThisMonth() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        return today;
    }

    public static Calendar getLastDayThisMonth() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, today.getActualMaximum(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        return today;
    }

    public static Calendar getFirstDayThisQuarter() {
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);

        if (month == 0 || month == 1 || month == 2) {
            today.set(Calendar.MONTH, Calendar.JANUARY);
        } else if (month == 3 || month == 4 || month == 5) {
            today.set(Calendar.MONTH, Calendar.APRIL);
        } else if (month == 6 || month == 7 || month == 8) {
            today.set(Calendar.MONTH, Calendar.JULY);
        } else {
            today.set(Calendar.MONTH, Calendar.OCTOBER);
        }

        today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        return today;
    }

    public static Calendar getLastDayThisQuarter() {
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);

        if (month == 0 || month == 1 || month == 2) {
            today.set(Calendar.MONTH, Calendar.MARCH);
        } else if (month == 3 || month == 4 || month == 5) {
            today.set(Calendar.MONTH, Calendar.JUNE);
        } else if (month == 6 || month == 7 || month == 8) {
            today.set(Calendar.MONTH, Calendar.SEPTEMBER);
        } else {
            today.set(Calendar.MONTH, Calendar.DECEMBER);
        }

        today.set(Calendar.DAY_OF_MONTH, today.getActualMaximum(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);

        return today;
    }

    public static Calendar getFirstDayThisYear() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.MONTH, today.getActualMinimum(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        return today;
    }

    public static Calendar getLastDayThisYear() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.MONTH, today.getActualMaximum(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, today.getActualMaximum(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);

        return today;
    }

}

