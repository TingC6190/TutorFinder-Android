package com.example.tingc6190.tutorfinder.Favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteTutorAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Tutor> mTutors;

    public FavoriteTutorAdapter(Context mContext, ArrayList<Tutor> mTutors) {
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
        int BASE_ID = 0x001101;
        return BASE_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_favorites, parent, false);

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

            vh.tutorName_tv.setText(name);

            if (tutor.getPicture() != null)
            {
                if (!tutor.getPicture().equals(""))
                {
                    Picasso.get().load(tutor.getPicture()).into(vh.tutorPicture_riv);
                }
            }
        }


        return convertView;
    }

    private class ViewHolder
    {
        final RoundedImageView tutorPicture_riv;
        final TextView tutorName_tv;

        ViewHolder(View _view)
        {
            tutorPicture_riv = _view.findViewById(R.id.cell_favorite_picture);
            tutorName_tv = _view.findViewById(R.id.favorite_tutor_name);
        }
    }
}
