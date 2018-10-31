package com.example.tingc6190.tutorfinder.Review;

import android.content.Context;
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
            String date = review.getDate();
            String description = review.getDescription();

            vh.name_tv.setText(name);
            vh.date_tv.setText(date);
            vh.description_tv.setText(description);
        }

        return convertView;
    }

    private class ViewHolder
    {
        final TextView name_tv;
        final TextView date_tv;
        final TextView description_tv;

        ViewHolder(View _view)
        {
            name_tv = _view.findViewById(R.id.reviewer_name);
            date_tv = _view.findViewById(R.id.review_date);
            description_tv = _view.findViewById(R.id.review_description);
        }
    }
}
