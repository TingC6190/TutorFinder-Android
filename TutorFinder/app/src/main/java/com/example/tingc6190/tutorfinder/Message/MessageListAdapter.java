package com.example.tingc6190.tutorfinder.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tingc6190.tutorfinder.DataObject.MessageInfo;
import com.example.tingc6190.tutorfinder.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MessageListAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<ArrayList<MessageInfo>> mAllMessages;

    public MessageListAdapter(Context mContext, ArrayList<ArrayList<MessageInfo>> mAllMessages) {
        this.mContext = mContext;
        this.mAllMessages = mAllMessages;
    }


    @Override
    public int getCount() {
        if (mAllMessages != null)
        {
            return mAllMessages.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mAllMessages != null && position < mAllMessages.size() && position >= 0)
        {
            return mAllMessages.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        int BASE_ID = 0x0100011;
        return BASE_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_message_list, parent, false);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) convertView.getTag();
        }

        ArrayList<MessageInfo> messages = (ArrayList<MessageInfo>) getItem(position);

        if (messages != null)
        {

        }

        return convertView;
    }

    private class ViewHolder
    {
        final TextView name_tv;

        ViewHolder(View _view)
        {
            name_tv = _view.findViewById(R.id.cell_message_list_name);
        }
    }
}
