package com.example.tingc6190.tutorfinder.DataObject.Schedule;

public class Wednesday {

    private String startTime;
    private String endTime;

    public Wednesday() {
    }

    public Wednesday(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
