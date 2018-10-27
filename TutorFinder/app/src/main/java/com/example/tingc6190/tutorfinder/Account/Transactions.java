package com.example.tingc6190.tutorfinder.Account;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tingc6190.tutorfinder.DataObject.Transaction;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;

import java.util.ArrayList;

public class Transactions extends Fragment {

    ArrayList<Transaction> transactions;
    HomeActivity homeActivity;
    TransactionAdapter transactionAdapter;

    public Transactions() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();

        transactions = new ArrayList<>();

        transactions = homeActivity.getAllTransactions();

        return inflater.inflate(R.layout.content_transaction_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            //set up our adapter and list
            ListView listView = getView().findViewById(R.id.list_transactions);
            transactionAdapter = new TransactionAdapter(getContext(), transactions);
            listView.setAdapter(transactionAdapter);

            transactionAdapter.notifyDataSetChanged();
        }
    }
}
