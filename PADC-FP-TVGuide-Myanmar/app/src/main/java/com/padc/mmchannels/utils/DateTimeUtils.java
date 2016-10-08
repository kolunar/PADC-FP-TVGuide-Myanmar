package com.padc.mmchannels.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 9/27/2016.
 */
public class DateTimeUtils {
    //DateFormat formatter = new SimpleDateFormat("HH:mm");
    //java.sql.Time timeValue = new java.sql.Time(formatter.parse(start_time_string).getTime());

    static DateFormat in_df = new SimpleDateFormat("HH:mm");
    static DateFormat out_df = new SimpleDateFormat("h:mma");
    static SimpleDateFormat sp_df = new SimpleDateFormat("yyyy-mm-dd HH:mm");

    public static long getAlarmTime(String air_date, String start_time, int time_ahead){
        Date airDate;
        long output = 0;
        try{
            //Conversion of input String to date
            airDate = sp_df.parse(air_date + " " + start_time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(airDate);
            cal.add(Calendar.MINUTE, time_ahead);
            output = cal.getTimeInMillis();
            System.out.println(output);
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        return output;
    }

    public static String getTimePeriod(String start_time, int duration){

        Date date_from;
        String output = null;
        try{
            //Conversion of input String to date
            date_from = in_df.parse(start_time);
            //old date format to new date format
            output = out_df.format(date_from);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date_from);
            cal.add(Calendar.MINUTE, duration);
            output = output.replace(":00","") + " - " + out_df.format(cal.getTime()).replace(":00","");
            System.out.println(output);
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        return output;
    }

    public static String getDuration(int duration){
        return "Duration: " + duration + "mins";
    }

    static SimpleDateFormat day_df = new SimpleDateFormat("E"); // the day of the week abbreviated
    public static String[] getDaysOfWeek(){
        Calendar calendar = Calendar.getInstance();
        Date day = calendar.getTime();

        String[] days = new String[7];
        days[0] = day_df.format(day);
        for (int i=1, size = days.length; i<size; i++)
        {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            day = calendar.getTime();
            days[i] = day_df.format(day);
        }
        return days;
    }

    public static int getDayNumber(String day) {
        switch (day){
            case "Sun":return 0;
            case "Mon":return 1;
            case "Tue":return 2;
            case "Wed":return 3;
            case "Thu":return 4;
            case "Fri":return 5;
            case "Sat":return 6;
            default:return 0;
        }
    }
}
