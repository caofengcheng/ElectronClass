package com.electronclass.common.database;

import com.electronclass.pda.mvp.entity.ClassInfo;
import com.electronclass.pda.mvp.entity.ClassMessage;
import com.electronclass.pda.mvp.entity.SchoolInfo;
import com.electronclass.pda.mvp.entity.TeacherInfo;

/**
 * 存放全局变量
 */
public class GlobalParam {
    public static SchoolInfo  schoolInfo;
    public static ClassInfo   classInfo;
    public static TeacherInfo teacherInfo;
    public static String      ecardNo;
    public static String      eventTime;
    public static String      pUrl        = "http://zteng-1258264962.cos.ap-shanghai.myqcloud.com/classBrand/elegant/";//图片地址

    public static String      TO_DUTY     = "TO_DUTY";
    public static String      ADD_DUTY    = "ADD_DUTY";
    public static String      UPDATE_DUTY = "UPDATE_DUTY";
    public static String      UPDATE_DUTY_ITEM = "UPDATE_DUTY_ITEM";

    private static String[] HOUR =
            {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                    "19", "20", "21", "22", "23"};

    private static String[] MINUTE =
            {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                    "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36",
                    "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54",
                    "55", "56", "57", "58", "59"};

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


}
