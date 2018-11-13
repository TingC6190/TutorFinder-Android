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
//            for (int i = 0; i < allStudentUID.size(); i++)
//            {
//                allStudentData.get(i).setAboutMe(allStudentUID.get(i));
//
//            }
//
//            for (int i = 0; i < messageTutorUID.size(); i++)
//            {
//                for (int j = 0; j < allStudentData.size(); j++)
//                {
//                    if (allStudentData.get(j).getAboutMe().equals(messageTutorUID.get(i).toString()))
//                    {
//                        filteredStudentData.add(allStudentData.get(j));
//                    }
//                }
//            }
//
//            Log.d("__CHECKINFOOOOOO__","____________1");
//            for (int i = 0; i < filteredStudentData.size(); i++)
//            {
//                Log.d("__CHECKINFOOOOOO__", filteredStudentData.get(i).getFirstName() + " " + filteredStudentData.get(i).getLastName() + "====" + filteredStudentData.get(i).getAboutMe());
//            }
//            Log.d("__CHECKINFOOOOOO__","____________2");
//            for (int i = 0; i < messageTutorUID.size(); i++)
//            {
//                //messageTutorUID.get(i).toString();
//                Log.d("__MTUTORUID__", messageTutorUID.get(i).toString());
//            }
//            Log.d("__MTUTORUID__", "====================");

            if (allMessages.size() != 0)
            {
                ListView listView = getView().findViewById(R.id.list_message);
                MessageListAdapter messageListAdapter = new MessageListAdapter(getContext(), allMessages);
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
