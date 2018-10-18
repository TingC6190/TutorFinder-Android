package com.example.tingc6190.tutorfinder.Account;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.Profile.Profile;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.TutorForm.TutorFormInitial;

public class Account extends Fragment {

    private HomeActivity homeActivity;

    public Account() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();

        return inflater.inflate(R.layout.content_account_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            Button logoutButton = getView().findViewById(R.id.logout_button);
            Button applyTutorButton = getView().findViewById(R.id.apply_tutor_button);

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeActivity.logOut();
                }
            });

            applyTutorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //launch initial tutor form
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new TutorFormInitial())
                            .addToBackStack("initial form")
                            .commit();
                }
            });
        }
    }
}
