package com.example.tingc6190.tutorfinder.Account;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tingc6190.tutorfinder.DataObject.Transaction;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Transaction> mTransactions;
    private final ArrayList<Tutor> mFavoriteTutors;

    public TransactionAdapter(Context mContext, ArrayList<Transaction> mTransactions, ArrayList<Tutor> mFavoriteTutors) {
        this.mContext = mContext;
        this.mTransactions = mTransactions;
        this.mFavoriteTutors = mFavoriteTutors;
    }

    @Override
    public int getCount() {
        if (mTransactions != null)
        {
            return mTransactions.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mTransactions != null && position < mTransactions.size() && position >= 0)
        {
            return mTransactions.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        int BASE_ID = 0x011011;
        return BASE_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_transactions, parent, false);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) convertView.getTag();
        }

       Transaction transaction = (Transaction) getItem(position);

        if (transaction != null)
        {
            String name = transaction.getFirstName() + " " + transaction.getLastName();
            String price = "$" + transaction.getPrice();
            String date = transaction.getDate();
            String email = transaction.getEmail();

            vh.transactionName_tv.setText(name);
            vh.transactionPrice_tv.setText(price);
            vh.transactionDate_tv.setText(date);

            vh.isFavorite.setVisibility(View.INVISIBLE);
            vh.isNotFavorite.setVisibility(View.VISIBLE);

            //check to see if tutor is a favorite or not
            if (mFavoriteTutors != null)
            {
                for (int i = 0; i < mFavoriteTutors.size(); i++)
                {
                    if (mFavoriteTutors.get(i).getEmail().equals(email))
                    {
                        vh.isFavorite.setVisibility(View.VISIBLE);
                        vh.isNotFavorite.setVisibility(View.INVISIBLE);
                        Log.d("________", "IS FAVORITE");
                    }
                }
            }






            if (transaction.getPictureUrl() != null)
            {
                if (!transaction.getPictureUrl().trim().equals(""))
                {
                    Picasso.get().load(transaction.getPictureUrl()).into(vh.transactionPicture_riv);
                }
                else
                {
                    vh.transactionPicture_riv.setImageResource(R.drawable.default_profile);
                }

            }


        }


        return convertView;
    }

    private class ViewHolder
    {
        final CircleImageView transactionPicture_riv;
        final TextView transactionName_tv;
        final TextView transactionPrice_tv;
        final TextView transactionDate_tv;
        final View isFavorite;
        final View isNotFavorite;


        ViewHolder(View _view)
        {
            transactionPicture_riv = _view.findViewById(R.id.cell_transaction_picture);
            transactionName_tv = _view.findViewById(R.id.cell_transaction_name);
            transactionPrice_tv = _view.findViewById(R.id.cell_transaction_price);
            transactionDate_tv = _view.findViewById(R.id.cell_transaction_date);
            isFavorite = _view.findViewById(R.id.transaction_favorite_button_checked);
            isNotFavorite = _view.findViewById(R.id.transaction_favorite_button_unchecked);
        }
    }
}
