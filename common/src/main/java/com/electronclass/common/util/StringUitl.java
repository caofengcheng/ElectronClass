package com.electronclass.common.util;

import android.util.Log;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUitl {

    public static String replaceString(String text, String arg) {
        return text.replace(arg, "");
    }

    /**
     * String 转 data
     *
     * @param str
     * @return
     */
    public static Date stringToDate(String str) {
        DateFormat   format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("StringUitl", "StringUitl:" + date);
        return date;
    }
}
