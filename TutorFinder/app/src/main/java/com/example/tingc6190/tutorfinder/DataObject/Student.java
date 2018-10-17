package com.example.tingc6190.tutorfinder.DataObject;

public class Student {

    private String firstName;
    private String lastName;
    private String email;
    private String zipcode;
    private String aboutMe;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String zipcode, String aboutMe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.zipcode = zipcode;
        this.aboutMe = aboutMe;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getZipcode() {
        return zipcode;
    }

}
