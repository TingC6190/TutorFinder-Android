package com.example.tingc6190.tutorfinder.Profile;

import java.util.ArrayList;

public class Review extends ArrayList<Review> {

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

    public void setDate(String date) {
        this.date = date;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
