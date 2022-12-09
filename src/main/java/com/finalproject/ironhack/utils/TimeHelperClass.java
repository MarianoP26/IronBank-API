package com.finalproject.ironhack.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import static java.util.Calendar.*;

public class TimeHelperClass {

    public static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static long timePassedBetweenInSeconds(String now, String then) {
        if (then == null) { return 2;}
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Date d1 = format.parse(now);
            Date d2 = format.parse(then);

            long timeBetween = d1.getTime() - d2.getTime(); //Millis

            return timeBetween / 1000;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return 2;
    }


    public static int getDiffMonths(Date first, Date last) {
        LocalDateTime firstie = first.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime secondie = first.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return (int) ChronoUnit.MONTHS.between(firstie,secondie);
    }
    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.ITALY);
        cal.setTime(date);
        return cal;
    }

}
