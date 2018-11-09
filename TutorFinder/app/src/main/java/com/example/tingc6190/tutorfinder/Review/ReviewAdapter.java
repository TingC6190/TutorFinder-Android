package com.example.tingc6190.tutorfinder.Review;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tingc6190.tutorfinder.DataObject.ReviewInfo;
import com.example.tingc6190.tutorfinder.R;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<ReviewInfo> mReviews;

    public ReviewAdapter(Context mContext, ArrayList<ReviewInfo> mReviews) {
        this.mContext = mContext;
        this.mReviews = mReviews;
    }

    @Override
    public int getCount() {
        if (mReviews != null)
        {
            return mReviews.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mReviews != null && position < mReviews.size() && position >= 0)
        {
            return mReviews.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        int BASE_ID = 0x100011;
        return BASE_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_review, parent, false);

            vh = new ViewHolder(convertView);

            convertView.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) convertView.getTag();
        }

        ReviewInfo review = (ReviewInfo) getItem(position);

        if (review != null)
        {
            String name = review.getFirstName() + " " + review.getLastName();
            //String name = "Anonymous";
            String date = review.getDate();
            String description = review.getDescription();
            int rate = review.getRate();

            Log.d("__REVIEW_RATING__", String.valueOf(rate));

            vh.name_tv.setText(name);
            vh.date_tv.setText(date);
            vh.description_tv.setText(description);

            switch (rate)
            {
                case 1:
                {
                    vh.star1.setBackgroundResource(R.drawable.star_rating);
                    vh.star2.setBackgroundResource(R.drawable.star_rating_empty);
                    vh.star3.setBackgroundResource(R.drawable.star_rating_empty);
                    vh.star4.setBackgroundResource(R.drawable.star_rating_empty);
                    vh.star5.setBackgroundResource(R.drawable.star_rating_empty);
                    break;
                }
                case 2:
                {
                    vh.star1.setBackgroundResource(R.drawable.star_rating);
                    vh.star2.setBackgroundResource(R.drawable.star_rating);
                    vh.star3.setBackgroundResource(R.drawable.star_rating_empty);
                    vh.star4.setBackgroundResource(R.drawable.star_rating_empty);
                    vh.star5.setBackgroundResource(R.drawable.star_rating_empty);
                    break;
                }
                case 3:
                {
                    vh.star1.setBackgroundResource(R.drawable.star_rating);
                    vh.star2.setBackgroundResource(R.drawable.star_rating);
                    vh.star3.setBackgroundResource(R.drawable.star_rating);
                    vh.star4.setBackgroundResource(R.drawable.star_rating_empty);
                    vh.star5.setBackgroundResource(R.drawable.star_rating_empty);
                    break;
                }
                case 4:
                {
                    vh.star1.setBackgroundResource(R.drawable.star_rating);
                    vh.star2.setBackgroundResource(R.drawable.star_rating);
                    vh.star3.setBackgroundResource(R.drawable.star_rating);
                    vh.star4.setBackgroundResource(R.drawable.star_rating);
                    vh.star5.setBackgroundResource(R.drawable.star_rating_empty);
                    break;
                }
                case 5:
                {
                    vh.star1.setBackgroundResource(R.drawable.star_rating);
                    vh.star2.setBackgroundResource(R.drawable.star_rating);
                    vh.star3.setBackgroundResource(R.drawable.star_rating);
                    vh.star4.setBackgroundResource(R.drawable.star_rating);
                    vh.star5.setBackgroundResource(R.drawable.star_rating);
                    break;
                }
            }
        }

        return convertView;
    }

    private class ViewHolder
    {
        final TextView name_tv;
        final TextView date_tv;
        final TextView description_tv;
        final View star1;
        final View star2;
        final View star3;
        final View star4;
        final View star5;

        ViewHolder(View _view)
        {
            name_tv = _view.findViewById(R.id.reviewer_name);
            date_tv = _view.findViewById(R.id.review_date);
            description_tv = _view.findViewById(R.id.review_description);
            star1 = _view.findViewById(R.id.cell_review_rating_star_1);
            star2 = _view.findViewById(R.id.cell_review_rating_star_2);
            star3 = _view.findViewById(R.id.cell_review_rating_star_3);
            star4 = _view.findViewById(R.id.cell_review_rating_star_4);
            star5 = _view.findViewById(R.id.cell_review_rating_star_5);
        }
    }


}
