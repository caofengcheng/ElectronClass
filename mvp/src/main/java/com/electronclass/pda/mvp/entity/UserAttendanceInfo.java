package com.electronclass.pda.mvp.entity;

/**
 * 学生考勤列表
 */
public class UserAttendanceInfo {
    private String userId;
    private String userName;
    private int attendanceType;//考勤状态 0 - 未打卡 1-正常打卡， 2 -迟到打卡

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(int attendanceType) {
        this.attendanceType = attendanceType;
    }
}
