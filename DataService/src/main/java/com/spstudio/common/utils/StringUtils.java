package com.spstudio.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Soul on 2017/1/19.
 */
public class StringUtils {
    public static Date str2Date(String dataStr) throws ParseException{
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (dataStr != null && !dataStr.isEmpty()) {
            Date date = formatter.parse(dataStr);
            return date;
        }
        return null;
    }

    public static String date2Str(Date date) throws ParseException{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String reportDate = df.format(date);
        return reportDate;
    }
}
