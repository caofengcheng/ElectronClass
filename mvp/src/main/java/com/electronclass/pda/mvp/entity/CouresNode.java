package com.electronclass.pda.mvp.entity;

/**
 * 班级课表
 */
public class CouresNode {
    private String classId;
    //  班级ID

    private String courseName;
    private int courseNum;
    // 节次,从1开始

    private String id;
    // 课程节点id

    private String schoolId;
    // 学校ID

    private String teacherName;
    //  教师姓名

    private int week;
    // 星期几 0 -星期天 1-星期一 … 6-星期六

    private int weekType;
    //  单双周 1-单周 2-双周 0-每周


    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeekType() {
        return weekType;
    }

    public void setWeekType(int weekType) {
        this.weekType = weekType;
    }
}
