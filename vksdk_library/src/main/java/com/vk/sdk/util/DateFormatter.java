package com.vk.sdk.util;

import com.vk.sdk.api.model.VKApiMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 18.09.2015.
 */
public class DateFormatter {
    private static Date date;
    private static boolean isShort = false;

// --Commented out by Inspection START (11.08.2016 16:04):
//    public static String getDate(VKApiMessage message){
//        date = new Date(message.date * 1000);
//        isShort = false;
//        return checkForThisDay();
//    }
// --Commented out by Inspection STOP (11.08.2016 16:04)

    public static String getShortDate(VKApiMessage message){
        return getShortDate(message.date);
    }

    public static String getShortDate(long dateLong){
        date = new Date(dateLong * 1000);
        isShort = true;
        String finalDate = checkForThisDay();
        return finalDate.replace("24:", "00:");
    }

    private static String checkForThisDay() {
        SimpleDateFormat formatter;
        Calendar calendar = Calendar.getInstance();

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int current_year = calendar.get(Calendar.YEAR);
        int current_month = calendar.get(Calendar.MONTH);
        int current_day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        boolean isDayRight = (day == current_day);
        boolean isMonthRight = (month == current_month);
        boolean isYearRight = (year == current_year);

        if(isDayRight&&isMonthRight&&isYearRight){
            if(isShort)
                formatter = new SimpleDateFormat("kk:mm");
            else
                formatter = new SimpleDateFormat("kk:mm:ss");

            return  formatter.format(date);
        }

        if(isYearRight){
            formatter = new SimpleDateFormat("dd.MM");
            return  formatter.format(date);
        }
        formatter = new SimpleDateFormat("dd.MM.yy");
        return  formatter.format(date);
    }
}
