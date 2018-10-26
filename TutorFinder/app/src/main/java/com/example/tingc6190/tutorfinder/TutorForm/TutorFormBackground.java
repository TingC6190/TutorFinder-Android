package com.example.tingc6190.tutorfinder.TutorForm;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Search;

public class TutorFormBackground extends Fragment {

    String city;
    String state;
    String zipcode;
    String license;
    BackgroundFormListener backgroundFormListener;
    HomeActivity homeActivity;
    Button applyButton;
    EditText city_et;
    EditText state_et;
    EditText zipcode_et;
    EditText license_et;

    public TutorFormBackground() {
    }

    public interface BackgroundFormListener
    {
        void getBackgroundFormListener(String city, String state, String zipcode, String license);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BackgroundFormListener)
        {
            backgroundFormListener = (BackgroundFormListener) context;
        }
        else
        {
            Log.d("error", "must implement BackgroundFormListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();

        return inflater.inflate(R.layout.content_tutor_form_bg_check, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            city_et = getView().findViewById(R.id.tutor_form_city);
            state_et = getView().findViewById(R.id.tutor_form_state);
            zipcode_et = getView().findViewById(R.id.tutor_form_zipcode);
            license_et = getView().findViewById(R.id.tutor_form_license);
            applyButton = getView().findViewById(R.id.background_form_apply_button);

            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    city = city_et.getText().toString().trim();
                    state = state_et.getText().toString().trim();
                    zipcode = zipcode_et.getText().toString().trim();
                    license = license_et.getText().toString().trim();

                    //make sure no fields are empty
                    if (TextUtils.isEmpty(city) || TextUtils.isEmpty(state) || TextUtils.isEmpty(zipcode) || TextUtils.isEmpty(license))
                    {
                        Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //make sure zipcode field has 5 digits
                        if (zipcode.length() != 5)
                        {
                            Toast.makeText(getContext(), "Please make sure the zipcode is correct.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //student user becomes a tutor user
                            backgroundFormListener.getBackgroundFormListener(city, state, zipcode, license);
                        }
                    }
                }
            });

        }
    }
}
