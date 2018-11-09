package com.example.tingc6190.tutorfinder.DataObject;

public class Transaction {

    private String firstName;
    private String lastName;
    private String price;
    private String pictureUrl;
    private String date;
    private String email;
    private String tutorUID;
    private String tutorMilli;

    public Transaction() {
    }

    public Transaction(String firstName, String lastName, String price, String pictureUrl, String date, String email, String tutorUID, String tutorMilli) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.date = date;
        this.email = email;
        this.tutorUID = tutorUID;
        this.tutorMilli = tutorMilli;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setImageUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTutorUID() {
        return tutorUID;
    }

    public void setTutorUID(String tutorUID) {
        this.tutorUID = tutorUID;
    }

    public String getTutorMilli() {
        return tutorMilli;
    }

    public void setTutorMilli(String tutorMilli) {
        this.tutorMilli = tutorMilli;
    }
}
