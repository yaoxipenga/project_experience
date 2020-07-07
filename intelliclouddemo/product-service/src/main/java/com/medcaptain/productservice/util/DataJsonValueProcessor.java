package com.medcaptain.productservice.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/***
 * 设定将Date类型转换为Json时格式的工具类
 */
public class DataJsonValueProcessor implements JsonValueProcessor {
    private String format = "yyyy-MM-dd HH:mm:ss";

    public static String stampToDate(String timeStamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(timeStamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public DataJsonValueProcessor()
    {

    }

    public DataJsonValueProcessor(String format)
    {

        this.format = format;
    }

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig)
    {

        String[] obj = {};
        if (value instanceof Date[])
        {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date[] dates = (Date[]) value;
            obj = new String[dates.length];
            for (int i = 0; i < dates.length; i++)
            {
                obj[i] = stampToDate(sf.format(dates[i]));
            }
        }
        return obj;
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig)
    {

        if (value instanceof Date)
        {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            String str = sf.format((Date) value);
            return stampToDate(str);
        }
        return value;
    }

    public String getFormat()
    {

        return format;
    }

    public void setFormat(String format)
    {

        this.format = format;
    }

}
