package com.example.tingc6190.tutorfinder.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tingc6190.tutorfinder.R;

import java.util.ArrayList;

public class TutorAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Tutor> mTutors;

    TutorAdapter(Context mContext, ArrayList<Tutor> mTutors) {
        this.mContext = mContext;
        this.mTutors = mTutors;
    }


    @Override
    public int getCount() {
        if (mTutors != null)
        {
            return mTutors.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mTutors != null && position < mTutors.size() && position >= 0)
        {
            return mTutors.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        int BASE_ID = 0x010101;
        return BASE_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_search, parent, false);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) convertView.getTag();
        }

        Tutor tutor = (Tutor) getItem(position);

        if (tutor != null)
        {
            String name = tutor.getFirstName() + " " + tutor.getLastName();
            String rating = String.valueOf(tutor.getRating()) + " Rating";
            String price = "$" + String.valueOf(tutor.getPrice()) + "/hr";
            String subject = tutor.getSubject();

            vh.name.setText(name);
            vh.rating.setText(rating);
            vh.price.setText(price);
            vh.subjects.setText(subject);

        }


        return convertView;
    }

    private class ViewHolder
    {
        final TextView name;
        final TextView rating;
        final TextView price;
        final TextView subjects;

        ViewHolder(View _view)
        {
            name = _view.findViewById(R.id.tutor_name);
            rating = _view.findViewById(R.id.tutor_rating);
            price = _view.findViewById(R.id.tutor_price);
            subjects = _view.findViewById(R.id.tutor_subject);
        }
    }
}
