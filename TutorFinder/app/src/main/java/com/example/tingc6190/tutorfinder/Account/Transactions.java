package com.example.tingc6190.tutorfinder.Account;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.DataObject.Transaction;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;

import java.util.ArrayList;

public class Transactions extends Fragment {

    ArrayList<Transaction> transactions;
    ArrayList<Tutor> favoriteTutors;
    HomeActivity homeActivity;
    TransactionAdapter transactionAdapter;
    TransactionListener transactionListener;


    public Transactions() {
    }

    public interface TransactionListener
    {
        void removeTransaction(Transaction transaction);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof TransactionListener)
        {
            transactionListener = (TransactionListener) context;
        }
        else
        {
            Log.d("error", "must implement TransactionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();

        transactions = new ArrayList<>();
        favoriteTutors = new ArrayList<>();

        transactions = homeActivity.getAllTransactions();
        favoriteTutors = homeActivity.getFavoriteTutors();


        return inflater.inflate(R.layout.content_transaction_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            //set up our adapter and list
            ListView listView = getView().findViewById(R.id.list_transactions);
            transactionAdapter = new TransactionAdapter(getContext(), transactions, favoriteTutors);
            listView.setAdapter(transactionAdapter);

            transactionAdapter.notifyDataSetChanged();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());

                    final Transaction selectedTransaction = transactions.get(position);

                    String alertTitle =  "$" + selectedTransaction.getPrice() + " - " + selectedTransaction.getFirstName() + " " + selectedTransaction.getLastName() +
                            " - " + selectedTransaction.getDate();

                    String alertMessage = "Are you sure you want to refund this transaction?";

                    alertBuilder.setTitle(alertTitle);
                    alertBuilder.setMessage(alertMessage);
                    alertBuilder.setPositiveButton("Refund", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Log.d("__REFUND__", "TRANSACTION WILL BE REFUNDED");

                            Toast.makeText(homeActivity,  "$ "+ selectedTransaction.getPrice() + "has been refunded.", Toast.LENGTH_SHORT).show();

                            transactionListener.removeTransaction(selectedTransaction);
                        }
                    });
                    alertBuilder.setCancelable(true);
                    alertBuilder.show();

                }
            });
        }
    }
}
