package com.coffee_just.chatapp.feedback.model;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.coffee_just.chatapp.bean.FeedBackMsg;
import com.coffee_just.chatapp.feedback.Listener.OnSendListener;

public class FeedBack implements IFeedBack {
    private final Uri uri = Uri.parse("mailto:");
    @Override
    public void sendMag(Context context,FeedBackMsg msg, OnSendListener mListener) {
        Intent i = new Intent(Intent.ACTION_SENDTO);
//        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT,"这是一个标题");
        i.putExtra(Intent.EXTRA_EMAIL,"llhm2013lc@foxmail.com");
        try {
            context.startActivity(i);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }
}
