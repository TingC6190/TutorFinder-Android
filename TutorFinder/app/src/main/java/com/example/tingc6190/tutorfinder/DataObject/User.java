package com.example.tingc6190.tutorfinder.DataObject;

import com.example.tingc6190.tutorfinder.Search.Tutor;

import java.util.ArrayList;

public class User {

    private ArrayList<Student> students;
    private ArrayList<Tutor> tutors;

    public User() {
    }

    public User(ArrayList<Student> students, ArrayList<Tutor> tutors) {
        this.students = students;
        this.tutors = tutors;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Tutor> getTutors() {
        return tutors;
    }

    public void setTutors(ArrayList<Tutor> tutors) {
        this.tutors = tutors;
    }
}
