package com.medcaptain.utils.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormaterUtil {
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    public static DateFormat getDateFormat(){
        DateFormat df = threadLocal.get();
        if(null == df){
            df = new SimpleDateFormat(dateFormat);
            threadLocal.set(df);
        }
        return df;
    }

    public static String formatTimestamp(Long timestamp){
        Date date = new Date(timestamp);
        return getDateFormat().format(date);
    }
}
