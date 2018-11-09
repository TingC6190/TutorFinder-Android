package com.example.tingc6190.tutorfinder.Message;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message extends Fragment {

    private EditText message_et;
    private View sendMessageButton;
    private HomeActivity homeActivity;
    private Tutor tutor;
    private String currentUserUID;
    private MessageListener messageListener;

    public Message() {
    }

    public interface MessageListener
    {
        void pushMessage(String fromUserUID, String toTutorUID, String dateTime, String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MessageListener)
        {
            messageListener = (MessageListener) context;
        }
        else
        {
            Log.d("error", "must implement MessageListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();
        tutor = new Tutor();

        tutor = homeActivity.getTutor();
        currentUserUID = homeActivity.getCurrentUserUID();


        return inflater.inflate(R.layout.content_message_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            message_et = getView().findViewById(R.id.message_et);
            sendMessageButton = getView().findViewById(R.id.send_message_button);


            sendMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String message = message_et.getText().toString().trim();

                    if(TextUtils.isEmpty(message))
                    {
                        Toast.makeText(getContext(), "Please write something before sending.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //send message to database
                        Log.d("__INSIDEMESSAGES__", "message from " + currentUserUID + " to " + tutor.getTutorUID());

                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy (hh:mm:ss)");
                        String currentDateTime = sdf.format(new Date());

                        messageListener.pushMessage(currentUserUID, tutor.getTutorUID(), currentDateTime, message);
                    }
                }
            });
        }
    }
}
