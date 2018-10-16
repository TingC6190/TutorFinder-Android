package com.example.tingc6190.tutorfinder.DataObject.Schedule;

public class Schedule {

    private Sunday sunday;
    private Monday monday;
    private Tuesday tuesday;
    private Wednesday wednesday;
    private Thursday thursday;
    private Friday friday;
    private Saturday saturday;

    public Schedule() {
    }

    public Schedule(Sunday sunday, Monday monday, Tuesday tuesday, Wednesday wednesday, Thursday thursday, Friday friday, Saturday saturday) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public Sunday getSunday() {
        return sunday;
    }

    public Monday getMonday() {
        return monday;
    }

    public Tuesday getTuesday() {
        return tuesday;
    }

    public Wednesday getWednesday() {
        return wednesday;
    }

    public Thursday getThursday() {
        return thursday;
    }

    public Friday getFriday() {
        return friday;
    }

    public Saturday getSaturday() {
        return saturday;
    }
}
