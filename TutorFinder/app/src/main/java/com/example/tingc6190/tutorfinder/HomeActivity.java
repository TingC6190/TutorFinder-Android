package com.example.tingc6190.tutorfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.tingc6190.tutorfinder.Account.Account;
import com.example.tingc6190.tutorfinder.DataObject.Location;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Friday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Monday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Saturday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Schedule;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Sunday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Thursday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Tuesday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Wednesday;
import com.example.tingc6190.tutorfinder.DataObject.Student;
import com.example.tingc6190.tutorfinder.DataObject.User;
import com.example.tingc6190.tutorfinder.Favorite.Favorite;
import com.example.tingc6190.tutorfinder.Message.Message;
import com.example.tingc6190.tutorfinder.Profile.Profile;
import com.example.tingc6190.tutorfinder.Profile.Review;
import com.example.tingc6190.tutorfinder.Search.Search;
import com.example.tingc6190.tutorfinder.Search.Tutor;
import com.example.tingc6190.tutorfinder.Welcome.Welcome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements Search.TutorListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase myDatabase;
    private DatabaseReference databaseReference;

    private ArrayList<Tutor> tutors = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    private Tutor tutor;
    private Review review;
    private User user;
    private String currentUserUID;
    private Student currentUserInfo;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navbar_search:
                    //launch search fragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Search())
                            .commit();
                    return true;

                case R.id.navbar_messages:
                    //launch messages fragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Message())
                            .commit();
                    return true;

                case R.id.navbar_favorites:
                    //launch favorites fragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Favorite())
                            .commit();
                    return true;

                case R.id.navbar_account:
                    //launch account fragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Account())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        currentUserUID = firebaseAuth.getUid();

//        Sunday sunday = new Sunday("10:00 AM", "11:00 PM");
//        Monday monday = new Monday("09:00 AM", "12:00 AM");
//        Tuesday tuesday = new Tuesday("09:00 AM", "12:00 AM");
//        Wednesday wednesday = new Wednesday("09:00 AM", "12:00 AM");
//        Thursday thursday = new Thursday("09:00 AM", "12:00 AM");
//        Friday friday = new Friday("09:00 AM", "12:00 AM");
//        Saturday saturday = new Saturday("10:00 AM", "12:00 AM");
//
//        Schedule schedule = new Schedule(sunday, monday, tuesday, wednesday, thursday, friday, saturday);
//
//        reviews.add(new Review("10/15/2018", "Super helpful tutor, helped me pass my next exam."));
//        reviews.add(new Review("8/13/2018", "Thank you for a great lesson."));
//
//        Location location = new Location("Orlando", "FL", "32817");
//
//        tutors.add(new Tutor("James", "Campbell",  4.5, 50, reviews, schedule, location, "Intro Java, Data Structures, Calculus"));
//        tutors.add(new Tutor("Betty", "Garner",    4.0, 35, reviews, schedule, location, "English, Grammar, Writing certified tutor"));
//        tutors.add(new Tutor("Earnest", "Walker",  5.0, 45, reviews, schedule, location, "College Student Proficient in Math and English Studies"));
//        tutors.add(new Tutor("Melody", "Mitchell", 2.5, 40, reviews, schedule, location, "Columbia Grad for English Writing"));
//        tutors.add(new Tutor("Douglas", "Hyde",    3.9, 50, reviews, schedule, location, "Biochemist Specializing in Science, Math, and Test Prep"));
//        tutors.add(new Tutor("Ryan", "Perkins",    3.7, 20, reviews, schedule, location, "Biological Science Major with 3+ years of tutoring experience"));
//        tutors.add(new Tutor("Amy", "Jackson",     4.2, 40, reviews, schedule, location, "College/High School tutor Chemistry and Algebra 1"));
//        tutors.add(new Tutor("Martin", "Bell",     2.9, 35, reviews, schedule, location, "Recent graduate in mathematics"));
//
//        students.add(new Student("Ting", "Chen", "tingc6190@gmail.com", "08817", "Likes games"));
//        students.add(new Student("Richard", "Lopez", "raynooor0@yahoo.com", "32828", "Mustangs.. Vroom Vroom!!"));
//
//
//        user = new User();
//        user.setTutors(tutors);
//        user.setStudents(students);
//
//
//        myDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = myDatabase.getReference("users");
//        myRef.setValue(user);


        getCurrentUser();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("users/tutors");

        //get our data from the database
        ValueEventListener tutorListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Tutor tutor = postSnapshot.getValue(Tutor.class);

                    tutors.add(tutor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error", "something went wrong when retrieving data");
            }
        };
        databaseReference.addValueEventListener(tutorListener);


        String accountUID = firebaseAuth.getUid();

        String email = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();



//user creation in database
//        students.add(new Student("Ting", "Chen", "tingc6190@gmail.com", "08817", "Likes games"));
//        students.add(new Student("Richard", "Lopez", "raynooor0@yahoo.com", "32828", "Mustangs.. Vroom Vroom!!"));

//        DatabaseReference studentNodeEmail = FirebaseDatabase.getInstance().getReference().child("users/students/" + accountUID);
//
//        Student student1 = new Student("Ting", "Chen", email, "08817", "Likes games and food");
//        studentNodeEmail.setValue(student1);


        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Welcome())
                .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



    //get the data of our current user
    private void getCurrentUser()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users/students/" + currentUserUID);

        //get our data from the database
        ValueEventListener tutorListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("__INSIDE_FUNCTION__", "_____________");

                currentUserInfo = dataSnapshot.getValue(Student.class);

                displayUserInfo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error", "something went wrong when retrieving data");
            }
        };
        databaseReference.addValueEventListener(tutorListener);
    }

    //sanity check
    private void displayUserInfo()
    {
        Log.d("__INSIDE_FUNCTION__", "_______DISPLAY______");
        Log.d("__FIRST__", currentUserInfo.getFirstName());
        Log.d("__LAST__", currentUserInfo.getLastName());
        Log.d("__EMAIL__", currentUserInfo.getEmail());
        Log.d("__ABOUTME__", currentUserInfo.getAboutMe());
        Log.d("__ZIPCODE__", currentUserInfo.getZipcode());
    }



    public void logOut()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void getTutor(Tutor mTutor) {

        tutor = mTutor;

        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Profile())
                .addToBackStack("profile")
                .commit();
    }

    public ArrayList<Tutor> getTutors()
    {
        return tutors;
    }

    public Tutor getTutor()
    {
        return tutor;
    }

}
