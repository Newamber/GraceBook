package com.newamber.gracebook.util;

import android.content.Context;

import com.newamber.gracebook.R;
import com.newamber.gracebook.app.GraceBookApplication;

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

    private static final SimpleDateFormat YEAR_MONTH_DAY_WEEK_HOUR_MIN_CHS_FORMAT = new SimpleDateFormat
            ("yyyy年MM月dd日，HH:mm", Locale.CHINA);

    private static final SimpleDateFormat MONTH_DAY_WEEK_CHS_FORMAT_1 = new SimpleDateFormat
            ("yyyy-MM-dd， EEEE", Locale.getDefault());

    private static final SimpleDateFormat YEAR_CHS = new SimpleDateFormat
            ("yyyy年", Locale.CHINA);

    private static final SimpleDateFormat YEAR_MONTH_DAY_CHS_FORMAT = new SimpleDateFormat
            ("yyyy年MM月dd日", Locale.CHINA);

    private static final SimpleDateFormat YEAR_MONTH_DAY_FORMAT = new SimpleDateFormat
            ("yyyy-MM-dd", Locale.getDefault());

    private static final SimpleDateFormat YEAR_MONTH_CHS_FORMAT = new SimpleDateFormat
            ("yyyy年MM月", Locale.CHINA);

    private static final SimpleDateFormat MONTH_DAY_FORMAT = new SimpleDateFormat
            ("MM-dd", Locale.getDefault());

    private static final SimpleDateFormat HOUR_MIN_FORMAT = new SimpleDateFormat
            ("HH:mm", Locale.getDefault());


    public static String formatYearQuarterInCHS(Calendar calendar) {
        Context context = GraceBookApplication.getContext();
        String quarter;
        int month = calendar.get(Calendar.MONTH);
        if (month == 0 || month == 1 || month == 2) {
            quarter = context.getString(R.string.first_quarter);
        } else if (month == 3 || month == 4 || month == 5) {
            quarter = context.getString(R.string.second_quarter);
        } else if (month == 6 || month == 7 || month == 8) {
            quarter = context.getString(R.string.third_quarter);
        } else {
            quarter = context.getString(R.string.fourth_quarter);
        }
        return formatYearInCHS(calendar) + context.getString(R.string.comma_chs) + quarter;
    }

    public static String formatYearMonthDayHourMinInCHS(Calendar calendar) {
        return YEAR_MONTH_DAY_WEEK_HOUR_MIN_CHS_FORMAT.format(calendar.getTime());
    }

    public static String formatTodayInCHS() {
        return YEAR_MONTH_DAY_CHS_FORMAT.format(Calendar.getInstance().getTime());
    }

    public static String formatTodayWithWeekInCHS() {
        return YEAR_MONTH_DAY_WEEK_CHS_FORMAT.format(Calendar.getInstance().getTime());
    }

    public static String formatYearMontDayWithWeekInCHS(Calendar calendar) {
        return YEAR_MONTH_DAY_WEEK_CHS_FORMAT.format(calendar.getTime());
    }

    public static String formatYearInCHS(Calendar calendar) {
        return YEAR_CHS.format(calendar.getTime());
    }

    public static String formatMonthDayForFlow(Calendar calendar) {
        return MONTH_DAY_WEEK_CHS_FORMAT_1.format(calendar.getTime());
    }

    public static String formatYearMonthDayInCHS(Calendar calendar) {
        return YEAR_MONTH_DAY_CHS_FORMAT.format(calendar.getTime());
    }

    public static String formatYearMonthDay(Calendar calendar) {
        return YEAR_MONTH_DAY_FORMAT.format(calendar.getTime());
    }

    public static String formatYearMonthInCHS(Calendar calendar) {
        return YEAR_MONTH_CHS_FORMAT.format(calendar.getTime());
    }

    public static String formatMonthDay(Calendar calendar) {
        return MONTH_DAY_FORMAT.format(calendar.getTime());
    }

    public static String formatHourMin(Calendar calendar) {
        return HOUR_MIN_FORMAT.format(calendar.getTime());
    }

    public static Calendar getFirstDay(DateRange dateRange) {
        Calendar today = Calendar.getInstance();
        switch (dateRange) {
            case WEEK:
                int week = today.get(Calendar.DAY_OF_WEEK);
                if (week != Calendar.SUNDAY) today.add(Calendar.DAY_OF_MONTH, 2 - week);
                else today.add(Calendar.DAY_OF_MONTH, - 6);
                break;
            case MONTH:
                today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH));
                break;
            case QUARTER:
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
                break;
            case YEAR:
                today.set(Calendar.MONTH, today.getActualMinimum(Calendar.MONTH));
                today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH));
                break;
            default:
                break;
        }
        setStartOfDay(today);

        return today;
    }

    public static Calendar getLastDay(DateRange dateRange) {
        Calendar today = Calendar.getInstance();
        switch (dateRange) {
            case WEEK:
                int week = today.get(Calendar.DAY_OF_WEEK);
                if (week != Calendar.SUNDAY) today.add(Calendar.DAY_OF_MONTH, 8 - week);
                break;
            case MONTH:
                today.set(Calendar.DAY_OF_MONTH, today.getActualMaximum(Calendar.DAY_OF_MONTH));
                break;
            case QUARTER:
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
                break;
            case YEAR:
                today.set(Calendar.MONTH, today.getActualMaximum(Calendar.MONTH));
                today.set(Calendar.DAY_OF_MONTH, today.getActualMaximum(Calendar.DAY_OF_MONTH));
                break;
            default:
                break;
        }
        setEndOfDay(today);

        return today;
    }

    public static void setStartOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    public static void setEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
    }


    /*public static int compareDateByYearMonthDay(Calendar date1, Calendar date2) {
        int comparedYear = date1.get(Calendar.YEAR);
        int comparedMonth = date1.get(Calendar.MONTH);
        int comparedDay = date1.get(Calendar.DAY_OF_MONTH);
        int year = date2.get(Calendar.YEAR);
        int month = date2.get(Calendar.MONTH);
        int day = date2.get(Calendar.DAY_OF_MONTH);

        if ((comparedYear > year)
                || (comparedYear == year && comparedMonth > month)
                || (comparedYear == year && comparedMonth == month && comparedDay > day)) {
            return 1;
        } else if () {

        }
    }*/

    public enum DateRange {
        WEEK,
        MONTH,
        QUARTER,
        YEAR;

        public static DateRange valueOf(int ordinal) {
            if (ordinal < 0 || ordinal >= values().length) {
                throw new IndexOutOfBoundsException("Invalid ordinal");
            }
            return values()[ordinal];
        }
    }
}