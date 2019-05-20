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

    public void setType(String type) {
        Type = type;
    }

    private String fromUser;
    private String toUser;
    private String Message;
    private String Type;

    public Message(String fromUser, String type, String message) {
        this(fromUser, message);
        Type = type;
    }


    public Message(String fromUser, String message) {
        this.fromUser = fromUser;
        Message = message;
    }

    public String getMessage() {
        return Message;
    }


}
