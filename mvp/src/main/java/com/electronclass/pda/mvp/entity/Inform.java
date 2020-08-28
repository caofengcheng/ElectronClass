package com.electronclass.pda.mvp.entity;

/**
 * 通知信息
 */
public class Inform {
    private int id;
    private String userId;
    private String userName;
    private String classId;
    private String schoolId;
    private int type;
    private int level;
    private String startTime;
    private String endTime;
    private String text;
    private String title;
    private int textType;
    private String picUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getClassId() {
        return classId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public int getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getText() {
        return text;
    }

    public int getTextType() {
        return textType;
    }
}
