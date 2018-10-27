package com.example.tingc6190.tutorfinder.DataObject;

public class Transaction {

    private String firstName;
    private String lastName;
    private String price;
    private String pictureUrl;

    public Transaction() {
    }

    public Transaction(String firstName, String lastName, String price, String pictureUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.price = price;
        this.pictureUrl = pictureUrl;
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
}
