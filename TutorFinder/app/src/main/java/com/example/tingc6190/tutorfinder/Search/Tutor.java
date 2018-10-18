package com.example.tingc6190.tutorfinder.Search;

import com.example.tingc6190.tutorfinder.DataObject.Location;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Schedule;
import com.example.tingc6190.tutorfinder.Profile.Review;

import java.util.ArrayList;

public class Tutor {
    private String firstName;
    private String lastName;
    private Double rating;
    private Integer price;
    private ArrayList<Review> reviews;
    private String subject;
    private Location location;
    private Schedule schedule;

    public Tutor() {
    }

    public Tutor(String firstName, String lastName, Double rating,
                 Integer price, ArrayList<Review> reviews, Schedule schedule, Location location, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.price = price;
        this.reviews = reviews;
        this.schedule = schedule;
        this.location = location;
        this.subject = subject;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getPrice() {
        return price;
    }

    public String getSubject() {
        return subject;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Location getLocation() {
        return location;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
