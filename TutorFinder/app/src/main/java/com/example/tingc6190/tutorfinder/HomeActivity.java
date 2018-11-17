package com.example.tingc6190.tutorfinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.Account.Account;
import com.example.tingc6190.tutorfinder.Account.Transactions;
import com.example.tingc6190.tutorfinder.DataObject.AllMessageInfo;
import com.example.tingc6190.tutorfinder.DataObject.Location;
import com.example.tingc6190.tutorfinder.DataObject.MessageInfo;
import com.example.tingc6190.tutorfinder.DataObject.ReviewInfo;
import com.example.tingc6190.tutorfinder.DataObject.Student;
import com.example.tingc6190.tutorfinder.DataObject.Transaction;
import com.example.tingc6190.tutorfinder.DataObject.User;
import com.example.tingc6190.tutorfinder.Favorite.Favorite;
import com.example.tingc6190.tutorfinder.Message.Message;
import com.example.tingc6190.tutorfinder.Message.MessageList;
import com.example.tingc6190.tutorfinder.Payment.Payment;
import com.example.tingc6190.tutorfinder.Profile.Profile;
import com.example.tingc6190.tutorfinder.Review.Review;
import com.example.tingc6190.tutorfinder.Search.Search;
import com.example.tingc6190.tutorfinder.Search.Tutor;
import com.example.tingc6190.tutorfinder.Setting.Setting;
import com.example.tingc6190.tutorfinder.TutorForm.TutorFormBackground;
import com.example.tingc6190.tutorfinder.TutorForm.TutorFormInitial;
import com.example.tingc6190.tutorfinder.Welcome.Welcome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements Search.TutorListener,
        TutorFormInitial.TutorFormListener, TutorFormBackground.BackgroundFormListener,
        Account.AccountListener, Setting.SettingListener, Profile.ProfileListener,
        Favorite.FavoriteListener, Payment.PaymentListener, Welcome.WelcomeListener,
        Review.ReviewListener, Message.MessageListener, MessageList.MessageListListener,
        Transactions.TransactionListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase myDatabase;
    private DatabaseReference databaseReference;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;

    private ArrayList<Tutor> tutors = new ArrayList<>();
    //private ArrayList<Review> reviews = new ArrayList<>();
    private Tutor tutor;
    //private Review review;
    private User user;
    private String currentUserUID;
    private Student currentUserInfo;
    private Tutor tutorFromInitialSetup;
    private String email;
    private boolean isTutor;
    //private MainActivity mainActivity;
    private ArrayList<String> allTutorUID = new ArrayList<>();
    private ArrayList<Tutor> favoriteTutors = new ArrayList<>();
    private ArrayList<Transaction> userTransactions = new ArrayList<>();
    private ArrayList<Tutor> tutors_duplicate = new ArrayList<>();
    private ArrayList<ReviewInfo> reviews = new ArrayList<>();
    private Tutor currentTutorToEdit;
    private int selectedTutorReviewCount;
    private ArrayList<ReviewInfo> currentTutorToEditReviews = new ArrayList<>();
    ArrayList<ReviewInfo> tempReview = new ArrayList<>();
    ArrayList<MessageInfo> messages = new ArrayList<>();
    ArrayList<ArrayList<MessageInfo>> allMessages = new ArrayList<>();
    ArrayList<AllMessageInfo> testAllMessages = new ArrayList<>();
    ArrayList<String> messageTutorUID = new ArrayList<>();
    private ArrayList<Student> allStudentData = new ArrayList<>();
    private ArrayList<String> allStudentUID = new ArrayList<>();
    int messagePosition;
    boolean hasPositionFromListClick = false;
    boolean hasTappedTutorMessageButton = false;
    int userSelectedZip;

    LocationManager locationManager;
    Location lastKnown;
    double longitude;
    double latitude;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navbar_search:
                    //launch search fragment
                    getFragmentManager().popBackStack();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Search(), "tutorListFragment")
                            .commit();
                    return true;

                case R.id.navbar_messages:
                    //launch messages fragment
                    getFragmentManager().popBackStack();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new MessageList())
                            .commit();
                    return true;

                case R.id.navbar_favorites:
                    //launch favorites fragment

                    getFavorites();

                    getFragmentManager().popBackStack();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Favorite(), "favoriteFragment")
                            .commit();
                    return true;

                case R.id.navbar_account:
                    //launch account fragment
                    getFragmentManager().popBackStack();
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

        email = firebaseAuth.getCurrentUser().getEmail();

        tutorFromInitialSetup = new Tutor();

        getAllStudentDataAndUID();

        pullAllTutors();

        Log.d("__NEWACTIVITYCREATED__", "_________");

       //checkIfUserIsTutor();


        //sendPasswordChange();

        //Log.d("__CURRENT_UID__", firebaseAuth.get);


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

        getAllMessages();

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

    //TESTING THIS!!!!!!
//    @Override
//    protected void onPause() {
//        super.onPause();
//        getAllMessages();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        getAllMessages();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        getAllMessages();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//       // finish();
//        //startActivity(getIntent());
////        getAllMessages();
//    }

    //get the data of our current user
    private void getCurrentUser()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users/students/" + currentUserUID);

        //get our data from the database
        ValueEventListener tutorListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("__INSIDE_FUNCTION__", "STUDENT DATA CHANGED");

                currentUserInfo = dataSnapshot.getValue(Student.class);

                //favoriteTutors = currentUserInfo.getFavorites();
                //userTransactions = currentUserInfo.getTransactions();

                //displayUserInfo();

//                Log.d("__FIRST__", currentUserInfo.getFirstName());
//                Log.d("__LAST__", currentUserInfo.getLastName());
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
    public void getTutor(final Tutor mTutor) {

        tutor = mTutor;

        hasPositionFromListClick = false;
        hasTappedTutorMessageButton = true;

        //get the num of reviews for the selected tutor
//        final DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + mTutor.getTutorUID() + "/reviews");
//
//        ValueEventListener reviewListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshotReview) {
//                if (reviewRef.getKey() != null)
//                {
//                    int reviewCounter = 0;
//                    for (DataSnapshot reviewSnapshot : dataSnapshotReview.getChildren())
//                    {
//                        reviewCounter = reviewCounter + 1;
//                    }
//
//                    selectedTutorReviewCount = reviewCounter;
//
//                    //Log.d("__CHECKING_FOR_REVIEW__", tutorUID + " HAS " + reviewCounter + " REVIEWS AND " + totalStar + " TOTAL STARS");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        reviewRef.addListenerForSingleValueEvent(reviewListener);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Profile())
                .addToBackStack("profile")
                .commit();
    }

    @Override
    public void getTutorToUpdate(Tutor tutor) {

//        DatabaseReference createTutor = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + currentUserUID);
//
//        createTutor.setValue(tutor);
        tutorFromInitialSetup = tutor;
    }

    @Override
    public void getBackgroundFormListener(String city, String state, String zipcode, String license) {

        //DatabaseReference createTutor = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + currentUserUID);

        Log.d("_________", city + state + zipcode);

        tutorFromInitialSetup.setLocation(new Location(city, state, zipcode));
        tutorFromInitialSetup.setLicenseNumber(license);
        tutorFromInitialSetup.setEmail(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail());
        tutorFromInitialSetup.setFirstName(currentUserInfo.getFirstName());
        tutorFromInitialSetup.setLastName(currentUserInfo.getLastName());
        tutorFromInitialSetup.setPicture(currentUserInfo.getPicture());

        pushTutorToDatabase();


        //createTutor.setValue(tutorFromInitialSetup);
    }


    @Override
    public void getAccountListener(String imageUrl) {
        //push image to the current tutor
        //tutorFromInitialSetup = tutor;

        //tutorFromInitialSetup.setPicture(imageUrl);

        if (isTutor)
        {
            updateTutorPicture(imageUrl);
            updateStudentPicture(imageUrl);
        }
        else
        {
            updateStudentPicture(imageUrl);
        }
        //updateTutorPicture(imageUrl);
    }



    @Override
    public void getUpdateStudent(String first, String last, String aboutMe) {
        currentUserInfo.setFirstName(first);
        currentUserInfo.setLastName(last);
        currentUserInfo.setAboutMe(aboutMe);

//        Student newStudent = currentUserInfo;
//        Log.d("__FIRSTNAME__", currentUserInfo.getFirstName());
//        Log.d("__LASTNAME__", currentUserInfo.getLastName());
//        Log.d("__ABOUTME__", currentUserInfo.getAboutMe());
//        Log.d("__ZIPCODE__", currentUserInfo.getZipcode());
//        Log.d("__PICTURE__", currentUserInfo.getPicture());
//        Log.d("__EMAIL__", currentUserInfo.getEmail());

        if (isTutor)
        {
            Tutor tempTutor = getCurrentTutorToEdit();

            tempTutor.setFirstName(first);
            tempTutor.setLastName(last);
            tempTutor.setAboutMe(aboutMe);


            DatabaseReference tutorDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + currentUserUID);

            tutorDatabaseRef.setValue(tempTutor);
        }

        DatabaseReference studentDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + currentUserUID);

        studentDatabaseRef.setValue(currentUserInfo);


        if (currentTutorToEditReviews.size() > 0)
        {
            Log.d("CHECKFORCOUNTER", "IS IT 00000000?");
            for (int i = 0; i < currentTutorToEditReviews.size(); i++)
            {
                String uniqueID = currentTutorToEditReviews.get(i).getReviewerID() + "_" + currentTutorToEditReviews.get(i).getTimeInMilli();
                DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + currentTutorToEdit.getTutorUID() + "/reviews/" + uniqueID);

                reviewRef.setValue(currentTutorToEditReviews.get(i));

                //Log.d("REVIEWDETAIL", re)
            }

            //DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + tutor.getTutorUID() + "/reviews");

            //reviewRef.setValue(reviews);
        }

        if (userTransactions.size() > 0)
        {
            DatabaseReference transactionsRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + firebaseAuth.getUid() + "/transactions");
            transactionsRef.setValue(userTransactions);
        }

        if (favoriteTutors.size() > 0)
        {
            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + firebaseAuth.getUid() + "/favorites");
            favoritesRef.setValue(favoriteTutors);
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Account())
                .commit();
    }

    @Override
    public void addTutorToFavorite(Tutor tutorToAdd)
    {
        String tutorUID = tutorToAdd.getTutorUID();

//        ArrayList<Tutor> tempFav = new ArrayList<>();
//
//        if (favoriteTutors == null)
//        {
//            favoriteTutors = tempFav;
//        }
//
//        favoriteTutors.add(tutorToAdd);

        DatabaseReference favoriteRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + currentUserUID + "/favorites/" + tutorUID);

        favoriteRef.setValue(tutorToAdd);
    }

    @Override
    public void removeTutorFromFavorite(Tutor tutorToRemove) {

        String tutorUID = tutorToRemove.getTutorUID();

//        for (int i = 0; i < favoriteTutors.size(); i++)
//        {
//            //remove tutor at position if emails match
//            if (favoriteTutors.get(i).getEmail().equals(tutorEmail))
//            {
//                favoriteTutors.remove(i);
//            }
//        }

        DatabaseReference favoriteRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + currentUserUID + "/favorites/" + tutorUID);

        favoriteRef.removeValue();
    }


    @Override
    public void getFavoriteTutor(Tutor mTutor) {

        tutor = mTutor;

        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Profile())
                .addToBackStack("favorite")
                .commit();
    }

    @Override
    public void getPaymentInfo(String firstName, String lastName, String price, String pictureUrl, String date, String email, String tutorUID, String uniqueTime)
    {
//        ArrayList<Transaction> tempTransaction = new ArrayList<>();
//
//        if (userTransactions == null)
//        {
//            userTransactions = tempTransaction;
//        }

//        userTransactions.add(new Transaction(firstName, lastName, price, pictureUrl, date, email));

        Transaction transaction = new Transaction(firstName, lastName, price, pictureUrl, date, email, tutorUID, uniqueTime);
        Transaction tutorTransaction = new Transaction(currentUserInfo.getFirstName(), currentUserInfo.getLastName(), price,
                currentUserInfo.getPicture(), date, firebaseAuth.getCurrentUser().getEmail(), firebaseAuth.getUid(), uniqueTime);

        //long time = System.currentTimeMillis();

        String transactionID = tutorUID + "_" + uniqueTime;
        String tutorTransactionID = firebaseAuth.getUid() + "_" + uniqueTime;
        //upload to db
        DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + currentUserUID + "/transactions/" + transactionID);
        DatabaseReference tutorTransactionRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + tutorUID + "/transactions/" + tutorTransactionID);

        transactionRef.setValue(transaction);
        tutorTransactionRef.setValue(tutorTransaction);
    }

    @Override
    public void getSearchSettings(String subject, String zipcode) {

        userSelectedZip = Integer.parseInt(zipcode);

        if (subject.equals("Any"))
        {
            tutors = tutors_duplicate;
        }
        else
        {
            ArrayList<Tutor> filteredTutors = new ArrayList<>();

            for (int i = 0; i < tutors_duplicate.size(); i++)
            {
                if (tutors_duplicate.get(i).getSubject().equals(subject))
                {
                    filteredTutors.add(tutors_duplicate.get(i));
                }
            }
            tutors = filteredTutors;
        }

        for (int i = 0; i < tutors.size(); i++)
        {
            if (tutors.get(i).getTutorUID().equals(currentUserUID))
            {
                currentTutorToEdit = new Tutor();
                currentTutorToEdit = tutors.get(i);

                tutors.remove(i);
            }
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Search())
                .commit();
    }


    @Override
    public void pushReview(String firstName, String lastName, String currentDate, String description, String tutorUID, int rate) {

        //reviews = new ArrayList<>();

        //getTutorReviews(tutorUID);



//        ArrayList<Tutor> tempFav = new ArrayList<>();
//
//        if (favoriteTutors == null)
//        {
//            favoriteTutors = tempFav;
//        }
//
//        favoriteTutors.add(tutorToAdd);

        long time = System.currentTimeMillis();

        String reviewerID = currentUserUID + "_" + time;
        ReviewInfo review = new ReviewInfo(firstName, lastName, currentDate, description, currentUserUID, rate, time);

        DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + tutorUID + "/reviews/" + reviewerID);

        reviewRef.setValue(review);




        //pushReviewToTutor(firstName, lastName, currentDate, description, tutorUID);
//        pushReviewToTutor(firstName, lastName, currentDate, description, tutorUID);

        //ArrayList<ReviewInfo> reviews = new ArrayList<>();

//        ReviewInfo review = new ReviewInfo();
//        review.setFirstName(firstName);
//        review.setLastName(lastName);
//        review.setDate(currentDate);
//        review.setDescription(description);
//
//        Log.d("__REVIEW__", firstName + " " + lastName + " " + currentDate + " " + description + " // " + tutorUID );
//
//        reviews.add(review);
//
//        DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + tutorUID + "/reviews");
//
//        reviewRef.setValue(reviews);
//
//        Log.d("___TEST____","UPLOAD");
    }


    @Override
    public void pullReviewOfTutor(String tutorUID) {

        //getTutorReviews(tutorUID);

        //ArrayList<ReviewInfo> reviewsDuplicate = new ArrayList<>();

        for (int i = 0; i < tutors.size(); i++)
        {
//            if (tutors.get(i).getReviews() != null)
//            {
//                if (tutors.get(i).getTutorUID().equals(tutorUID))
//                {
//                    reviews = tutors.get(i).getReviews();
//                }
//            }
//            else
//            {
//                reviews = new ArrayList<>();
//                Log.d("________", "no review node");
//            }
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Review())
                .addToBackStack("review")
                .commit();
    }


    //work on this!!!
    @Override
    public void pushMessage(Student fromStudent, Tutor toTutor, String dateTime, String message) {

//        Log.d("__MESSAGEINHOME__", "Message from " + fromStudent.getEmail() + " to " + toTutor.getEmail() + " at " + dateTime);
//        Log.d("__MESSAGEINHOME__", message);

        //CHECK THIS CONDITION STATEMENT***************************************
        if (hasTappedTutorMessageButton && !hasPositionFromListClick)
        {
            Log.d("__PUSH_CONDITION__", "MESSAGE BUTTON");
            DatabaseReference userMessageRef = FirebaseDatabase.getInstance().getReference().child("users/messages/" + firebaseAuth.getUid() + "/" + toTutor.getTutorUID());
            DatabaseReference tutorMessageRef = FirebaseDatabase.getInstance().getReference().child("users/messages/" + toTutor.getTutorUID() + "/" + firebaseAuth.getUid());

            userMessageRef.push().setValue(new MessageInfo(fromStudent.getEmail(), toTutor.getEmail(), fromStudent.getFirstName(), fromStudent.getLastName(), message, dateTime));
            tutorMessageRef.push().setValue(new MessageInfo(fromStudent.getEmail(), toTutor.getEmail(), fromStudent.getFirstName(), fromStudent.getLastName(), message, dateTime));
        }
        else if (!hasTappedTutorMessageButton && hasPositionFromListClick)
        {
            Log.d("__PUSH_CONDITION__", "LIST CLICK");
            DatabaseReference userMessageRef = FirebaseDatabase.getInstance().getReference().child("users/messages/" + firebaseAuth.getUid() + "/" + messageTutorUID.get(messagePosition));
            DatabaseReference tutorMessageRef = FirebaseDatabase.getInstance().getReference().child("users/messages/" + messageTutorUID.get(messagePosition) + "/" + firebaseAuth.getUid());

            userMessageRef.push().setValue(new MessageInfo(fromStudent.getEmail(), "", fromStudent.getFirstName(), fromStudent.getLastName(), message, dateTime));
            tutorMessageRef.push().setValue(new MessageInfo(fromStudent.getEmail(), "", fromStudent.getFirstName(), fromStudent.getLastName(), message, dateTime));
        }




    }


    @Override
    public void getTutorMessage(ArrayList<MessageInfo> tutorMessages, int position) {

        hasPositionFromListClick = true;
        hasTappedTutorMessageButton = false;

        messagePosition = position;

        Log.d("__POSITION__", String.valueOf(messagePosition));

        messages = tutorMessages;

        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Message(), "messageFragment")
                .addToBackStack("message")
                .commit();

    }

    @Override
    public void removeTransaction(Transaction transaction) {

        String tutorUID = transaction.getTutorUID();
        String uniqueTime = transaction.getTutorMilli();

        String transactionID = tutorUID + "_" + uniqueTime;
        String tutorTransactionID = firebaseAuth.getUid() + "_" + uniqueTime;

        DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + currentUserUID + "/transactions/" + transactionID);
        DatabaseReference tutorTransactionRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + tutorUID + "/transactions/" + tutorTransactionID);

        transactionRef.removeValue();
        tutorTransactionRef.removeValue();
    }

    public void pullAllTutors()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users/tutors");

        //get our data from the database
        ValueEventListener tutorListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Tutor> getAllTutors = new ArrayList<>();
                ArrayList<String> tutorsUID = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    final Tutor mTutor = postSnapshot.getValue(Tutor.class);
                    final String tutorUID = postSnapshot.getKey();

                    //********* Loop through each tutor to get their review information *********//
                    final DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + tutorUID + "/reviews");

                    ValueEventListener reviewListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshotReview) {
                            if (reviewRef.getKey() != null)
                            {
                                int reviewCounter = 0;
                                int totalStar = 0;
                                for (DataSnapshot reviewSnapshot : dataSnapshotReview.getChildren())
                                {
                                    ReviewInfo review = reviewSnapshot.getValue(ReviewInfo.class);
                                    assert review != null;
                                    totalStar = totalStar + review.getRate();
                                    reviewCounter = reviewCounter + 1;

                                    if (tutorUID.equals(firebaseAuth.getUid()))
                                    {
                                        tempReview.add(review);
                                    }
                                }
                                currentTutorToEditReviews = tempReview;

                                if (reviewCounter > 0)
                                {
                                    Double averageRating = (double) totalStar / (double) reviewCounter;
                                    assert mTutor != null;
                                    mTutor.setRating(averageRating);
                                    mTutor.setReviewCounter(reviewCounter);
                                }
                                else
                                {
                                    mTutor.setRating(0.0);
                                    mTutor.setReviewCounter(0);
                                }


                                Log.d("__CHECKING_FOR_REVIEW__", tutorUID + " HAS " + reviewCounter + " REVIEWS AND " + totalStar + " TOTAL STARS");
                            }
                            else
                            {
                                Log.d("__CHECKING_FOR_REVIEW__", tutorUID + " no REVIEW");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    reviewRef.addValueEventListener(reviewListener);

                    //allTutorUID.ad

                    //tutors = new ArrayList<>();
                    //tutors.add(tutor);

                    getAllTutors.add(mTutor);
                    tutorsUID.add(tutorUID);

                }

                for (int i = 0; i < getAllTutors.size(); i++)
                {
                    if (getAllTutors.get(i).getTutorUID().equals(currentUserUID))
                    {
                        currentTutorToEdit = new Tutor();
                        currentTutorToEdit = getAllTutors.get(i);

                        getAllTutors.remove(i);

                    }
                }

                tutors = getAllTutors;
                tutors_duplicate = getAllTutors;
                allTutorUID = tutorsUID;
                checkIfUserIsTutor();


                if (getFragmentManager().findFragmentByTag("tutorListFragment") != null &&
                        getFragmentManager().findFragmentByTag("tutorListFragment").isVisible())
                {
                    getFragmentManager().popBackStack();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Search(), "tutorListFragment")
                            .commit();
                }

                Log.d("__DATABASE__", "__HAS_UPDATED__");

                //Log.d("_______test______", String.valueOf(tutors.get(0).toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error", "something went wrong when retrieving data");
            }
        };
        databaseReference.addValueEventListener(tutorListener);
    }


    public void pushTutorToDatabase()
    {


        //FIX THIS!!!!!!!!!!!!!!!!!!!!!
        //only update, do not recreate
        DatabaseReference createTutor = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + currentUserUID);

        createTutor.setValue(tutorFromInitialSetup);

//        if (currentTutorToEditReviews.size() > 0)
//        {
//            for (int i = 0; i < currentTutorToEditReviews.size(); i++)
//            {
//                String desc = currentTutorToEditReviews.get(i).getDescription();
//                String time = String.valueOf(currentTutorToEditReviews.get(i).getTimeInMilli());
//                String rate = String.valueOf(currentTutorToEditReviews.get(i).getRate());
//
//                Log.d("HOPINGTHISWORKSSS", rate + " " + desc + " " + time);
//            }
//        }
//        else
//        {
//            Log.d("HOPINGTHISWORKSSS", "AHHHHH");
//        }

        //SystemClock.sleep(7000);

        if (currentTutorToEditReviews.size() > 0)
        {
            Log.d("CHECKFORCOUNTER", "IS IT 00000000?");
            for (int i = 0; i < currentTutorToEditReviews.size(); i++)
            {
                String uniqueID = currentTutorToEditReviews.get(i).getReviewerID() + "_" + currentTutorToEditReviews.get(i).getTimeInMilli();
                DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + currentTutorToEdit.getTutorUID() + "/reviews/" + uniqueID);

                reviewRef.setValue(currentTutorToEditReviews.get(i));

                //Log.d("REVIEWDETAIL", re)
            }

            //DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + tutor.getTutorUID() + "/reviews");

            //reviewRef.setValue(reviews);
        }

        //getFragmentManager().popBackStack("launch", 1);
        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Account())
                .commit();
    }

    private void updateTutorPicture(String url)
    {
        DatabaseReference tutorPictureRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + currentUserUID + "/picture");

        tutorPictureRef.setValue(url);
    }

    private void updateStudentPicture(String url)
    {
        DatabaseReference studentPictureRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + currentUserUID + "/picture");

        studentPictureRef.setValue(url);
    }

    public void sendPasswordResetEmail()
    {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(HomeActivity.this, "Email has been sent.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkIfUserIsTutor()
    {
       for (int i = 0; i < allTutorUID.size(); i++)
       {
           String uid = allTutorUID.get(i);

           if (currentUserUID.equals(uid))
           {
               isTutor = true;
               Log.d("__ISTUTOR__", currentUserUID + " == " + uid + " " + isTutor);
               break;
           }
           else
           {
               isTutor = false;
               Log.d("__ISTUTOR__", currentUserUID + " != " + uid + " " + isTutor);
           }
       }
    }

    public Tutor getTutorToEdit()
    {
        Tutor newTutor = new Tutor();

        for (int i = 0; i < tutors_duplicate.size(); i++)
        {
            String email = tutors_duplicate.get(i).getEmail();

            if (email.equals(getStudentEmail()))
            {
                newTutor = tutors_duplicate.get(i);
            }
        }

        return newTutor;
    }

    private void getTutorReviews(final String tutorUID)
    {
        DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + tutorUID + "/reviews");

        //get our data from the database
        ValueEventListener reviewListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                ArrayList<Tutor> getAllTutors = new ArrayList<>();
//                ArrayList<String> tutorsUID = new ArrayList<>();

                ArrayList<ReviewInfo> getAllReviews = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
//                    Tutor mTutor = postSnapshot.getValue(Tutor.class);
//                    String tutorUID = postSnapshot.getKey();
//
//
//                    //allTutorUID.ad
//
//                    //tutors = new ArrayList<>();
//                    //tutors.add(tutor);
//
//                    getAllTutors.add(mTutor);
//                    tutorsUID.add(tutorUID);

                    ReviewInfo review = postSnapshot.getValue(ReviewInfo.class);
                    getAllReviews.add(review);
                }

                //Log.d("__REVIEW_DB__", String.valueOf(reviews.size()));
                Log.d("___TEST____","DOWNLOAD");
                reviews = getAllReviews;
                //Log.d("__REVIEW_DB__", "DID NOT BREAK");
//                tutors = getAllTutors;
//                tutors_duplicate = getAllTutors;
//                allTutorUID = tutorsUID;
//                checkIfUserIsTutor();

                //Log.d("__DATABASE__", "__HAS_UPDATED__");

                //Log.d("_______test______", String.valueOf(tutors.get(0).toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error", "something went wrong when retrieving data");
            }
        };
        reviewRef.addValueEventListener(reviewListener);
    }

    private void pushReviewToTutor(String firstName, String lastName, String currentDate, String description, String tutorUID)
    {
        ReviewInfo review = new ReviewInfo();
        review.setFirstName(firstName);
        review.setLastName(lastName);
        review.setDate(currentDate);
        review.setDescription(description);

        Log.d("__REVIEW__", firstName + " " + lastName + " " + currentDate + " " + description + " // " + tutorUID );

        reviews.add(review);

        DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + tutorUID + "/reviews");

        reviewRef.setValue(reviews);

        Log.d("___TEST____","UPLOAD");

        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Review())
                .addToBackStack("review")
                .commit();
    }


    public void getLocationOfUser()
    {
        Log.d("_______", "getLocationOfUser");

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(android.location.Location location) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    2000,
                    10.0f,
                    locationListener);

//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            List<Address> addressList = null;
//            try {
//                addressList = geocoder.getFromLocation(latitude, longitude, 1);
//
//                if (addressList.get(0) != null)
//                {
//                    String zipcode = addressList.get(0).getPostalCode();
//
//                    Log.d("______POSTAL_____", zipcode);
//                }
//
////                Log.d("__ADDRESS__", addressList.toString());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            Log.d("__LATITUDE__", String.valueOf(latitude));
            Log.d("__LONGITUDE__", String.valueOf(longitude));

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try
            {
                List<Address> addressList = geocoder.getFromLocation(40.514658, -74.393104, 1);

                if (addressList.size() > 0)
                {
                    Log.d("_______", "HAS ADDRESS");
                }
                else
                {
                    Log.d("_______", "no ADDRESS");
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void getFavorites()
    {
        DatabaseReference favoriteRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + currentUserUID + "/favorites");

        //get our data from the database
        ValueEventListener favListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("__INSIDE_FUNCTION__", "FAVORITE DATA CHANGED");

                ArrayList<Tutor> favTutor = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Tutor tutor = postSnapshot.getValue(Tutor.class);

                    favTutor.add(tutor);
                }

                favoriteTutors = favTutor;

                //currentUserInfo = dataSnapshot.getValue(Student.class);
                //favoriteTutors = currentUserInfo.getFavorites();
                //userTransactions = currentUserInfo.getTransactions();

                //displayUserInfo();

//                Log.d("__FIRST__", currentUserInfo.getFirstName());
//                Log.d("__LAST__", currentUserInfo.getLastName());

                if (getFragmentManager().findFragmentByTag("favoriteFragment") != null &&
                        getFragmentManager().findFragmentByTag("favoriteFragment").isVisible())
                {
                    getFragmentManager().popBackStack();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Favorite(), "favoriteFragment")
                            .commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error", "something went wrong when retrieving data");
            }
        };
        favoriteRef.addValueEventListener(favListener);
    }


    public void getTransactions()
    {
        DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + currentUserUID + "/transactions");


        ValueEventListener transactionListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("__INSIDE_FUNCTION__", "TRANSACTION DATA CHANGED");

                ArrayList<Transaction> transactions = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Transaction transaction = postSnapshot.getValue(Transaction.class);

                    transactions.add(transaction);
                }

                userTransactions = transactions;

                if (getFragmentManager().findFragmentByTag("transactionsFragment") != null &&
                        getFragmentManager().findFragmentByTag("transactionsFragment").isVisible())
                {
                    getFragmentManager().popBackStack();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Transactions(), "transactionsFragment")
                            .commit();
                }

                //currentUserInfo = dataSnapshot.getValue(Student.class);
                //favoriteTutors = currentUserInfo.getFavorites();
                //userTransactions = currentUserInfo.getTransactions();

                //displayUserInfo();

//                Log.d("__FIRST__", currentUserInfo.getFirstName());
//                Log.d("__LAST__", currentUserInfo.getLastName());

//                if (getFragmentManager().findFragmentByTag("favoriteFragment") != null &&
//                        getFragmentManager().findFragmentByTag("favoriteFragment").isVisible())
//                {
//                    getFragmentManager().popBackStack();
//                    getFragmentManager().beginTransaction()
//                            .replace(R.id.content_container, new Favorite(), "favoriteFragment")
//                            .commit();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error", "something went wrong when retrieving data");
            }
        };
        transactionRef.addValueEventListener(transactionListener);

    }

    public void getTutorReviews()
    {
        DatabaseReference reviewsRef = FirebaseDatabase.getInstance().getReference().child("users/tutors/" + tutor.getTutorUID() + "/reviews");

        ValueEventListener reviewListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("__INSIDE_FUNCTION__", "REVIEWS DATA CHANGED");

                //ArrayList<Transaction> transactions = new ArrayList<>();
                ArrayList<ReviewInfo> tempReviews = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    //Transaction transaction = postSnapshot.getValue(Transaction.class);
                    ReviewInfo review = postSnapshot.getValue(ReviewInfo.class);
                    tempReviews.add(review);
                }

                reviews = tempReviews;


                if (getFragmentManager().findFragmentByTag("reviewsFragment") != null &&
                        getFragmentManager().findFragmentByTag("reviewsFragment").isVisible())
                {
                    getFragmentManager().popBackStack();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Review(), "reviewsFragment")
                            .addToBackStack("reviews")
                            .commit();
                }
//                if (getFragmentManager().findFragmentByTag("transactionsFragment") != null &&
//                        getFragmentManager().findFragmentByTag("transactionsFragment").isVisible())
//                {
//                    getFragmentManager().popBackStack();
//                    getFragmentManager().beginTransaction()
//                            .replace(R.id.content_container, new Transactions(), "transactionsFragment")
//                            .commit();
//                }

                //currentUserInfo = dataSnapshot.getValue(Student.class);
                //favoriteTutors = currentUserInfo.getFavorites();
                //userTransactions = currentUserInfo.getTransactions();

                //displayUserInfo();

//                Log.d("__FIRST__", currentUserInfo.getFirstName());
//                Log.d("__LAST__", currentUserInfo.getLastName());

//                if (getFragmentManager().findFragmentByTag("favoriteFragment") != null &&
//                        getFragmentManager().findFragmentByTag("favoriteFragment").isVisible())
//                {
//                    getFragmentManager().popBackStack();
//                    getFragmentManager().beginTransaction()
//                            .replace(R.id.content_container, new Favorite(), "favoriteFragment")
//                            .commit();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error", "something went wrong when retrieving data");
            }
        };
        reviewsRef.addValueEventListener(reviewListener);


    }

    public void getAllStudentDataAndUID()
    {
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference().child("users/students");

        //get our data from the database
        ValueEventListener studentListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> tempStudentKeys = new ArrayList<>();
                ArrayList<Student> tempAllStudentData = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    String tempKey = dataSnapshot.getKey();
                    Student tempStudent = dataSnapshot.getValue(Student.class);

                    tempStudentKeys.add(tempKey);
                    tempAllStudentData.add(tempStudent);
                }
                allStudentUID = tempStudentKeys;
                allStudentData = tempAllStudentData;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error", "something went wrong when retrieving data");
            }
        };
        studentRef.addValueEventListener(studentListener);
    }


    public void getAllMessages()
    {
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("users/tutors");
        allMessages = new ArrayList<>();
        messageTutorUID = new ArrayList<>();

        final DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference().child("users/messages/" + getCurrentUserUID());

        //get our data from the database
        ValueEventListener messageListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                //final ArrayList<AllMessageInfo> tempAllMessages = new ArrayList<>();

                final ArrayList<ArrayList<MessageInfo>> tempAllMessages = new ArrayList<>();
                final ArrayList<AllMessageInfo> blah = new ArrayList<>();
                ArrayList<String> tempMessageTutorUID = new ArrayList<>();

                final long dbSize = dataSnapshot.getChildrenCount();
                Log.d("SIZEEEEEE", String.valueOf(dbSize));

                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    //Log.d("__TEMPMESSAGESIZE1__", String.valueOf(tempMessages.size()));
                    String uid = postSnapshot.getKey();
                    messageTutorUID.add(uid);

                    Log.d("__TESTFORUID__", uid);

                    String tempUID = postSnapshot.getKey();

                    DatabaseReference childMessageRef = FirebaseDatabase.getInstance().getReference().child("users/messages/" + getCurrentUserUID() + "/" + uid);

                    ValueEventListener childMessageListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot messageDataSnapshot) {

                            ArrayList<MessageInfo> tempMessages = new ArrayList<>();


                            for (DataSnapshot messagePostSnapshot : messageDataSnapshot.getChildren())
                            {
                                //Log.d("__TEMPMESSAGESIZE2__", String.valueOf(tempMessages.size()));
                                //String checkID =  messagePostSnapshot.getValue();

                                //Log.d("__TESTFORUID__", checkID);

                                //NEEDS SOME FIX
                                final MessageInfo message = messagePostSnapshot.getValue(MessageInfo.class);

                                //final String tutorUID = postSnapshot.getKey();

                                Log.d("__INSIDE_MESSAGE_REF__", message.getMessage());

                                tempMessages.add(message);
                            }
                            //messages = tempMessages;
                            //tempAllMessages.add(tempMessages);
                            //tempAllMessages.add(tempMessages);

                            tempAllMessages.add(tempMessages);
                            //blah.add(tempMessages, "somethingcool");
                            allMessages = tempAllMessages;
                            if (allMessages.size() == dbSize)
                            {
                                messages = allMessages.get(messagePosition);
                                //messages = allMessages.get(messagePosition);
                                if (getFragmentManager().findFragmentByTag("messageFragment") != null &&
                                        getFragmentManager().findFragmentByTag("messageFragment").isVisible())
                                {
                                    getFragmentManager().popBackStack();
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.content_container, new Message(), "messageFragment")
                                            .commit();
                                }
                            }


                            Log.d("__REFRESH_TEMPALL__", String.valueOf(allMessages.size()));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    childMessageRef.addListenerForSingleValueEvent(childMessageListener);

                    //messageTutorUID.add(tempUID);
                    //allMessages = tempAllMessages;
                    //testAllMessages = tempAllMessages;

                    Log.d("__allmessagecount__", String.valueOf(allMessages.size()));

                    Log.d("__PLZ__WORK__", "______________");


                }

//                Log.d("__TEMPMESSAGESIZE3__", String.valueOf(tempMessages.size()));
//
//                if (tempMessages.size() > 0)
//                {
//                    messages = tempMessages;
//                }
                //checkIfUserIsTutor();

                if (messageTutorUID.size() > 0)
                {
                    Log.d("MESSAGETUTORUID","====================================");
                    for (int i = 0; i < messageTutorUID.size(); i++)
                    {
                        Log.d("MESSAGETUTORUID", messageTutorUID.get(i).toString());
                    }
                }

                //_________________________________stuff
//                if (tempAllMessages.size() > 0)
//                {
//                    Log.d("TESTING", "INSIDELOAD");
//                    messages = tempAllMessages.get(messagePosition);
//
//                }

                //if (messagePosition != null)
//                messagePosition = 0;
//                if (messagePosition > -1)
//                {
//                    messages = allMessages.get(messagePosition);
//                }


//                if (hasPositionFromListClick)
//                {
//                    messages = allMessages.get(messagePosition);
//                    if (getFragmentManager().findFragmentByTag("messageFragment") != null &&
//                            getFragmentManager().findFragmentByTag("messageFragment").isVisible())
//                    {
//                        getFragmentManager().popBackStack();
//                        getFragmentManager().beginTransaction()
//                                .replace(R.id.content_container, new Message(), "messageFragment")
//                                .commit();
//                    }
//                }


                for (int i = 0; i < messages.size(); i++)
                {
                    Log.d("__MESSAGEFRAG", messages.get(i).getMessage());
                }



                if (messages.size() > 0)
                {
                    for (int i = 0; i < messages.size(); i++)
                    {
                        Log.d("__CHECK_FOR_MESSAGE__", messages.get(i).getMessage());
                    }
                }

//                getFragmentManager().beginTransaction()
//                        .replace(R.id.content_container, new Message())
//                        .commit();

//                getFragmentManager().beginTransaction()
//                        .replace(R.id.content_container, new Message())
//                        .commit();

//                if (getFragmentManager().findFragmentByTag("messageFragment") != null &&
//                        getFragmentManager().findFragmentByTag("messageFragment").isVisible())
//                {
//                    getFragmentManager().popBackStack();
//                    getFragmentManager().beginTransaction()
//                            .replace(R.id.content_container, new Message(), "messageFragment")
//                            .commit();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error", "something went wrong when retrieving data");
            }
        };
        messageRef.addValueEventListener(messageListener);



    }


    public Tutor getCurrentTutor()
    {
        return tutorFromInitialSetup;
    }

    public Student getCurrentStudent()
    {
        return currentUserInfo;
    }

    public String getStudentEmail()
    {
        return email;
    }

    public ArrayList<Tutor> getFavoriteTutors()
    {
        return favoriteTutors;
    }

    public ArrayList<Transaction> getAllTransactions()
    {
        return userTransactions;
    }

    public ArrayList<Tutor> getTutors()
    {
        return tutors;
    }

    public Tutor getTutor()
    {
        return tutor;
    }

    public boolean isTutor() {
        return isTutor;
    }

    public String getCurrentUserUID()
    {
        return currentUserUID;
    }

    public ArrayList<ReviewInfo> getReviews()
    {
        return reviews;
    }

    public Tutor getCurrentTutorToEdit()
    {
        return currentTutorToEdit;
    }

    public int getSelectedTutorReviewCount()
    {
        return selectedTutorReviewCount;
    }

    public ArrayList<MessageInfo> getMessages()
    {
        return messages;
    }

    public ArrayList<ArrayList<MessageInfo>> getAllMessage()
    {
        return allMessages;
    }

    public ArrayList<String> getMessageTutorUID()
    {
        return messageTutorUID;
    }

    public ArrayList<String> getAllStudentUID()
    {
        return allStudentUID;
    }

    public ArrayList<Student> getAllStudentData()
    {
        return allStudentData;
    }

    public int getUserSelectedZip()
    {
        return userSelectedZip;
    }


}
