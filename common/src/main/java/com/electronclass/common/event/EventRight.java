package com.electronclass.common.event;

/**
 * @author caofengcheng on 2019-11-01
 */
public class EventRight {
    boolean visit;

    public EventRight(boolean visit) {
        this.visit = visit;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }
}
