package com.coffee_just.chatapp.feedback.model;

import android.content.Context;

import com.coffee_just.chatapp.bean.FeedBackMsg;
import com.coffee_just.chatapp.feedback.Listener.OnSendListener;

public interface IFeedBack {
    void sendMag(Context context,FeedBackMsg msg, OnSendListener mListener);
}
