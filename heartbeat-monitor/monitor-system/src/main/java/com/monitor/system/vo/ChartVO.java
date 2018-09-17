package com.monitor.system.vo;

import java.util.List;

public class ChartVO {
    private List<Long> timesList;
    private List<Long> countList;

    public List<Long> getTimesList() {
        return timesList;
    }

    public void setTimesList(List<Long> timesList) {
        this.timesList = timesList;
    }

    public List<Long> getCountList() {
        return countList;
    }

    public void setCountList(List<Long> countList) {
        this.countList = countList;
    }
}
