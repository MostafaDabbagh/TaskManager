package com.example.taskmanager.utils;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    public static String getTimeText(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");
        String timeStr = simpleDateFormat.format(date);
        return timeStr;

    }
}




