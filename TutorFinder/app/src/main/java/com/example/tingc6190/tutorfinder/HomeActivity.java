package com.example.tingc6190.tutorfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.tingc6190.tutorfinder.Account.Account;
import com.example.tingc6190.tutorfinder.Favorite.Favorite;
import com.example.tingc6190.tutorfinder.Message.Message;
import com.example.tingc6190.tutorfinder.Profile.Profile;
import com.example.tingc6190.tutorfinder.Search.Search;
import com.example.tingc6190.tutorfinder.Search.Tutor;
import com.example.tingc6190.tutorfinder.Welcome.Welcome;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements Search.TutorListener {

    private ArrayList<Tutor> tutors = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private Tutor tutor;

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

        tutors.add(new Tutor("James", "Campbell", 4.5, 50, "Intro Java, Data Structures, Calculus"));
        tutors.add(new Tutor("Betty", "Garner", 4.0, 35, "English, Grammar, Writing certified tutor"));
        tutors.add(new Tutor("Earnest", "Walker", 5.0, 45, "College Student Proficient in Math and English Studies"));
        tutors.add(new Tutor("Melody", "Mitchell", 2.5, 40, "Columbia Grad for English Writing"));
        tutors.add(new Tutor("Douglas", "Hyde", 3.9, 50, "Biochemist Specializing in Science, Math, and Test Prep"));
        tutors.add(new Tutor("Ryan", "Perkins", 3.7, 20, "Biological Science Major with 3+ years of tutoring experience"));
        tutors.add(new Tutor("Amy", "Jackson", 4.2, 40, "College/High School tutor Chemistry and Algebra 1"));
        tutors.add(new Tutor("Martin", "Bell", 2.9, 35, "Recent graduate in mathematics"));


        firebaseAuth = FirebaseAuth.getInstance();

        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Welcome())
                .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
