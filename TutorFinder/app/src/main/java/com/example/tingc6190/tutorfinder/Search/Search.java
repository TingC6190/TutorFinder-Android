package com.example.tingc6190.tutorfinder.Search;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tingc6190.tutorfinder.DataObject.Location;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.Profile.Profile;
import com.example.tingc6190.tutorfinder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends Fragment {

    HomeActivity homeActivity;
    ArrayList<Tutor> tutors = new ArrayList<>();
    private TutorListener tutorListener;
    private DatabaseReference databaseReference;

    public Search() {
    }

    public interface TutorListener
    {
        void getTutor(Tutor mTutor);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof TutorListener)
        {
            tutorListener = (TutorListener) context;
        }
        else
        {
            Log.d("error", "must implement TutorListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        homeActivity = (HomeActivity) getActivity();

        tutors = homeActivity.getTutors();


//        for (int i = 0; i < tutors.size(); i++)
//        {
//            String name = tutors.get(i).getFirstName() + " " + tutors.get(i).getLastName();
//
//            String price = "$" + String.valueOf(tutors.get(i).getPrice()) + "/hr";
//
//            String rating = String.valueOf(tutors.get(i).getRating()) + "* Rating";
//
//            String subject = tutors.get(i).getSubject();
//
//            Location location = tutors.get(i).getLocation();
//
//            String tutorLocation = location.getCity() + ", " + location.getState() + " " + location.getZipcode();
//
//            Log.d("__NAME__", name);
//            Log.d("__PRICE__", price);
//            Log.d("__RATING__", rating);
//            Log.d("__SUBJECT__", subject);
//            Log.d("__LOCATION__", tutorLocation);
//            Log.d(" ", " ");
//        }




        return inflater.inflate(R.layout.content_search_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            if (tutors.size() != 0)
            {
                //display our list
                ListView listView = getView().findViewById(R.id.list_search);
                TutorAdapter tutorAdapter = new TutorAdapter(getContext(), tutors);
                listView.setAdapter(tutorAdapter);

                tutorAdapter.notifyDataSetChanged();

                //get the selected tutor's profile
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("__ITEM_CLICKED__", tutors.get(position).getFirstName());

                        tutorListener.getTutor(tutors.get(position));
                    }
                });
            }
        }
    }
}
