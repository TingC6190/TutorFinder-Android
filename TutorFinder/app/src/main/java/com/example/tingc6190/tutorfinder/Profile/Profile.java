package com.example.tingc6190.tutorfinder.Profile;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tingc6190.tutorfinder.DataObject.Schedule.Schedule;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Search;
import com.example.tingc6190.tutorfinder.Search.Tutor;

public class Profile extends Fragment {

    private Tutor tutor;
    private HomeActivity homeActivity;

    public Profile() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();
        tutor = homeActivity.getTutor();

        return inflater.inflate(R.layout.content_profile_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            if (tutor != null)
            {
                TextView name_tv;
                TextView price_tv;
                TextView subject_tv;
                TextView timeSunday_tv;
                TextView timeMonday_tv;
                TextView timeTuesday_tv;
                TextView timeWednesday_tv;
                TextView timeThursday_tv;
                TextView timeFriday_tv;
                TextView timeSaturday_tv;
                final View favorite_checked_v;
                final View favorite_unchecked_v;
                final View email_v;

                name_tv = getView().findViewById(R.id.profile_name);
                price_tv = getView().findViewById(R.id.profile_price);
                subject_tv = getView().findViewById(R.id.profile_subject);
                favorite_checked_v = getView().findViewById(R.id.profile_favorite_button_checked);
                favorite_unchecked_v = getView().findViewById(R.id.profile_favorite_button_unchecked);
                timeSunday_tv = getView().findViewById(R.id.profile_time_sun);
                timeMonday_tv = getView().findViewById(R.id.profile_time_mon);
                timeTuesday_tv = getView().findViewById(R.id.profile_time_tue);
                timeWednesday_tv = getView().findViewById(R.id.profile_time_wed);
                timeThursday_tv = getView().findViewById(R.id.profile_time_thu);
                timeFriday_tv = getView().findViewById(R.id.profile_time_fri);
                timeSaturday_tv = getView().findViewById(R.id.profile_time_sat);
                email_v = getView().findViewById(R.id.profile_email_button);

                String fullName = tutor.getFirstName() + " " + tutor.getLastName();
                String price = "$" + tutor.getPrice() + "/hr";
                String subject = tutor.getSubject();

                //tutor schedule
                Schedule schedule = tutor.getSchedule();
                String timeSunday = schedule.getSunday().getStartTime() + " - " + schedule.getSunday().getEndTime();
                String timeMonday = schedule.getMonday().getStartTime() + " - " + schedule.getMonday().getEndTime();
                String timeTuesday = schedule.getTuesday().getStartTime() + " - " + schedule.getTuesday().getEndTime();
                String timeWednesday = schedule.getWednesday().getStartTime() + " - " + schedule.getWednesday().getEndTime();
                String timeThursday = schedule.getThursday().getStartTime() + " - " + schedule.getThursday().getEndTime();
                String timeFriday = schedule.getFriday().getStartTime() + " - " + schedule.getFriday().getEndTime();
                String timeSaturday = schedule.getSaturday().getStartTime() + " - " + schedule.getSaturday().getEndTime();


                name_tv.setText(fullName);
                price_tv.setText(price);
                subject_tv.setText(subject);
                timeSunday_tv.setText(timeSunday);
                timeMonday_tv.setText(timeMonday);
                timeTuesday_tv.setText(timeTuesday);
                timeWednesday_tv.setText(timeWednesday);
                timeThursday_tv.setText(timeThursday);
                timeFriday_tv.setText(timeFriday);
                timeSaturday_tv.setText(timeSaturday);

                //checking for favorites
                favorite_checked_v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        favorite_checked_v.setVisibility(View.INVISIBLE);
                        favorite_unchecked_v.setVisibility(View.VISIBLE);
                    }
                });

                favorite_unchecked_v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        favorite_checked_v.setVisibility(View.VISIBLE);
                        favorite_unchecked_v.setVisibility(View.INVISIBLE);

                    }
                });

                email_v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("__EMAIL__", "STUFF");

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL, "tingc6190@gmail.com");

                        startActivity(intent);
                    }
                });

            }
        }
    }
}
