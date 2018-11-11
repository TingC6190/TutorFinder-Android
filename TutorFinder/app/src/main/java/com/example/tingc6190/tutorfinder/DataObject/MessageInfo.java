package com.example.tingc6190.tutorfinder.DataObject;

public class MessageInfo {

    private String fromEmail;
    private String toEmail;
    private String firstName;
    private String lastName;
    private String message;
    private String dateTime;

    public MessageInfo() {
    }

    public MessageInfo(String fromEmail, String toEmail, String firstName, String lastName, String message, String dateTime) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
