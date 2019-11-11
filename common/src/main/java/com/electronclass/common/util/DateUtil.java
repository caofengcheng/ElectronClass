package com.electronclass.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private DateUtil() {
    }


    public static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取当前时间
     *
     * @return 返回当前时间，格式2017-05-04	10:54:21
     */
    public static String getNowDate(DatePattern pattern) {
        String           dateString = null;
        Calendar         calendar   = Calendar.getInstance();
        Date             dateNow    = calendar.getTime();
        SimpleDateFormat sdf        = new SimpleDateFormat( pattern.getValue(), Locale.CHINA );
        dateString = sdf.format( dateNow );
        return dateString;
    }

    /**
     * 根据日期字符串判断当月第几周
     *
     * @return
     * @throws Exception
     */
    public static int getWeek() throws Exception {
        SimpleDateFormat sdf      = new SimpleDateFormat( "yyyy-MM-dd" );
        Date             date     = sdf.parse( getNowDate( DatePattern.ALL_TIME ) );
        Calendar         calendar = Calendar.getInstance();
        calendar.setTime( date );
        //第几周
        int week = calendar.get( Calendar.WEEK_OF_MONTH );
        return week;
    }

    /**
     * 将一个日期字符串转换成Data对象
     *
     * @param dateString 日期字符串
     * @param pattern    转换格式
     * @return 返回转换后的日期对象
     */
    public static Date stringToDate(String dateString, DatePattern pattern) {
        Date             date = null;
        SimpleDateFormat sdf  = new SimpleDateFormat( pattern.getValue(), Locale.CHINA );
        try {
            date = sdf.parse( dateString );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将date转换成字符串
     *
     * @param date    日期
     * @param pattern 日期的目标格式
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        String           string = "";
        SimpleDateFormat sdf    = new SimpleDateFormat( pattern, Locale.CHINA );
        string = sdf.format( date );
        return string;
    }

    /**
     * 获取指定日期周几
     *
     * @param date 指定日期
     * @return 返回值为： "周日", "周一", "周二", "周三", "周四", "周五", "周六"
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        int week = calendar.get( Calendar.DAY_OF_WEEK ) - 1;
        if (week < 0)
            week = 0;
        return weekDays[week];
    }

    /**
     * 获取指定日期对应周几的序列
     *
     * @param date 指定日期
     * @return 周一：1	周二：2	周三：3	周四：4	周五：5	周六：6	周日：7
     */
    public static int getIndexWeekOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        int index = calendar.get( Calendar.DAY_OF_WEEK );
        if (index == 1) {
            return 7;
        } else {
            return --index;
        }
    }

    /**
     * 返回当前月份
     *
     * @return
     */
    public static int getNowMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get( Calendar.MONTH ) + 1;
    }

    /**
     * 获取当前月号
     *
     * @return
     */
    public static int getNowDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get( Calendar.DATE );
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getNowYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get( Calendar.YEAR );
    }

    /**
     * 获取本月份的天数
     *
     * @return
     */
    public static int getNowDaysOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return daysOfMonth( calendar.get( Calendar.YEAR ), calendar.get( Calendar.DATE ) + 1 );
    }


    // 获取本周开始时间
    public static int startWeek(Date date) {
        // 使用Calendar类进行时间的计算
        SimpleDateFormat sdf = new SimpleDateFormat( "dd" );
        Calendar         cal = Calendar.getInstance();
        cal.setTime( date );
        // 获得当前日期是一个星期的第几天
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会计算到下一周去。
        // dayWeek值（1、2、3...）对应周日，周一，周二...
        int dayWeek = cal.get( Calendar.DAY_OF_WEEK );
        if (dayWeek == 1) {
            dayWeek = 7;
        } else {
            dayWeek -= 1;
        }
        System.out.println( "前时间是本周的第几天:" + dayWeek ); // 输出要当前时间是本周的第几天
        return dayWeek;
    }

    /**
     * 获取周一到周日
     *
     * @return
     */
    public static String[] getWeekDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd" );
        Calendar         calendar   = Calendar.getInstance();
        while (calendar.get( Calendar.DAY_OF_WEEK ) != Calendar.MONDAY) {
            calendar.add( Calendar.DAY_OF_WEEK, -1 );
        }
        Date[]   dates = new Date[7];
        String[] dateS = new String[7];
        for (int i = 0; i < 7; i++) {
            dateS[i] = dateFormat.format( calendar.getTime() );
            calendar.add( Calendar.DATE, 1 );
        }
        return dateS;
    }


    //获得系统时间加10分钟后的日期
    public static String getAfterTen() {
        Calendar now = Calendar.getInstance();
        now.add( Calendar.MINUTE, 10 );
        SimpleDateFormat sdf  = new SimpleDateFormat( "yyyy-MM-dd-HH-mm" );
        String           date = sdf.format( now.getTimeInMillis() );
        return date;
    }

    //获得系统时间加20分钟后的日期
    public static String getAfterTwenty() {
        Calendar now = Calendar.getInstance();
        now.add( Calendar.MINUTE, 20 );
        SimpleDateFormat sdf  = new SimpleDateFormat( "yyyy-MM-dd-HH-mm" );
        String           date = sdf.format( now.getTimeInMillis() );
        return date;
    }

    //获得系统时间加半小时后的小时
    public static String getHour() {
        Calendar now = Calendar.getInstance();
        now.add( Calendar.MINUTE, 30 );
        SimpleDateFormat sdf  = new SimpleDateFormat( "HH" );
        String           hour = sdf.format( now.getTimeInMillis() );
        return hour;
    }

    //获得系统时间加半小时后的小时
    public static String getMinutes() {
        Calendar now = Calendar.getInstance();
        now.add( Calendar.MINUTE, 30 );
        SimpleDateFormat sdf     = new SimpleDateFormat( "mm" );
        String           minutes = sdf.format( now.getTimeInMillis() );
        return minutes;
    }

    /**
     * 返回明天
     *
     * @return
     */
    public static String tomorrow() {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        Calendar         now = Calendar.getInstance();
        now.setTime( new Date() );
        now.add( now.DATE, 1 );
        String date = sdf.format( now.getTime() );
        return date;
    }

    public static String[] Year() {
        return new String[]{"1999",
                "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
                "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};
    }


    public static String[] Month() {
        return new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    }

    public static String[] Day28() {
        return new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28"};
    }

    public static String[] Day29() {
        return new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29"};
    }

    public static String[] Day30() {
        return new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    }

    public static String[] Day31() {
        return new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31"};
    }

    public static int thisYear() {
        String[] year    = Year();
        int      nowYear = getNowYear();
        for (int i = 0; i < year.length; i++) {
            if (year[i].equals(String.valueOf( nowYear ))) {
                return i;
            }
        }
        return 0;
    }

    public static int thisMonth() {
        String[] month    = Month();
        int      nowMonth = getNowMonth();
        for (int i = 0; i < month.length; i++) {
            if (month[i].equals( String.valueOf( nowMonth ) )) {
                return i;
            }
        }
        return 0;
    }

    public static int thisDay() {
        String[] month = new String[0];
        String day = String.valueOf( getNowDay());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getNowYear());
        calendar.set(Calendar.MONTH, getNowMonth()  - 1);

        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        switch (days) {
            case 28:
                month = Day28();
                break;
            case 29:
                month = Day29();
                break;
            case 30:
                month = Day30();
                break;
            case 31:
                month = Day31();
                break;
        }
        for (int i = 0; i < month.length; i++) {
            if (month[i].equals( day )) {
                return i;
            }
        }
        return 0;
    }

    public static String[] thisDayEntries() {
        String[] day = new String[0];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getNowYear());
        calendar.set(Calendar.MONTH, getNowMonth()  - 1);

        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        switch (days) {
            case 28:
                day = Day28();
                break;
            case 29:
                day = Day29();
                break;
            case 30:
                day = Day30();
                break;
            case 31:
                day = Day31();
                break;
        }
        return day;
    }

    /**
     * 获取指定月份的天数
     *
     * @param year  年份
     * @param month 月份
     * @return 对应天数
     */
    public static int daysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if ((year % 4 == 0 && year % 100 == 0) || year % 400 != 0) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 枚举日期格式
     */
    public enum DatePattern {

        /**
         * 格式："yyyy-MM-dd HH:mm:ss"
         */
        ALL_TIME {
            public String getValue() {
                return "yyyy-MM-dd HH:mm:ss";
            }
        },
        /**
         * 格式："yyyy-MM"
         */
        ONLY_MONTH {
            public String getValue() {
                return "yyyy-MM";
            }
        },
        /**
         * 格式："yyyy-MM-dd"
         */
        ONLY_DAY {
            public String getValue() {
                return "yyyy-MM-dd";
            }
        },
        /**
         * 格式："yyyy-MM-dd HH"
         */
        ONLY_HOUR {
            public String getValue() {
                return "yyyy-MM-dd HH";
            }
        },
        /**
         * 格式："yyyy-MM-dd HH:mm"
         */
        ONLY_MINUTE {
            public String getValue() {
                return "yyyy-MM-dd HH:mm";
            }
        },
        /**
         * 格式："MM-dd"
         */
        ONLY_MONTH_DAY {
            public String getValue() {
                return "MM-dd";
            }
        },
        /**
         * 格式："MM-dd HH:mm"
         */
        ONLY_MONTH_SEC {
            public String getValue() {
                return "MM-dd HH:mm";
            }
        },
        /**
         * 格式："HH:mm:ss"
         */
        ONLY_TIME {
            public String getValue() {
                return "HH:mm:ss";
            }
        },
        /**
         * 格式："dd"
         */
        ONLY_DD {
            public String getValue() {
                return "dd";
            }

        },
        /**
         * 格式："HH:mm"
         */
        ONLY_HOUR_MINUTE {
            public String getValue() {
                return "HH:mm";
            }
        };


        public abstract String getValue();
    }

    private static SimpleDateFormat msFormat = new SimpleDateFormat( "mm:ss" );

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    public static String timeParse(long duration) {
        String time = "";
        if (duration > 1000) {
            time = timeParseMinute( duration );
        } else {
            long minute  = duration / 60000;
            long seconds = duration % 60000;
            long second  = Math.round( (float) seconds / 1000 );
            if (minute < 10) {
                time += "0";
            }
            time += minute + ":";
            if (second < 10) {
                time += "0";
            }
            time += second;
        }
        return time;
    }

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    public static String timeParseMinute(long duration) {
        try {
            return msFormat.format( duration );
        } catch (Exception e) {
            e.printStackTrace();
            return "0:00";
        }
    }

    /**
     * 判断两个时间戳相差多少秒
     *
     * @param d
     * @return
     */
    public static int dateDiffer(long d) {
        try {
            long l1       = Long.parseLong( String.valueOf( System.currentTimeMillis() ).substring( 0, 10 ) );
            long interval = l1 - d;
            return (int) Math.abs( interval );
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 计算两个时间间隔
     *
     * @param sTime
     * @param eTime
     * @return
     */
    public static String cdTime(long sTime, long eTime) {
        long diff = eTime - sTime;
        return diff > 1000 ? diff / 1000 + "秒" : diff + "毫秒";
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String           res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        long             lt               = new Long( s );
        Date             date             = new Date( lt );
        res = simpleDateFormat.format( date );
        return res;
    }

    /**
     * 根据年月获得 这个月总共有几天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDay(int year, int month) {
        int     day  = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }
}

