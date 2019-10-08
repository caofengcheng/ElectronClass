package com.electronclass.pda.mvp.entity;

/**
 * 班级和校园信息
 */
public class ClassMessage {
    int        id;
    ClassInfo  classInfo;
    SchoolInfo schoolInfo;
    String        ecardNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public SchoolInfo getSchoolInfo() {
        return schoolInfo;
    }

    public void setSchoolInfo(SchoolInfo schoolInfo) {
        this.schoolInfo = schoolInfo;
    }

    public String getEcardNo() {
        return ecardNo;
    }

    public void setEcardNo(String ecardNo) {
        this.ecardNo = ecardNo;
    }
}
