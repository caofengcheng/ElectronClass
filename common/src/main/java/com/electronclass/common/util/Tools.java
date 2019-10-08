package com.electronclass.common.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.electronclass.common.base.BaseApplication;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Tools {

    /**
     * 气泡提示
     *
     * @param text 文本内容
     */
    public static void displayToast(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast toast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT);
        TextView textView = toast.getView().findViewById(android.R.id.message);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(20);
        toast.show();
    }

    /**
     * 获取机器序列号
     *
     * @param c Context
     * @return 机器序列号
     */
    @SuppressLint("HardwareIds")
    public static String getMachineCode(Context c) {
        final TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        final String tmDevice, tmSerial, androidId;
        if (tm == null) {
            return "";
        }
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(c.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }

    public static int dp2px(int dp) {
        return (int) (dp * BaseApplication.getInstance().getResources().getDisplayMetrics().density);
    }

    public static int px2dp(int px) {
        return (int) (px / BaseApplication.getInstance().getResources().getDisplayMetrics().density);
    }

    public static String getStringWithDefault(String text) {
        return getStringWithDefault(text, "--");
    }

    public static String getStringWithDefault(String text, String defaultValue) {
        return TextUtils.isEmpty(text) ? defaultValue : text;
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatDate(Date date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatNumber(long number, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(number);
    }

    public static long changeDateToTimeMillis(String date) {
        return changeDateToTimeMillis(date, "yyyy-MM-dd");
    }

    public static long changeDateToTimeMillis(String date, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
            Date date1 = simpleDateFormat.parse(date);
            return date1.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 字符串转为时间
     *
     * @param date   时间
     * @param format 字符串
     * @return
     */
    public static Date string2Date(String date, String format) {
        if (date == null || format == null) {
            return null;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date timeBetweenDates(Date date1, Date date2) {
        return new Date(date1.getTime() - date2.getTime());
    }

    public static String toHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return "";
        }
        char[] buffer = new char[2];
        for (int i = src.length - 1; i >= 0; i--) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }
}
