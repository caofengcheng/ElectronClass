package com.electronclass.pda.mvp.entity;

/**
 * 班级年级列表
 */
public class ClassItem {
    String departId;  //部门id
    String departName;//部门名字
    String departCode;
    int    departType;//组织架构类型（1-学生；2-教师；3-家长；4-校友；5-退休教师；6-其他 )
    int    type;
    int    level;     //组织架构级别 对于大学：2-学部，3-学院，4-系，5-专业，6-班级；对于中小学：2-学部，5-年级，6-班级
    String parentId;
    String wxDepartId;
    String schoolId;
    String createTime;
    boolean isClick; //是否选中状态

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public int getDepartType() {
        return departType;
    }

    public void setDepartType(int departType) {
        this.departType = departType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getWxDepartId() {
        return wxDepartId;
    }

    public void setWxDepartId(String wxDepartId) {
        this.wxDepartId = wxDepartId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
