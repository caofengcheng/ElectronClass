package com.electronclass.common.database;

import com.blankj.utilcode.util.StringUtils;
import com.electronclass.pda.mvp.entity.ClassInfo;
import com.electronclass.pda.mvp.entity.SchoolInfo;
import com.electronclass.pda.mvp.entity.TeacherInfo;


/**
 * 存放全局变量
 */
public class GlobalParam {
    private static SchoolInfo schoolInfo;
    private static ClassInfo classInfo;
    private static TeacherInfo teacherInfo;
    private static String ecardNo; //设备号
    private static String eventTime;//考勤时间
    //    public static String      pUrl = "http://zteng-1258264962.cos.ap-shanghai.myqcloud.com/classBrand/elegant/";//图片地址
    public static String pUrl = "http://io.ztengit.com/ecard/file";//图片地址

    public static String TO_DUTY = "TO_DUTY";
    public static String ADD_DUTY = "ADD_DUTY";
    public static String UPDATE_DUTY = "UPDATE_DUTY";
    public static String UPDATE_DUTY_ITEM = "UPDATE_DUTY_ITEM";

    public static String APPURL = "APPURL";
    public static String FoodAppUrl = "http://jjez.yksmart2.com:8388/catering/";//智腾食堂
    public static String DYH5 = "http://io.ztengit.com/dyH5/index.html?param=";//德育h5
    public static String JKIP = "";//监控ip


    /**
     * 全量更新ID
     */
    public static String MULAN_UPDATEID = "b1896a9375";
    public static String HENGHONGDA_UPDATEID = "6f5867655a";
    public static String HK_UPDATEID = "c024855929";
    public static String ECARD_UPDATEID = "d01f17311e";


    /**
     * 刷卡类型
     */
    public static String cardType = "MAINACTIVITY";
    /**
     * 主页刷卡
     */
    public static String MAINACTIVITY = "MAINACTIVITY";

    /**
     * 食堂刷卡
     */
    public static String FOODACTIVITY = "FOODACTIVITY";

    /**
     * 其他刷卡
     */
    public static String OTHER = "OTHER";

    /**
     * 添加值日刷卡
     */
    public static String UPDATEACTIVITY = "UPDATEACTIVITY";

    private static String[] HOUR =
            {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                    "19", "20", "21", "22", "23"};

    private static String[] MINUTE =
            {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                    "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36",
                    "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54",
                    "55", "56", "57", "58", "59"};


    public static String toSettingPwd = "!@#TZjt123";

    public static String EVENTTIME = "EVENTTIME";


    public static String getCardType() {
        return cardType;
    }

    public static void setCardType(String cardType) {
        GlobalParam.cardType = cardType;
    }

    public static TeacherInfo getTeacherInfo() {
        return teacherInfo;
    }

    public static void setTeacherInfo(TeacherInfo teacherInfo) {
        GlobalParam.teacherInfo = teacherInfo;
    }

    public static String getEventTime() {
        return eventTime;
    }

    public static void setEventTime(String eventTime) {
        GlobalParam.eventTime = eventTime;
    }

    public static SchoolInfo getSchoolInfo() {
        return schoolInfo;
    }

    public static void setSchoolInfo(SchoolInfo schoolInfo) {
        GlobalParam.schoolInfo = schoolInfo;
    }

    public static ClassInfo getClassInfo() {
        return classInfo;
    }

    public static void setClassInfo(ClassInfo classInfo) {
        GlobalParam.classInfo = classInfo;
    }

    public static String getEcardNo() {
        return ecardNo;
    }

    public static void setEcardNo(String ecardNo) {
        GlobalParam.ecardNo = ecardNo;
    }

    public static String[] getHOUR() {
        return HOUR;
    }

    public static String[] getMINUTE() {
        return MINUTE;
    }

    public static String getJKIP() {
        return JKIP;
    }

    public static void setJKIP(String JKIP) {
        GlobalParam.JKIP = JKIP;
    }
}
