package com.electronclass.pda.mvp.entity;

import java.util.List;

public class InformPage {
    private int current;
    private int pages;
    private List<Inform> records;

    private boolean searchCount;
    private int size;
    private int total;

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

    public List<Inform> getRecords() {
        return records;
    }

    public void setRecords(List<Inform> records) {
        this.records = records;
    }

    public boolean isSearchCount() {
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
