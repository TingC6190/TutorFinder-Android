package com.example.tingc6190.tutorfinder.Message;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tingc6190.tutorfinder.DataObject.MessageInfo;
import com.example.tingc6190.tutorfinder.DataObject.Student;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MessageList extends Fragment {

    private ArrayList<ArrayList<MessageInfo>> allMessages;
    private HomeActivity homeActivity;
    private MessageListListener messageListListener;
    private ArrayList<String> messageTutorUID;
    private ArrayList<String> allStudentUID;
    private ArrayList<Student> allStudentData;
    private ArrayList<Student> filteredStudentData;
    ArrayList<Tutor> tutors = new ArrayList<>();

    public MessageList() {
    }

    public interface MessageListListener
    {
        void getTutorMessage(ArrayList<MessageInfo> tutorMessages, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MessageListListener)
        {
            messageListListener = (MessageListListener) context;
        }
        else
        {
            Log.d("error", "must implement MessageListListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();
        allMessages = new ArrayList<>();
        messageTutorUID = new ArrayList<>();
        allStudentUID = new ArrayList<>();
        allStudentData = new ArrayList<>();
        filteredStudentData = new ArrayList<>();

        tutors = homeActivity.getTutors();
        allMessages = homeActivity.getAllMessage();
        messageTutorUID = homeActivity.getMessageTutorUID();
        allStudentUID = homeActivity.getAllStudentUID();
        allStudentData = homeActivity.getAllStudentData();


        Log.d("__INSIDELIST__", String.valueOf(allMessages.size()));

        return inflater.inflate(R.layout.content_message_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {

            ArrayList<String> nameList = new ArrayList<>();
            for (int i = 0; i < messageTutorUID.size(); i++)
            {
                for (int j = 0; j < tutors.size(); j++)
                {
                    if (messageTutorUID.get(i).equals(tutors.get(j).getTutorUID()))
                    {
                        Log.d("__NAMELIST__", tutors.get(j).getTutorUID());
                        String name = tutors.get(j).getFirstName() + " " + tutors.get(j).getLastName();
                        nameList.add(name);
                    }
                }
            }

            if (allMessages.size() != 0)
            {
                ListView listView = getView().findViewById(R.id.list_message);
                MessageListAdapter messageListAdapter = new MessageListAdapter(getContext(), allMessages, nameList);
                listView.setAdapter(messageListAdapter);

                messageListAdapter.notifyDataSetChanged();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        messageListListener.getTutorMessage(allMessages.get(position), position);
                    }
                });
            }

        }
    }
}
