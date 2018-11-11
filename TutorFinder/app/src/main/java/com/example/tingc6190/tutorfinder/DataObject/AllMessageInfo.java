package com.example.tingc6190.tutorfinder.DataObject;

import java.util.ArrayList;

public class AllMessageInfo {

    private ArrayList<MessageInfo> messages;
    private String name;

    public AllMessageInfo() {
    }

    public AllMessageInfo(ArrayList<MessageInfo> messages, String name) {
        this.messages = messages;
        this.name = name;
    }

    public ArrayList<MessageInfo> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageInfo> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
