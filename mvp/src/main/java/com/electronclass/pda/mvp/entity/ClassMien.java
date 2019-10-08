package com.electronclass.pda.mvp.entity;

import java.util.List;

/**
 * 校园风采列表
 */
public class ClassMien {
    int  pageStart;
    int  pageSize;
    int  total;
    List<ClassMienMessage> data;

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ClassMienMessage> getData() {
        return data;
    }

    public void setData(List<ClassMienMessage> data) {
        this.data = data;
    }
}
