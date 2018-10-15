package com.example.tingc6190.tutorfinder.Search;

public class TutorSchedule {
    private String sundayStart;
    private String sundayEnd;

    private String mondayStart;
    private String mondayEnd;

    private String tuesdayStart;
    private String tuesdayEnd;

    private String wednesdayStart;
    private String wednesdayEnd;

    private String thursdayStart;
    private String thursdayEnd;

    private String fridayStart;
    private String fridayEnd;

    private String saturdayStart;
    private String saturdayEnd;


    public TutorSchedule(String sundayStart, String sundayEnd, String mondayStart, String mondayEnd, String tuesdayStart, String tuesdayEnd, String wednesdayStart, String wednesdayEnd, String thursdayStart, String thursdayEnd, String fridayStart, String fridayEnd, String saturdayStart, String saturdayEnd) {
        this.sundayStart = sundayStart;
        this.sundayEnd = sundayEnd;
        this.mondayStart = mondayStart;
        this.mondayEnd = mondayEnd;
        this.tuesdayStart = tuesdayStart;
        this.tuesdayEnd = tuesdayEnd;
        this.wednesdayStart = wednesdayStart;
        this.wednesdayEnd = wednesdayEnd;
        this.thursdayStart = thursdayStart;
        this.thursdayEnd = thursdayEnd;
        this.fridayStart = fridayStart;
        this.fridayEnd = fridayEnd;
        this.saturdayStart = saturdayStart;
        this.saturdayEnd = saturdayEnd;
    }

    public String getSundayEnd() {
        return sundayEnd;
    }

//    public String getSundayTime()
//    {
//        sundayStart.substring()
//    }
}
