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

    private static char [] convert_nums = {
            '9', '0', '6', '3', '2', '8', '4', '7', '1'
    };

    // 根据系统时间生成唯一字符码
    public static String getPureNumberUniqueCode(){
        String tStr = String.valueOf(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for(int i=1;i<tStr.length();i++)
        {
            sb.append(convert_nums[tStr.charAt(i)-'0']);
        }
        return sb.toString();
    }

    private static char [][] letters= {
            {'0', 'H', '1', '9', 'k', 'O', 'r', 'X', 'c'},
            {'3', 'Q', '8', 'q', 'K', 'U', 'h', 'Z', 'y'},
            {'7', '5', '4', 'N', 'R', 'F', 'i', 'L', 'l'},
            {'A', '2', 'V', 'x', 'W', 'e', '#', 'I', 'S'},
            {'g', 'z', 'G', 'd', 'T', 'u', 'v', 'o', 'f'},
            {'B', 'b', 'w', 't', 'p', 'Y', 'C', 'a', 'E'},
            {'6', 'n', 'J', 's', 'D', 'P', 'j', 'M', 'm'}
    };

    // 根据系统时间生成唯一字符码
    public static String getUniqueCode(){
        //这里根据你的需要初始化不同的字符
        String tStr = String.valueOf(System.currentTimeMillis());
        /*因为tStr的字符只有‘0’-‘9’，我们可以把它看成索引到letters中找相应的字符，这样相当于“置换”，所以是不会重复的。*/
        int chosenArray = (tStr.charAt(tStr.length() - 1) - '0') % 7;
        char[] letter_arr = letters[chosenArray];
        StringBuilder sb = new StringBuilder();
        for(int i=1;i<tStr.length();i++)
        {
            sb.append(letter_arr[tStr.charAt(i) - '0']);
        }
        return sb.toString();
    }

    public static void main(String[] args){
        getUniqueCode();
    }
}
