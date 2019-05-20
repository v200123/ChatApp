package com.coffee_just.chatapp.Chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.coffee_just.chatapp.Untils.L;

public class MessageService extends Service {
    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.d("广播已经开始了");
    }

}
