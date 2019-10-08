package com.electronclass.common.util;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 页面：
 * @author zhangguihao
 */
public class TextUtils {
    private static DecimalFormat format = null;

    private static Pattern getPhonePattern() {
        return Holder.phonePattern;
    }

    public static boolean isPhoneNumber(String phone) {
        if (null == phone) {
            return false;
        }
        return getPhonePattern().matcher(phone).matches();


    }

    private static class Holder {
        static Pattern phonePattern = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
    }

    public static String format(double number) {
        if (format == null) {
            format = new DecimalFormat("#.00");
        }
        String numStr = number + "";
        if ((numStr).endsWith("0")) {
            return numStr.substring(0, numStr.length() - 2);
        }
        return format.format(number);
    }
}
