package com.example.tingc6190.tutorfinder.DataObject;

public class ReviewInfo {

    String firstName;
    String lastName;
    String date;
    String description;
    String reviewerID;

    public ReviewInfo() {
    }

    public ReviewInfo(String firstName, String lastName, String date, String description, String reviewerID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.description = description;
        this.reviewerID = reviewerID;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }
}
