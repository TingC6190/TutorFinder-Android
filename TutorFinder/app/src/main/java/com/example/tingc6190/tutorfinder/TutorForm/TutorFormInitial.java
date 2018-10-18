package com.example.tingc6190.tutorfinder.TutorForm;

import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tingc6190.tutorfinder.DataObject.Location;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Friday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Monday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Saturday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Schedule;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Sunday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Thursday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Tuesday;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Wednesday;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.Profile.Review;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;

import java.util.ArrayList;
import java.util.Calendar;

public class TutorFormInitial extends Fragment implements View.OnClickListener {

    TextView sundayStart_tv;
    TextView sundayEnd_tv;
    TextView mondayStart_tv;
    TextView mondayEnd_tv;
    TextView tuesdayStart_tv;
    TextView tuesdayEnd_tv;
    TextView wednesdayStart_tv;
    TextView wednesdayEnd_tv;
    TextView thursdayStart_tv;
    TextView thursdayEnd_tv;
    TextView fridayStart_tv;
    TextView fridayEnd_tv;
    TextView saturdayStart_tv;
    TextView saturdayEnd_tv;

    String subject;
    String aboutMe;
    Schedule schedule;
    Tutor tutor;
    TutorFormListener tutorFormListener;
    Integer price;

    public TutorFormInitial() {
    }

    public interface TutorFormListener
    {
        void getTutorToUpdate(Tutor tutor);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof TutorFormListener)
        {
            tutorFormListener = (TutorFormListener) context;
        }
        else
        {
            Log.d("error", "must implement TutorFormListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        tutor = new Tutor();
        //schedule = new Schedule(new Sunday(), new Monday(), new Tuesday(), new Wednesday(), new Thursday(), new Friday(), new Saturday());
        schedule = new Schedule(
                new Sunday("N/A", "N/A"),
                new Monday("N/A", "N/A"),
                new Tuesday("N/A", "N/A"),
                new Wednesday("N/A", "N/A"),
                new Thursday("N/A", "N/A"),
                new Friday("N/A", "N/A"),
                new Saturday("N/A", "N/A"));

        return inflater.inflate(R.layout.content_tutor_form_initial_setup, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {

            final EditText aboutMe_tutorForm = getView().findViewById(R.id.tutor_form_about_me);
            Spinner subjectSpinner = getView().findViewById(R.id.subject_spinner);
            Button nextButton = getView().findViewById(R.id.tutor_form_next_button);
            final TextView pricePicker_tv = getView().findViewById(R.id.tutor_form_price);

            sundayStart_tv = getView().findViewById(R.id.sunday_start);
            sundayEnd_tv =   getView().findViewById(R.id.sunday_end);
            mondayStart_tv = getView().findViewById(R.id.monday_start);
            mondayEnd_tv =   getView().findViewById(R.id.monday_end);
            tuesdayStart_tv = getView().findViewById(R.id.tuesday_start);
            tuesdayEnd_tv =   getView().findViewById(R.id.tuesday_end);
            wednesdayStart_tv = getView().findViewById(R.id.wednesday_start);
            wednesdayEnd_tv =   getView().findViewById(R.id.wednesday_end);
            thursdayStart_tv = getView().findViewById(R.id.thursday_start);
            thursdayEnd_tv =   getView().findViewById(R.id.thursday_end);
            fridayStart_tv = getView().findViewById(R.id.friday_start);
            fridayEnd_tv =   getView().findViewById(R.id.friday_end);
            saturdayStart_tv = getView().findViewById(R.id.saturday_start);
            saturdayEnd_tv =   getView().findViewById(R.id.saturday_end);


            ArrayAdapter<CharSequence> subjectAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_subject, android.R.layout.simple_spinner_item);
            subjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            subjectSpinner.setAdapter(subjectAdapter);

            //get our selected subject in the spinner
            subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    subject = parent.getItemAtPosition(position).toString();
                    Log.d("____SUBJECT____", subject);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            pricePicker_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getContext());
                    dialog.setTitle("NumberPicker");
                    dialog.setContentView(R.layout.number_picker_dialog);

                    Button confirmButton = dialog.findViewById(R.id.num_picker_confirm);
                    Button cancelButton = dialog.findViewById(R.id.num_picker_cancel);

                    final NumberPicker numberPicker1 = dialog.findViewById(R.id.num_picker1);
                    numberPicker1.setMaxValue(9);
                    numberPicker1.setMinValue(0);
                    numberPicker1.setWrapSelectorWheel(false);
                    numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        }
                    });

                    final NumberPicker numberPicker2 = dialog.findViewById(R.id.num_picker2);
                    numberPicker2.setMaxValue(9);
                    numberPicker2.setMinValue(0);
                    numberPicker2.setWrapSelectorWheel(false);
                    numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        }
                    });

                    final NumberPicker numberPicker3 = dialog.findViewById(R.id.num_picker3);
                    numberPicker3.setMaxValue(9);
                    numberPicker3.setMinValue(0);
                    numberPicker3.setWrapSelectorWheel(false);
                    numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        }
                    });

                    confirmButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            price = Integer.valueOf(String.valueOf(numberPicker1.getValue()) + numberPicker2.getValue() + numberPicker3.getValue());

                            //Log.d("__CHECK_FOR_PRICE__", price);

                            pricePicker_tv.setText("$" + price);

                            dialog.dismiss();
                        }
                    });

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

            sundayStart_tv.setOnClickListener(this);
            sundayEnd_tv.setOnClickListener(this);
            mondayStart_tv.setOnClickListener(this);
            mondayEnd_tv.setOnClickListener(this);
            tuesdayStart_tv.setOnClickListener(this);
            tuesdayEnd_tv.setOnClickListener(this);
            wednesdayStart_tv.setOnClickListener(this);
            wednesdayEnd_tv.setOnClickListener(this);
            thursdayStart_tv.setOnClickListener(this);
            thursdayEnd_tv.setOnClickListener(this);
            fridayStart_tv.setOnClickListener(this);
            fridayEnd_tv.setOnClickListener(this);
            saturdayStart_tv.setOnClickListener(this);
            saturdayEnd_tv.setOnClickListener(this);

            // move to next form for background check
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    aboutMe = aboutMe_tutorForm.getText().toString().trim();

                    tutor.setFirstName("");
                    tutor.setLastName("");
                    tutor.setLocation(new Location("", "", ""));
                    tutor.setPrice(price);
                    tutor.setRating(0.0);
                    tutor.setDateVerified("");
                    tutor.setEmail("");
                    tutor.setLicenseNumber("");

                    //Review review = new Review("", "");

                    ArrayList<Review> reviews = new ArrayList<>();
                    reviews.add(new Review("", ""));
                    tutor.setReviews(reviews);

                    tutor.setDateVerified("12/15/2017");
                    tutor.setVerified(true);
                    tutor.setAboutMe(aboutMe);
                    tutor.setSubject(subject);
                    tutor.setSchedule(schedule);
                    tutor.setLicenseNumber("");

                    tutorFormListener.getTutorToUpdate(tutor);

                    //move to background check form
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new TutorFormBackground())
                            .addToBackStack("background form")
                            .commit();
                }
            });
        }
    }


    @Override
    public void onClick(View v)
    {
        // sunday
       if (v == sundayStart_tv){
           getTime(sundayStart_tv);
       }
       else if (v == sundayEnd_tv){
           getTime(sundayEnd_tv);
       }
       // monday
       else if (v == mondayStart_tv){
           getTime(mondayStart_tv);
       }
       else if (v == mondayEnd_tv){
           getTime(mondayEnd_tv);
       }
       // tuesday
       else if (v == tuesdayStart_tv){
           getTime(tuesdayStart_tv);
       }
       else if (v == tuesdayEnd_tv){
           getTime(tuesdayEnd_tv);
       }
       // wednesday
       else if (v == wednesdayStart_tv){
           getTime(wednesdayStart_tv);
       }
       else if (v == wednesdayEnd_tv){
           getTime(wednesdayEnd_tv);
       }
       // thursday
       else if (v == thursdayStart_tv){
           getTime(thursdayStart_tv);
       }
       else if (v == thursdayEnd_tv){
           getTime(thursdayEnd_tv);
       }
       // friday
       else if (v == fridayStart_tv){
           getTime(fridayStart_tv);
       }
       else if (v == fridayEnd_tv){
           getTime(fridayEnd_tv);
       }
       // saturday
       else if (v == saturdayStart_tv){
           getTime(saturdayStart_tv);
       }
       else if (v == saturdayEnd_tv){
           getTime(saturdayEnd_tv);
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
                saveSchedule(tv, time);

            }
        }, 12, 0, false);
        timePickerDialog.show();
    }

    //save the selected time
    private void saveSchedule(TextView tv, String time)
    {
        // Sunday
        if (tv == sundayStart_tv) {
            schedule.getSunday().setStartTime(time);
        }
        else if (tv == sundayEnd_tv) {
            schedule.getSunday().setEndTime(time);
        }
        // Monday
        else if (tv == mondayStart_tv) {
            schedule.getMonday().setStartTime(time);
        }
        else if (tv == mondayEnd_tv) {
            schedule.getMonday().setEndTime(time);
        }
        // Tuesday
        else if (tv == tuesdayStart_tv) {
            schedule.getTuesday().setStartTime(time);
        }
        else if (tv == tuesdayEnd_tv) {
            schedule.getTuesday().setEndTime(time);
        }
        // Wednesday
        else if (tv == wednesdayStart_tv) {
            schedule.getWednesday().setStartTime(time);
        }
        else if (tv == wednesdayEnd_tv) {
            schedule.getWednesday().setEndTime(time);
        }
        // Thursday
        else if (tv == thursdayStart_tv) {
            schedule.getThursday().setStartTime(time);
        }
        else if (tv == thursdayEnd_tv) {
            schedule.getThursday().setEndTime(time);
        }
        // Friday
        else if (tv == fridayStart_tv) {
            schedule.getFriday().setStartTime(time);
        }
        else if (tv == fridayEnd_tv) {
            schedule.getFriday().setEndTime(time);
        }
        // Saturday
        else if (tv == saturdayStart_tv) {
            schedule.getSaturday().setStartTime(time);
        }
        else if (tv == saturdayEnd_tv) {
            schedule.getSaturday().setEndTime(time);
        }
    }
}
