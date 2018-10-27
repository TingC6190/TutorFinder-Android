package com.example.tingc6190.tutorfinder.DataObject;

import com.example.tingc6190.tutorfinder.Search.Tutor;

import java.util.ArrayList;

public class Student {

    private String firstName;
    private String lastName;
    private String email;
    private String zipcode;
    private String aboutMe;
    private String picture;
    private ArrayList<Tutor> favorites;
    private ArrayList<Transaction> transactions;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String zipcode, String aboutMe,
                   String picture, ArrayList<Tutor> favorites, ArrayList<Transaction> transactions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.zipcode = zipcode;
        this.aboutMe = aboutMe;
        this.picture = picture;
        this.favorites = favorites;
        this.transactions = transactions;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ArrayList<Tutor> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Tutor> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
