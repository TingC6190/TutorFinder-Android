package com.example.tingc6190.tutorfinder.DataObject;

public class Location {

    private String city;
    private String state;
    private String zipcode;

    public Location() {
    }

    public Location(String city, String state, String zipcode) {
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }
}
