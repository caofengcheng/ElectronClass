package com.electronclass.pda.mvp.entity;

/**
 * 权限
 */
public class Jurisdiction {
    SchoolInfo schoolInfo;
    TeacherInfo teacherInfo;
    int permission;   //0 -没有权限 即 普通教师，15- 班主任， 240 校管理员   255--班主任和管理员

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public SchoolInfo getSchoolInfo() {
        return schoolInfo;
    }

    public void setSchoolInfo(SchoolInfo schoolInfo) {
        this.schoolInfo = schoolInfo;
    }

    public TeacherInfo getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(TeacherInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }
}
