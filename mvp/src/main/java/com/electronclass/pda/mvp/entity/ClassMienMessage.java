package com.electronclass.pda.mvp.entity;

/**
 * 班级风采详细数据
 */
public class ClassMienMessage {
//    int id;
//    String userId;
//    private int  type;//文件类型 1-图片 2-视频
//    String url;
//    String createTime;

   private String classId;
    //班级ID

    private String content;
    //活动内容

    private String createTime;
    // 创建时间

    private String happenedDate;
    // 活动日期

    private int id;
    // 主键ID

    private String modifyTime;
    // 修改时间

    private String picUrl;
    // 文件路径

    private String title;
    // 标题



    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(String happenedDate) {
        this.happenedDate = happenedDate;
    }


    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
