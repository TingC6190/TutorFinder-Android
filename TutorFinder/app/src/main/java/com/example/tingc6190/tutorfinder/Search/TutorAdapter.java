package com.example.tingc6190.tutorfinder.Search;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.DataObject.Location;
import com.example.tingc6190.tutorfinder.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

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

            Location location = tutor.getLocation();

            String tutorLocation = location.getCity() + ", " + location.getState() + " " + location.getZipcode();

            vh.name.setText(name);
            vh.rating.setText(rating);
            vh.price.setText(price);
            vh.subjects.setText(subject);
            vh.location.setText(tutorLocation);


            if (tutor.getPicture() != null)
            {
                if (!tutor.getPicture().equals(""))
                {
                    Picasso.get().load(tutor.getPicture()).into(vh.profilePicture);
                }
            }

            Log.d("___________", tutor.getFirstName() + " " + tutor.getLastName());
        }


        return convertView;
    }

    private class ViewHolder
    {
        final TextView name;
        final TextView rating;
        final TextView price;
        final TextView subjects;
        final TextView location;
        final RoundedImageView profilePicture;

        ViewHolder(View _view)
        {
            name = _view.findViewById(R.id.tutor_name);
            rating = _view.findViewById(R.id.tutor_rating);
            price = _view.findViewById(R.id.tutor_price);
            subjects = _view.findViewById(R.id.tutor_subject);
            location = _view.findViewById(R.id.tutor_location);
            profilePicture = _view.findViewById(R.id.cell_profile_picture);
        }
    }
}
