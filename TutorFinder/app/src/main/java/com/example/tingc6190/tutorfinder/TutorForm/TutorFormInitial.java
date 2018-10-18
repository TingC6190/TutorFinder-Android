package com.example.tingc6190.tutorfinder.TutorForm;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;

import java.util.Calendar;

public class TutorFormInitial extends Fragment implements View.OnClickListener {

    TextView sundayStart_tv;
    TextView sundayEnd_tv;

    public TutorFormInitial() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.content_tutor_form_initial_setup, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            sundayStart_tv = getView().findViewById(R.id.sunday_start);
            sundayEnd_tv = getView().findViewById(R.id.sunday_end);

            sundayStart_tv.setOnClickListener(this);
            sundayEnd_tv.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v)
    {
       if (v == sundayStart_tv){
           getTime(sundayStart_tv);
       }
       else if (v == sundayEnd_tv){
           getTime(sundayEnd_tv);
       }

    }


    private void getTime(final TextView tv)
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), 2,  new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                int hour;
                String amOrPm;
                String hourToDisplay;
                String minuteToDisplay;

                if (hourOfDay > 12)
                {
                    hour = hourOfDay - 12;
                    amOrPm = "PM";
                }
                else
                {
                    hour = hourOfDay;
                    amOrPm = "AM";
                }

                if (hour < 10)
                {
                    hourToDisplay = "0" + hour;
                }
                else
                {
                    hourToDisplay = String.valueOf(hour);
                }

                if (minute < 10)
                {
                    minuteToDisplay = "0" + minute;
                }
                else
                {
                    minuteToDisplay = String.valueOf(minute);
                }

                if (hourToDisplay.equals("12") && minuteToDisplay.equals("00"))
                {
                    amOrPm = "PM";
                }

                if (hourToDisplay.equals("00") && minuteToDisplay.equals("00"))
                {
                    hourToDisplay = "12";
                    amOrPm = "AM";
                }

                if (hourOfDay == 12 && minute < 60)
                {
                    amOrPm = "PM";
                }

                if (hourToDisplay.equals("00"))
                {
                    hourToDisplay = "12";
                }

                String time = hourToDisplay + ":" + minuteToDisplay + " " + amOrPm;

                tv.setText(time);
            }
        }, 12, 0, false);
        timePickerDialog.show();
    }
}
