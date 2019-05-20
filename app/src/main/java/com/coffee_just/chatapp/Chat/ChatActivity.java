package com.coffee_just.chatapp.Chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffee_just.chatapp.Chat.adapter.weChatFragmentAdapter;
import com.coffee_just.chatapp.R;
import com.coffee_just.chatapp.Untils.L;
import com.coffee_just.chatapp.bean.Message;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.util.NetUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSend;
    private EditText chat_input;
    private Toolbar  chatToolbar;
    private weChatFragmentAdapter mAdapter;
    private String title;
    private RecyclerView mRecyclerView;
    private static boolean isRunning = false;
    @SuppressLint("HandlerLeak")

    private static ArrayList<Message> mMessages ;
    public static final String  EXTRA_CHAT= "com.lc.wechat.chat";
    public static Intent instance(Context context, String id){
            Intent i = new Intent(context,ChatActivity.class);
            i.putExtra(EXTRA_CHAT,id);
            return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_chat);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setToolbar();
        isRunning = true;

    }
//初始化各个控件
    private  void initView(){
        btnSend = findViewById(R.id.btn_send);
        chat_input = findViewById(R.id.chat_input);
        chat_input.addTextChangedListener(enableButton());
        btnSend.setOnClickListener(this);
        btnSend.setEnabled(false);
        chatToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.chat_recycle_view);
//        mMessages = (ArrayList<Message>) LitePal.where("fromUser =  ? ",title).find(Message.class);

}

    private void initData(){
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(title);
//获取此会话的所有消息
        List<EMMessage> messages = conversation.getAllMessages();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new weChatFragmentAdapter(getApplicationContext(),mMessages);
        mRecyclerView.setAdapter(mAdapter);
    }

private void setToolbar(){
         title= getIntent().getStringExtra(EXTRA_CHAT);
        chatToolbar.setTitle(title);
}

private TextWatcher enableButton(){
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    btnSend.setEnabled(false);
                }
                else
                btnSend.setEnabled(true);
            }
        };
        return watcher;
}

    @Override
    public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn_send:
                    if(!TextUtils.isEmpty(chat_input.getText())){
                        sendMsg(chat_input.getText().toString());
                        chat_input.setText("");
                    }
            }
    }
//销毁并且解除消息的监听
    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


    private void sendMsg(String content)
    {
        EMMessage message = EMMessage.createTxtSendMessage(content,getIntent().getStringExtra(EXTRA_CHAT));
        EMClient.getInstance().chatManager().sendMessage(message);
    }


    public static void reflashUI(String id){
        if(isRunning) {

        }
        }



}
