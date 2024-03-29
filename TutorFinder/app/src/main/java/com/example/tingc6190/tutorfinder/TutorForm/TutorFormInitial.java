package com.example.tingc6190.tutorfinder.TutorForm;

import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import android.widget.Toast;

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
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;

import org.w3c.dom.Text;

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

    TextView resetSunday;
    TextView resetMonday;
    TextView resetTuesday;
    TextView resetWednesday;
    TextView resetThursday;
    TextView resetFriday;
    TextView resetSaturday;


    String subject;
    String aboutMe;
    Schedule schedule;
    Tutor tutor;
    TutorFormListener tutorFormListener;
    Integer price;
    Tutor currentTutor;
    HomeActivity homeActivity;
    Boolean isTutor;
    TimePickerDialog timePickerDialog;
    Tutor tutorToEdit;
    String userUID;

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

        homeActivity = (HomeActivity) getActivity();
        isTutor = homeActivity.isTutor();
        tutorToEdit = new Tutor();
        currentTutor = new Tutor();
        currentTutor = homeActivity.getCurrentTutor();
        userUID = homeActivity.getCurrentUserUID();

        if (isTutor)
        {
            //tutorToEdit = homeActivity.getTutorToEdit();
            tutorToEdit = homeActivity.getCurrentTutorToEdit();
        }

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

            resetSunday = getView().findViewById(R.id.reset_sunday);
            resetMonday = getView().findViewById(R.id.reset_monday);
            resetTuesday = getView().findViewById(R.id.reset_tuesday);
            resetWednesday = getView().findViewById(R.id.reset_wednesday);
            resetThursday = getView().findViewById(R.id.reset_thursday);
            resetFriday = getView().findViewById(R.id.reset_friday);
            resetSaturday = getView().findViewById(R.id.reset_saturday);

//            if (!currentTutor.getAboutMe().toString().trim().equals(""))
//            {
//                aboutMe_tutorForm.setText(currentTutor.getAboutMe());
//            }

            if (isTutor)
            {
                Schedule tutorSchedule = tutorToEdit.getSchedule();
                price = tutorToEdit.getPrice();
                String priceString = "$" + String.valueOf(tutorToEdit.getPrice() + "/hr");

                aboutMe_tutorForm.setText(tutorToEdit.getAboutMe());
                pricePicker_tv.setText(priceString);

                schedule = tutorSchedule;

                //Log.d("_____________", "BLAHBLAH__" + tutorSchedule.getSunday().getStartTime());

                mondayStart_tv.setText(tutorSchedule.getMonday().getStartTime());
                mondayEnd_tv  .setText(tutorSchedule.getMonday().getEndTime());

                sundayStart_tv.setText(tutorSchedule.getSunday().getStartTime());
                sundayEnd_tv  .setText(tutorSchedule.getSunday().getEndTime());

                //mondayStart_tv.setText(tutorSchedule.getMonday().getStartTime());
                //mondayEnd_tv  .setText(tutorSchedule.getMonday().getEndTime());

                tuesdayStart_tv.setText(tutorSchedule.getTuesday().getStartTime());
                tuesdayEnd_tv  .setText(tutorSchedule.getTuesday().getEndTime());

                wednesdayStart_tv.setText(tutorSchedule.getWednesday().getStartTime());
                wednesdayEnd_tv  .setText(tutorSchedule.getWednesday().getEndTime());

                thursdayStart_tv.setText(tutorSchedule.getThursday().getStartTime());
                thursdayEnd_tv  .setText(tutorSchedule.getThursday().getEndTime());

                fridayStart_tv.setText(tutorSchedule.getFriday().getStartTime());
                fridayEnd_tv  .setText(tutorSchedule.getFriday().getEndTime());

                saturdayStart_tv.setText(tutorSchedule.getSaturday().getStartTime());
                saturdayEnd_tv  .setText(tutorSchedule.getSaturday().getEndTime());
            }

            ArrayAdapter<CharSequence> subjectAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.spinner_subject, android.R.layout.simple_spinner_item);
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


            //select the price
            pricePicker_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getContext());
                    dialog.setTitle("NumberPicker");
                    dialog.setContentView(R.layout.number_picker_dialog);

                    Button confirmButton = dialog.findViewById(R.id.num_picker_confirm);
                    Button cancelButton = dialog.findViewById(R.id.num_picker_cancel);

                    final NumberPicker numberPicker3 = dialog.findViewById(R.id.num_picker3);

                    final String[] priceArray = {"5", "10", "15", "20", "25", "30", "35", "40", "45", "50",
                            "55", "60", "65", "70", "75", "80", "85", "90", "95", "100"};

                    int priceArrayCount = priceArray.length - 1;

                    numberPicker3.setMaxValue(priceArrayCount);
                    numberPicker3.setDisplayedValues(priceArray);
                    numberPicker3.setWrapSelectorWheel(false);
                    numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        }
                    });

                    confirmButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int indexValue = numberPicker3.getValue();
                            String getPriceFromArray = priceArray[indexValue];

                            price = Integer.valueOf(getPriceFromArray);

                            //Log.d("__CHECK_FOR_PRICE__", price);

                            pricePicker_tv.setText("$" + price + "/hr");

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

            resetSunday.setOnClickListener(this);
            resetMonday.setOnClickListener(this);
            resetTuesday.setOnClickListener(this);
            resetWednesday.setOnClickListener(this);
            resetThursday.setOnClickListener(this);
            resetFriday.setOnClickListener(this);
            resetSaturday.setOnClickListener(this);

            // move to next form for background check
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    aboutMe = aboutMe_tutorForm.getText().toString().trim();

                    if (TextUtils.isEmpty(aboutMe))
                    {
                        Toast.makeText(getContext(), "Please write a short bio about yourself.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (price != null)
                        {
                            if (checkTimeFields())
                            {
                                tutor.setFirstName("");
                                tutor.setLastName("");
                                tutor.setLocation(new Location("", "", ""));
                                tutor.setPrice(price);
                                tutor.setRating(0.0);
                                tutor.setDateVerified("");
                                tutor.setEmail("");
                                tutor.setLicenseNumber("");
                                tutor.setReviewCounter(0);

                                //Review review = new Review("", "");

//                                ArrayList<Review> reviews = new ArrayList<>();
//                                reviews.add(new Review("", ""));
//                                tutor.setReviews(reviews);

                                tutor.setDateVerified("12/15/2017");
                                tutor.setVerified(true);
                                tutor.setAboutMe(aboutMe);
                                tutor.setSubject(subject);
                                tutor.setSchedule(schedule);
                                tutor.setLicenseNumber("");
                                tutor.setTutorUID(userUID);

                                tutorFormListener.getTutorToUpdate(tutor);

                                //move to background check form
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.content_container, new TutorFormBackground())
                                        .addToBackStack("background form")
                                        .commit();
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Please set a price.", Toast.LENGTH_SHORT).show();
                        }
                    }

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

       String notAvailable = "N/A";

       if (v == resetSunday) {
           sundayStart_tv.setText(notAvailable);
           sundayEnd_tv.setText(notAvailable);
       }
       else if (v == resetMonday) {
           mondayStart_tv.setText(notAvailable);
           mondayEnd_tv.setText(notAvailable);
       }
       else if (v == resetTuesday) {
           tuesdayStart_tv.setText(notAvailable);
           tuesdayEnd_tv.setText(notAvailable);
       }
       else if (v == resetWednesday) {
           wednesdayStart_tv.setText(notAvailable);
           wednesdayEnd_tv.setText(notAvailable);
       }
       else if (v == resetThursday) {
           thursdayStart_tv.setText(notAvailable);
           thursdayEnd_tv.setText(notAvailable);
       }
       else if (v == resetFriday) {
           fridayStart_tv.setText(notAvailable);
           fridayEnd_tv.setText(notAvailable);
       }
       else if (v == resetSaturday) {
           saturdayStart_tv.setText(notAvailable);
           saturdayEnd_tv.setText(notAvailable);
       }

    }


    private void getTime(final TextView tv)
    {
        timePickerDialog = new TimePickerDialog(getContext(), 2,  new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Log.d("__HOUROFDAY__", String.valueOf(hourOfDay));
                Log.d("__MINUTE__", String.valueOf(minute));

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

        if (!tv.getText().toString().equals("N/A"))
        {
            String time = tv.getText().toString();

            String[] splitTime = time.split(" ");

            String[] splitHourAndMinute = splitTime[0].split(":");

            int hour = Integer.parseInt(splitHourAndMinute[0]);
            int minute = Integer.parseInt(splitHourAndMinute[1]);
            String amOrPm = splitTime[1];

            //11:59 PM == 23:59
            //12:00 PM == 24:00
            //12:00 AM == 00:00
            //11:59 AM == 11:59
            if (amOrPm.equals("PM"))
            {
                hour = hour + 12;
                if (hour == 24 && minute > 0)
                {
                    hour = hour - 12;
                }
            }
            if (amOrPm.equals("PM") && hour == 24 && minute == 0)
            {
                hour = 12;
            }
            if (amOrPm.equals("AM") && hour == 12 && minute == 0)
            {
                hour = 0;
            }

            Log.d("__WHATTIME__", hour + "    :    " + minute + "    " + amOrPm);
            timePickerDialog.updateTime(hour, minute);
        }
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

    //make sure start or end time for the selected days are not "N/A"
    private boolean checkTimeFields()
    {
        String sunStart = sundayStart_tv.getText().toString();
        String sunEnd   = sundayEnd_tv.getText().toString();
        String monStart = mondayStart_tv.getText().toString();
        String monEnd   = mondayEnd_tv.getText().toString();
        String tueStart = tuesdayStart_tv.getText().toString();
        String tueEnd   = tuesdayEnd_tv.getText().toString();
        String wedStart = wednesdayStart_tv.getText().toString();
        String wedEnd   = wednesdayEnd_tv.getText().toString();
        String thuStart = thursdayStart_tv.getText().toString();
        String thuEnd   = thursdayEnd_tv.getText().toString();
        String friStart = fridayStart_tv.getText().toString();
        String friEnd   = fridayEnd_tv.getText().toString();
        String satStart = saturdayStart_tv.getText().toString();
        String satEnd   = saturdayEnd_tv.getText().toString();

        if (sunStart.equals("N/A") && !sunEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Sunday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__SUNSTART__", "__EMPTY__");
            return false;
        }
        else if (!sunStart.equals("N/A") && sunEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Sunday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__SUNEND__", "__EMPTY__");
            return false;
        }
        else if (monStart.equals("N/A") && !monEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Monday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__MONSTART__", "__EMPTY__");
            return false;
        }
        else if (!monStart.equals("N/A") && monEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Monday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__MONEND__", "__EMPTY__");
            return false;
        }
        else if (tueStart.equals("N/A") && !tueEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Tuesday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__TUESTART__", "__EMPTY__");
            return false;
        }
        else if (!tueStart.equals("N/A") && tueEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Tuesday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__TUEEND__", "__EMPTY__");
            return false;
        }
        else if (wedStart.equals("N/A") && !wedEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Wednesday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__WEDSTART__", "__EMPTY__");
            return false;
        }
        else if (!wedStart.equals("N/A") && wedEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Wednesday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__WEDEND__", "__EMPTY__");
            return false;
        }
        else if (thuStart.equals("N/A") && !thuEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Thursday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__THUSTART__", "__EMPTY__");
            return false;
        }
        else if (!thuStart.equals("N/A") && thuEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Thursday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__THUEND__", "__EMPTY__");
            return false;
        }
        else if (friStart.equals("N/A") && !friEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Friday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__FRISTART__", "__EMPTY__");
            return false;
        }
        else if (!friStart.equals("N/A") && friEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Friday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__FRIEND__", "__EMPTY__");
            return false;
        }
        else if (satStart.equals("N/A") && !satEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Saturday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__SATSTART__", "__EMPTY__");
            return false;
        }
        else if (!satStart.equals("N/A") && satEnd.equals("N/A"))
        {
            Toast.makeText(getContext(), "Please make sure both fields for Saturday are filled.", Toast.LENGTH_LONG).show();
            Log.d("__SATEND__", "__EMPTY__");
            return false;
        }

        return true;
    }
}
