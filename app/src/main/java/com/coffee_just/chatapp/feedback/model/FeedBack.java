package com.coffee_just.chatapp.feedback.model;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.coffee_just.chatapp.bean.FeedBackMsg;
import com.coffee_just.chatapp.feedback.Listener.OnSendListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

public class FeedBack implements IFeedBack {
    private final Uri uri = Uri.parse("mailto:");
    @Override
    public void sendMag(String msg, OnSendListener mListener) {
        if(msg.isEmpty())
        {
            mListener.failed();

        }
        else {
        EMMessage message = EMMessage.createTxtSendMessage(msg,"v200123");
        EMClient.getInstance().chatManager().sendMessage(message);
        mListener.success();
        }

    }
}
