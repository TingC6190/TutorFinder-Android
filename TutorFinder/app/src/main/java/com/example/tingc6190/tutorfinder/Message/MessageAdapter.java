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

public class MessageAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<MessageInfo> mMessages;

    public MessageAdapter(Context mContext, ArrayList<MessageInfo> mMessages) {
        this.mContext = mContext;
        this.mMessages = mMessages;
    }

    @Override
    public int getCount() {
        if (mMessages != null)
        {
            return mMessages.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mMessages != null && position < mMessages.size() && position >= 0)
        {
            return mMessages.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        int BASE_ID = 0x110110;
        return BASE_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_message, parent, false);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) convertView.getTag();
        }

        MessageInfo message = (MessageInfo) getItem(position);

        if (message != null)
        {
            String nameUID = message.getFromUID();
            String dateTime = message.getDateTime();
            String desc = message.getMessage();

            vh.messageName_et.setText(nameUID);
            vh.messageDate_et.setText(dateTime);
            vh.messageDesc_et.setText(desc);
        }

        return convertView;
    }

    private class ViewHolder
    {
        final TextView messageName_et;
        final TextView messageDate_et;
        final TextView messageDesc_et;

        ViewHolder(View _view)
        {
            messageName_et = _view.findViewById(R.id.message_name);
            messageDate_et = _view.findViewById(R.id.message_date);
            messageDesc_et = _view.findViewById(R.id.message_description);
        }
    }
}
