package com.example.tingc6190.tutorfinder.Search;

public class Tutor {
    private String firstName;
    private String lastName;
    private Double rating;
    private Integer price;
    private String subject;

    public Tutor(String firstName, String lastName, Double rating, Integer price, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.price = price;
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
}
