package com.example.taskmanager.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static Date getRandomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(2010, 2020);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static String getDateText(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return "" + calendar.get(Calendar.YEAR) + "/"
                + (calendar.get(Calendar.MONTH) + 1) + "/"
                + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String getTimeText(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return "" + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE);
    }
}




