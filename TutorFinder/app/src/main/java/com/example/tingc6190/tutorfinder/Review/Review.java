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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.DataObject.ReviewInfo;
import com.example.tingc6190.tutorfinder.DataObject.Student;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Review extends Fragment {

    Student currentStudent;
    HomeActivity homeActivity;
    ReviewListener reviewListener;
    String tutorUID;
    ArrayList<ReviewInfo> reviews;
    View star1;
    View star2;
    View star3;
    View star4;
    View star5;
    int rate = 0;

    public Review() {
    }

    public interface ReviewListener
    {
        void pushReview(String firstName, String lastName, String currentDate, String description, String tutorUID);
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
        reviews = new ArrayList<>();

        currentStudent = homeActivity.getCurrentStudent();
        tutorUID = homeActivity.getTutor().getTutorUID();
        reviews = homeActivity.getReviews();

        Log.d("__REVIEW__", currentStudent.getEmail());

        //homeActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        homeActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return inflater.inflate(R.layout.content_review_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            star1 = getView().findViewById(R.id.review_rating_star_1);
            star2 = getView().findViewById(R.id.review_rating_star_2);
            star3 = getView().findViewById(R.id.review_rating_star_3);
            star4 = getView().findViewById(R.id.review_rating_star_4);
            star5 = getView().findViewById(R.id.review_rating_star_5);

            Log.d("__CHECREVIEWSss__", String.valueOf(reviews.size()));

            final EditText writeReview_et;
            Button publishReviewButton;

            writeReview_et = getView().findViewById(R.id.write_review_et);
            publishReviewButton = getView().findViewById(R.id.publish_review_button);



            if (reviews.size() != 0)
            {
                ListView listView = getView().findViewById(R.id.list_review);
                ReviewAdapter reviewAdapter = new ReviewAdapter(getContext(), reviews);
                listView.setAdapter(reviewAdapter);

                reviewAdapter.notifyDataSetChanged();
            }

            star1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateReviewOne();
                }
            });

            star2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateReviewTwo();
                }
            });

            star3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateReviewThree();
                }
            });

            star4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateReviewFour();
                }
            });

            star5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateReviewFive();
                }
            });

            publishReviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String description = writeReview_et.getText().toString().trim();

                    if (!TextUtils.isEmpty(description))
                    {
                        if (rate != 0)
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                            String currentDate = sdf.format(new Date());

                            String firstName = currentStudent.getFirstName();
                            String lastName = currentStudent.getLastName();

                            Log.d("__REVIEW__", currentDate);

                            reviewListener.pushReview(firstName, lastName, currentDate, description, tutorUID);

                            writeReview_et.setText("");
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Please give the tutor a rating.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Please write a review before publishing.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void rateReviewOne()
    {
        rate = 1;

        star1.setBackgroundResource(R.drawable.star_rating);
        star2.setBackgroundResource(R.drawable.star_rating_empty);
        star3.setBackgroundResource(R.drawable.star_rating_empty);
        star4.setBackgroundResource(R.drawable.star_rating_empty);
        star5.setBackgroundResource(R.drawable.star_rating_empty);
    }

    private void rateReviewTwo()
    {
        rate = 2;

        star1.setBackgroundResource(R.drawable.star_rating);
        star2.setBackgroundResource(R.drawable.star_rating);
        star3.setBackgroundResource(R.drawable.star_rating_empty);
        star4.setBackgroundResource(R.drawable.star_rating_empty);
        star5.setBackgroundResource(R.drawable.star_rating_empty);
    }

    private void rateReviewThree()
    {
        rate = 3;

        star1.setBackgroundResource(R.drawable.star_rating);
        star2.setBackgroundResource(R.drawable.star_rating);
        star3.setBackgroundResource(R.drawable.star_rating);
        star4.setBackgroundResource(R.drawable.star_rating_empty);
        star5.setBackgroundResource(R.drawable.star_rating_empty);
    }

    private void rateReviewFour()
    {
        rate = 4;

        star1.setBackgroundResource(R.drawable.star_rating);
        star2.setBackgroundResource(R.drawable.star_rating);
        star3.setBackgroundResource(R.drawable.star_rating);
        star4.setBackgroundResource(R.drawable.star_rating);
        star5.setBackgroundResource(R.drawable.star_rating_empty);
    }

    private void rateReviewFive()
    {
        rate = 5;

        star1.setBackgroundResource(R.drawable.star_rating);
        star2.setBackgroundResource(R.drawable.star_rating);
        star3.setBackgroundResource(R.drawable.star_rating);
        star4.setBackgroundResource(R.drawable.star_rating);
        star5.setBackgroundResource(R.drawable.star_rating);
    }
}
