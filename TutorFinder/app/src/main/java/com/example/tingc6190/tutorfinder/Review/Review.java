package com.example.tingc6190.tutorfinder.Review;

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

import java.text.SimpleDateFormat;
import java.util.Date;

public class Review extends Fragment {

    Student currentStudent;
    HomeActivity homeActivity;
    ReviewListener reviewListener;
    String tutorUID;

    public Review() {
    }

    public interface ReviewListener
    {
        void getReview(String firstName, String lastName, String currentDate, String description, String tutorUID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ReviewListener)
        {
            reviewListener = (ReviewListener) context;
        }
        else
        {
            Log.d("error", "must implement ReviewListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();
        currentStudent = new Student();

        currentStudent = homeActivity.getCurrentStudent();
        tutorUID = homeActivity.getTutor().getTutorUID();

        Log.d("__REVIEW__", currentStudent.getEmail());

        return inflater.inflate(R.layout.content_review_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            final EditText writeReview_et;
            Button publishReviewButton;

            writeReview_et = getView().findViewById(R.id.write_review_et);
            publishReviewButton = getView().findViewById(R.id.publish_review_button);


            publishReviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String description = writeReview_et.getText().toString().trim();

                    if (!TextUtils.isEmpty(description))
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        String currentDate = sdf.format(new Date());

                        String firstName = currentStudent.getFirstName();
                        String lastName = currentStudent.getLastName();

                        Log.d("__REVIEW__", currentDate);

                        reviewListener.getReview(firstName, lastName, currentDate, description, tutorUID);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Please write a review before publishing.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
