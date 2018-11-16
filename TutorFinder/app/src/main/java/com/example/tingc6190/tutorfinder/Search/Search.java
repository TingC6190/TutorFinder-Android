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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tingc6190.tutorfinder.DataObject.Location;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.MainActivity;
import com.example.tingc6190.tutorfinder.Profile.Profile;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Welcome.Welcome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Search extends Fragment {

    HomeActivity homeActivity;
    ArrayList<Tutor> tutors = new ArrayList<>();
    ArrayList<Tutor> dupeTutors;
    private TutorListener tutorListener;
    private DatabaseReference databaseReference;
    Spinner leftSpinner;
    Spinner rightSpinner;
    Spinner radiusSpinner;
    String leftSpinnerString;
    String rightSpinnerString;
    String radiusSpinnerString;
    int userSelectedZip;

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
        userSelectedZip = homeActivity.getUserSelectedZip();
        tutors = homeActivity.getTutors();

        dupeTutors = tutors;

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
            TextView filterText_tv = getView().findViewById(R.id.filter_text);

            if (tutors.size() != 0)
            {
                //display our list
                ListView listView = getView().findViewById(R.id.list_search);
                final TutorAdapter tutorAdapter = new TutorAdapter(getContext(), tutors);
                listView.setAdapter(tutorAdapter);

                //tutorAdapter.notifyDataSetChanged();

                //get the selected tutor's profile
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("__ITEM_CLICKED__", tutors.get(position).getFirstName());

                        tutorListener.getTutor(tutors.get(position));
                    }
                });

                Collections.sort(tutors, new Comparator<Tutor>() {
                    @Override
                    public int compare(Tutor o1, Tutor o2) {
                        return o1.getPrice().compareTo(o2.getPrice());
                    }
                });


                //spinners
                //leftSpinner = getView().findViewById(R.id.setting_spinner_left);
                rightSpinner = getView().findViewById(R.id.setting_spinner_right);
                radiusSpinner = getView().findViewById(R.id.spinner_radius);

                //leftSpinnerString = "Price";
                rightSpinnerString = "Price Ascending";
                radiusSpinnerString = "Any Range";


//                ArrayAdapter<CharSequence> leftAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_items_left,
//                        android.R.layout.simple_spinner_item);
//                leftAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                leftSpinner.setAdapter(leftAdapter);

                ArrayAdapter<CharSequence> rightAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_items_right, android.R.layout.simple_spinner_item);
                rightAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                rightSpinner.setAdapter(rightAdapter);

                ArrayAdapter<CharSequence> radiusAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_mile_radius, android.R.layout.simple_spinner_item);
                radiusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                radiusSpinner.setAdapter(radiusAdapter);

                rightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        rightSpinnerString = parent.getItemAtPosition(position).toString();

                        if (rightSpinnerString.equals("Price Ascending"))
                        {
                            Collections.sort(tutors, new Comparator<Tutor>() {
                                @Override
                                public int compare(Tutor o1, Tutor o2) {
                                    return o1.getPrice().compareTo(o2.getPrice());
                                }
                            });
                        }

                        if (rightSpinnerString.equals("Price Descending"))
                        {
                            Collections.sort(tutors, new Comparator<Tutor>() {
                                @Override
                                public int compare(Tutor o1, Tutor o2) {
                                    return o2.getPrice().compareTo(o1.getPrice());
                                }
                            });
                        }

                        ListView listView = getView().findViewById(R.id.list_search);
                        TutorAdapter tutorAdapter = new TutorAdapter(getContext(), tutors);
                        listView.setAdapter(tutorAdapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                radiusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        radiusSpinnerString = parent.getItemAtPosition(position).toString();

                        int lowRadius;
                        int highRadius;

                        ArrayList<Tutor> tempTutors = new ArrayList<>();
                        int getSelectedRange= 0;

                        if (!radiusSpinnerString.equals("Any Range"))
                        {
                            if (radiusSpinnerString.equals("5 Mile Radius"))
                            {
                                getSelectedRange = 5;
                            }

                            if (radiusSpinnerString.equals("10 Mile Radius"))
                            {
                                getSelectedRange = 10;
                            }

                            if (radiusSpinnerString.equals("15 Mile Radius"))
                            {
                                getSelectedRange = 15;
                            }

                            if (radiusSpinnerString.equals("20 Mile Radius"))
                            {
                                getSelectedRange = 20;
                            }

                            if (radiusSpinnerString.equals("25 Mile Radius"))
                            {
                                getSelectedRange = 25;
                            }

                            Log.d("__RADIUS__", "*********************************************");
                            for (int i = 0; i < dupeTutors.size(); i++)
                            {
                                int tutorZipToInt = Integer.parseInt(dupeTutors.get(i).getLocation().getZipcode());

                                lowRadius = userSelectedZip - (getSelectedRange * 5);
                                highRadius = userSelectedZip + (getSelectedRange * 5);


                                if (tutorZipToInt >= lowRadius && tutorZipToInt <= highRadius)
                                {
                                    Log.d("__RADIUS__", highRadius + " > " + tutorZipToInt + " > " + lowRadius);
                                    tempTutors.add(dupeTutors.get(i));
                                }
                                tutors = tempTutors;
                                //tutorAdapter.notifyDataSetChanged();
                            }

                            ListView listView = getView().findViewById(R.id.list_search);
                            TutorAdapter tutorAdapter = new TutorAdapter(getContext(), tutors);
                            listView.setAdapter(tutorAdapter);

                            //Log.d("__ZIP_IN_LIST__", hig);

                            Log.d("__ZIP_IN_LIST__", "*********************BEFORE***********************");
                            for (int i = 0; i < dupeTutors.size(); i++)
                            {

                                Log.d("__ZIP_IN_LIST__", dupeTutors.get(i).getLocation().getZipcode());
                            }
//                            tutors = tempTutors;
//                            tutorAdapter.notifyDataSetChanged();
                            Log.d("__ZIP_IN_LIST__", "*********************AFTER***********************");
                            for (int i = 0; i < tutors.size(); i++)
                            {

                                Log.d("__ZIP_IN_LIST__", tutors.get(i).getLocation().getZipcode());
                            }

                            //tutorAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            tutors = dupeTutors;
                            //tutorAdapter.notifyDataSetChanged();

                            ListView listView = getView().findViewById(R.id.list_search);
                            TutorAdapter tutorAdapter = new TutorAdapter(getContext(), tutors);
                            listView.setAdapter(tutorAdapter);

                            Log.d("__ZIP_IN_LIST__", "*********************ALL***********************");
                            for (int i = 0; i < tutors.size(); i++)
                            {

                                Log.d("__ZIP_IN_LIST__", tutors.get(i).getLocation().getZipcode());
                            }
                        }
                        //tutors = tempTutors;
//
//                        tutorAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            filterText_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Welcome())
                            .commit();
                }
            });
        }
    }
}
