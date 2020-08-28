package com.electronclass.pda.mvp.entity;

import java.util.List;

/**
 * 考勤表
 */
public class Attendance {
    private AttendanceExt attendanceExt;
    private List<UserAttendanceInfo> userAttendanceInfo;

    public AttendanceExt getAttendanceExt() {
        return attendanceExt;
    }

    public void setAttendanceExt(AttendanceExt attendanceExt) {
        this.attendanceExt = attendanceExt;
    }

    public List<UserAttendanceInfo> getUserAttendanceInfo() {
        return userAttendanceInfo;
    }

    public void setUserAttendanceInfo(List<UserAttendanceInfo> userAttendanceInfo) {
        this.userAttendanceInfo = userAttendanceInfo;
    }
}
