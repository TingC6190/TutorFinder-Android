package com.example.tingc6190.tutorfinder.DataObject;

public class MessageInfo {

    private String fromUID;
    private String toUID;
    private String message;
    private String dateTime;

    public MessageInfo() {
    }

    public MessageInfo(String fromUID, String toUID, String message, String dateTime) {
        this.fromUID = fromUID;
        this.toUID = toUID;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getFromUID() {
        return fromUID;
    }

    public void setFromUID(String fromUID) {
        this.fromUID = fromUID;
    }

    public String getToUID() {
        return toUID;
    }

    public void setToUID(String toUID) {
        this.toUID = toUID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
