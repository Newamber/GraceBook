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

    public static Calendar getMondayThisWeek() {
        Calendar today = Calendar.getInstance();
        today.setFirstDayOfWeek(Calendar.MONDAY);
        /*int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY: 2
            case Calendar.TUESDAY: 3 -4
                today.add(Calendar.DAY_OF_MONTH, -1);
                break;
            case Calendar.WEDNESDAY: 4 -6
                today.add(Calendar.DAY_OF_MONTH, -2);
                break;
            case Calendar.THURSDAY: 5 -8
                today.add(Calendar.DAY_OF_MONTH, -3);
                break;
            case Calendar.FRIDAY: 6 -10
                today.add(Calendar.DAY_OF_MONTH, -4);
                break;
            case Calendar.SATURDAY: 7 -12
                today.add(Calendar.DAY_OF_MONTH, -5);
                break;
            case Calendar.SUNDAY: 1 -7
                today.add(Calendar.DAY_OF_MONTH, -6);
                break;
            default:
                break;
        }*/
        today.set(Calendar.DAY_OF_WEEK, today.getActualMinimum(Calendar.DAY_OF_WEEK));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        return today;
    }

    public static Calendar getSundayThisWeek() {
        Calendar today = Calendar.getInstance();
        today.setFirstDayOfWeek(Calendar.MONDAY);
        /*int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                today.add(Calendar.DAY_OF_MONTH, 6);
                break;
            case Calendar.TUESDAY:
                today.add(Calendar.DAY_OF_MONTH, 5);
                break;
            case Calendar.WEDNESDAY:
                today.add(Calendar.DAY_OF_MONTH, 4);
                break;
            case Calendar.THURSDAY:
                today.add(Calendar.DAY_OF_MONTH, 3);
                break;
            case Calendar.FRIDAY:
                today.add(Calendar.DAY_OF_MONTH, 2);
                break;
            case Calendar.SATURDAY:
                today.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case Calendar.SUNDAY:
            default:
                break;
        }*/
        today.set(Calendar.DAY_OF_WEEK, today.getActualMaximum(Calendar.DAY_OF_WEEK));
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 60);

        return today;
    }

    public static Calendar getFirstDayofMonth() {
        Calendar day = Calendar.getInstance();
        day.set(Calendar.DAY_OF_MONTH, day.getActualMinimum(Calendar.DAY_OF_MONTH));
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        return day;
    }

    public static Calendar getLastDayOfMonth() {
        Calendar day = Calendar.getInstance();
        day.set(Calendar.DAY_OF_MONTH, day.getActualMaximum(Calendar.DAY_OF_MONTH));
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 60);
        return day;
    }
}

