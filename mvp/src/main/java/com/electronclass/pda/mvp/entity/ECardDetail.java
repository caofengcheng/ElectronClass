package com.electronclass.pda.mvp.entity;


/**
 * 根据班牌设备号查询信息
 */
public class ECardDetail {
    private String classId;
    private String className;
    private String ecardNo;
    private int id;
    private String ip;
    private int isPassword;
    private String password;
    private String schoolId;
    private String schoolName;
    private  int type;
    private String updateTime;


    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEcardNo() {
        return ecardNo;
    }

    public void setEcardNo(String ecardNo) {
        this.ecardNo = ecardNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getIsPassword() {
        return isPassword;
    }

    public void setIsPassword(int isPassword) {
        this.isPassword = isPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
