package com.example.tingc6190.tutorfinder.Profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
                final View favorite_checked_v;
                final View favorite_unchecked_v;

                name_tv = getView().findViewById(R.id.profile_name);
                price_tv = getView().findViewById(R.id.profile_price);
                subject_tv = getView().findViewById(R.id.profile_subject);
                favorite_checked_v = getView().findViewById(R.id.profile_favorite_button_checked);
                favorite_unchecked_v = getView().findViewById(R.id.profile_favorite_button_unchecked);

                String fullName = tutor.getFirstName() + " " + tutor.getLastName();
                String price = "$" + tutor.getPrice() + "/hr";
                String subject = tutor.getSubject();

                name_tv.setText(fullName);
                price_tv.setText(price);
                subject_tv.setText(subject);


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

            }
        }
    }
}
