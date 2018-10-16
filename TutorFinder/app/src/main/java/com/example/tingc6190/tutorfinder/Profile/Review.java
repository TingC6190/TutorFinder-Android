package com.example.tingc6190.tutorfinder.Profile;

public class Review {

    private String date;
    private String review;

    public Review() {
    }

    public Review(String date, String review) {
        this.date = date;
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public String getReview() {
        return review;
    }
}
