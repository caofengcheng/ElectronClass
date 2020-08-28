package com.electronclass.pda.mvp.entity;

import java.util.List;

/**
 * 班级风采列表
 */
public class ClassMienPage {
    private int current;
    private int pages;
    private int total;
    private boolean searchCount;
    private int size;
    private List<ClassMienMessage> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ClassMienMessage> getRecords() {
        return records;
    }

    public void setRecords(List<ClassMienMessage> records) {
        this.records = records;
    }
}
