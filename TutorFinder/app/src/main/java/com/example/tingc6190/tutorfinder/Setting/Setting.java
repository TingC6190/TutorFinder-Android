package com.example.tingc6190.tutorfinder.Setting;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.DataObject.Student;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.TutorForm.TutorFormInitial;

public class Setting extends Fragment {

    EditText firstName_et;
    EditText lastName_et;
    EditText aboutMe_et;
    Button applyButton;
    Student student;
    HomeActivity homeActivity;
    SettingListener settingListener;
    Button changePasswordButton;

    public Setting() {
    }

    public interface SettingListener
    {
        void getUpdateStudent(String first, String last, String aboutMe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SettingListener)
        {
            settingListener = (SettingListener) context;
        }
        else
        {
            Log.d("error", "must implement SettingListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();

        student = homeActivity.getCurrentStudent();

        return inflater.inflate(R.layout.content_setting_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            firstName_et = getView().findViewById(R.id.first_name_setting);
            lastName_et = getView().findViewById(R.id.last_name_setting);
            aboutMe_et = getView().findViewById(R.id.about_me_setting);
            applyButton = getView().findViewById(R.id.apply_settings_button);
            changePasswordButton = getView().findViewById(R.id.change_password_button);

            firstName_et.setText(student.getFirstName());
            lastName_et.setText(student.getLastName());

            if (!student.getAboutMe().trim().equals(""))
            {
                aboutMe_et.setText(student.getAboutMe());
            }

            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    applyChanges();
                }
            });

            changePasswordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Password())
                            .addToBackStack("password screen")
                            .commit();
                }
            });

        }
    }


    private void applyChanges()
    {
        String first = firstName_et.getText().toString().trim();
        String last = lastName_et.getText().toString().trim();
        String aboutMe = aboutMe_et.getText().toString().trim();

        //make sure none of the fields are empty
        if (!TextUtils.isEmpty(first) && !TextUtils.isEmpty(last) && !TextUtils.isEmpty(aboutMe))
        {
            //check to see if any fields have changed from what it was before
            if (student.getFirstName().equals(first) && student.getLastName().equals(last) &&
                    student.getAboutMe().equals(aboutMe))
            {
                Toast.makeText(getContext(), "Please make changes before you apply.", Toast.LENGTH_LONG).show();
            }
            else
            {
                settingListener.getUpdateStudent(first, last, aboutMe);
            }
        }
        else
        {
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        }
    }
}
