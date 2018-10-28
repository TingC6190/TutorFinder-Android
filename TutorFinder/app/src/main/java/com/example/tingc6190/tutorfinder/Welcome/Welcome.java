package com.example.tingc6190.tutorfinder.Welcome;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Search;

public class Welcome extends Fragment {

    private WelcomeListener welcomeListener;
    private String subject;
    private String zipcode;

    public Welcome() {
    }

    public interface WelcomeListener
    {
        void getSearchSettings(String subject, String zipcode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof WelcomeListener)
        {
            welcomeListener = (WelcomeListener) context;
        }
        else
        {
            Log.d("error", "must implement WelcomeListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.content_welcome_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            Button searchButton = getView().findViewById(R.id.search_welcome);
            Spinner subjectSpinner = getView().findViewById(R.id.setting_subject_spinner);


            ArrayAdapter<CharSequence> subjectAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.spinner_subject, android.R.layout.simple_spinner_item);
            subjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            subjectSpinner.setAdapter(subjectAdapter);

            //get our selected subject in the spinner
            subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    subject = parent.getItemAtPosition(position).toString();
                    //Log.d("__SPINNER__", subject);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("__SEARCH_TAPPED__", subject);

                    welcomeListener.getSearchSettings(subject, "");

//                    getFragmentManager().beginTransaction()
//                            .replace(R.id.content_container, new Search())
//                            .commit();
                }
            });

        }
    }
}
