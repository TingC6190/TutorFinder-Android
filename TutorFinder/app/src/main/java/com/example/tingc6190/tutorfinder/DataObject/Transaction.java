package com.example.tingc6190.tutorfinder.DataObject;

public class Transaction {

    private String firstName;
    private String lastName;
    private String price;
    private String pictureUrl;
    private String date;

    public Transaction() {
    }

    public Transaction(String firstName, String lastName, String price, String pictureUrl, String date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.date = date;
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
}
