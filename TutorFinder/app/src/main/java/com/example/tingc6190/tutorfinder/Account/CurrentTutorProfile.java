package com.example.tingc6190.tutorfinder.Account;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tingc6190.tutorfinder.DataObject.Schedule.Schedule;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentTutorProfile extends Fragment {

    private Tutor currentTutor = new Tutor();
    private HomeActivity homeActivity;
    TextView price_tv;
    TextView name_tv;
    TextView email_tv;
    TextView aboutMe_tv;
    TextView subject_tv;
    TextView date_tv;
    TextView sunTime_tv;
    TextView monTime_tv;
    TextView tueTime_tv;
    TextView wedTime_tv;
    TextView thuTime_tv;
    TextView friTime_tv;
    TextView satTime_tv;
    CircleImageView profilePicture_riv;


    public CurrentTutorProfile() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();
        currentTutor = homeActivity.getCurrentTutorToEdit();

        return inflater.inflate(R.layout.content_current_tutor_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            price_tv = getView().findViewById(R.id.current_tutor_profile_price);
            name_tv = getView().findViewById(R.id.current_tutor_profile_name);
            email_tv = getView().findViewById(R.id.current_tutor_profile_email);
            aboutMe_tv = getView().findViewById(R.id.current_tutor_profile_aboutme);
            subject_tv = getView().findViewById(R.id.current_tutor_profile_subject);
            sunTime_tv = getView().findViewById(R.id.current_tutor_profile_time_sun);
            monTime_tv = getView().findViewById(R.id.current_tutor_profile_time_mon);
            tueTime_tv = getView().findViewById(R.id.current_tutor_profile_time_tue);
            wedTime_tv = getView().findViewById(R.id.current_tutor_profile_time_wed);
            thuTime_tv = getView().findViewById(R.id.current_tutor_profile_time_thu);
            friTime_tv = getView().findViewById(R.id.current_tutor_profile_time_fri);
            satTime_tv = getView().findViewById(R.id.current_tutor_profile_time_sat);
            date_tv = getView().findViewById(R.id.current_tutor_profile_verified_date);
            profilePicture_riv = getView().findViewById(R.id.current_tutor_profile_image);

            String price = "$" + currentTutor.getPrice() + "/hr";
            String name = currentTutor.getFirstName() + " " + currentTutor.getLastName();
            String email = currentTutor.getEmail();
            String aboutMe = currentTutor.getAboutMe();
            String subject = currentTutor.getSubject();
            String profileImage = currentTutor.getPicture().trim();
            String date = currentTutor.getDateVerified();

            Schedule schedule = currentTutor.getSchedule();
            String sunTime = schedule.getSunday().getStartTime() + " - " + schedule.getSunday().getEndTime();
            String monTime = schedule.getMonday().getStartTime() + " - " + schedule.getMonday().getEndTime();
            String tueTime = schedule.getTuesday().getStartTime() + " - " + schedule.getTuesday().getEndTime();
            String wedTime = schedule.getWednesday().getStartTime() + " - " + schedule.getWednesday().getEndTime();
            String thuTime = schedule.getThursday().getStartTime() + " - " + schedule.getThursday().getEndTime();
            String friTime = schedule.getFriday().getStartTime() + " - " + schedule.getFriday().getEndTime();
            String satTime = schedule.getSaturday().getStartTime() + " - " + schedule.getSaturday().getEndTime();

            price_tv.setText(price);
            name_tv.setText(name);
            email_tv.setText(email);
            aboutMe_tv.setText(aboutMe);
            subject_tv.setText(subject);
            date_tv.setText(date);
            sunTime_tv.setText(sunTime);
            monTime_tv.setText(monTime);
            tueTime_tv.setText(tueTime);
            wedTime_tv.setText(wedTime);
            thuTime_tv.setText(thuTime);
            friTime_tv.setText(friTime);
            satTime_tv.setText(satTime);

            if (!TextUtils.isEmpty(profileImage))
            {
                Picasso.get().load(profileImage).into(profilePicture_riv);
            }
            else
            {
                profilePicture_riv.setImageResource(R.drawable.default_profile);
            }
        }
    }
}
