package com.padc.tvguide.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 9/27/2016.
 */
public class DateTimeUtils {
    public static String getTimePeriod(String start_time, int duration){
        DateFormat in_df = new SimpleDateFormat("HH:mm");
        DateFormat out_df = new SimpleDateFormat("h:mma");
        Date date_from = null;
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
}
