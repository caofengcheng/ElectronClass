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
}
