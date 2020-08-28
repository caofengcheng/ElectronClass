package com.electronclass.pda.mvp.entity;

/**
 * 考勤扩展信息
 */
public class AttendanceExt {
    private int id;
    private String classId;
    private  String eventDate;//查询日期
    private  int personNum;//应到人数
    private  int attendanceNum;//实到人数（包含迟到打卡）
    private int lateNum; //迟到人数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public int getAttendanceNum() {
        return attendanceNum;
    }

    public void setAttendanceNum(int attendanceNum) {
        this.attendanceNum = attendanceNum;
    }

    public int getLateNum() {
        return lateNum;
    }

    public void setLateNum(int lateNum) {
        this.lateNum = lateNum;
    }
}
