package com.coffee_just.chatapp.bean;

import com.hyphenate.chat.EMMessage;

import org.litepal.crud.LitePalSupport;

public class Message extends LitePalSupport {
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setType(EMMessage.Direct type) {
        Type = type;
    }

    private String fromUser;
    private String toUser;
    private String Message;
    private EMMessage.Direct Type;

    public Message(String fromUser, EMMessage.Direct type, String message) {
        Type = type;
    }


    public String getMessage() {
        return Message;
    }


}
