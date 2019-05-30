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
import com.coffee_just.chatapp.Untils.EaseCommonUtils;
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
    private EMMessageListener msgListener;
    protected int pagesize = 20;
    private EMConversation conversation;
    @SuppressLint("HandlerLeak")

    private static List<EMMessage> mMessages ;
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
        initData();
        addListerMsg();
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
        title = getIntent().getStringExtra(EXTRA_CHAT);
//        mMessages = (ArrayList<Message>) LitePal.where("fromUser =  ? ",title).find(Message.class);

}

    private void initData(){
        getAllMessage();
        mMessages = conversation.getAllMessages();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new weChatFragmentAdapter(getApplicationContext(),mMessages);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(mMessages.size()-1);
    }

private void setToolbar(){
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
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        L.d("chatActivity"+"消息监听已经被移除了");

    }


    private void sendMsg(String content)
    {
        EMMessage message = EMMessage.createTxtSendMessage(content,getIntent().getStringExtra(EXTRA_CHAT));
        EMClient.getInstance().chatManager().sendMessage(message);
        mMessages.add(message);
        mAdapter.notifyItemInserted(mMessages.size()-1);
        mRecyclerView.scrollToPosition(mMessages.size()-1);
        chat_input.setText("");

    }



        private void addListerMsg() {
            msgListener = new EMMessageListener() {

                @Override
                public void onMessageReceived(List<EMMessage> messages) {

                    for (EMMessage message : messages) {
                        String username = null;
                        // 群组消息
                        L.d("ChatActi"+"接收到消息了，发信人是"+message.getFrom());
                        if (message.getChatType() == EMMessage.ChatType.GroupChat || message.getChatType() == EMMessage.ChatType.ChatRoom) {
                            username = message.getTo();
                        } else {
                            // 单聊消息
                            username = message.getFrom();
                        }
                        // 如果是当前会话的消息，刷新聊天页面
                        if (username.equals(title)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mMessages.add(message);
                                    mAdapter.notifyItemInserted(mMessages.size()-1);
                                    mRecyclerView.scrollToPosition(mMessages.size()-1);
                                    L.d("视图更新了,当前的集合长度为"+mMessages.size());
                                }
                            });


                        }
                    }

                }

                @Override
                public void onCmdMessageReceived(List<EMMessage> list) {

                }

                @Override
                public void onMessageRead(List<EMMessage> list) {

                }

                @Override
                public void onMessageDelivered(List<EMMessage> list) {

                }

                @Override
                public void onMessageRecalled(List<EMMessage> list) {

                }

                @Override
                public void onMessageChanged(EMMessage emMessage, Object o) {

                }


            };
            EMClient.getInstance().chatManager().addMessageListener(msgListener);
        }

    protected void getAllMessage() {
        // 获取当前conversation对象

         conversation = EMClient.getInstance().chatManager().getConversation(title, EMConversation.EMConversationType.Chat,true);
        // 把此会话的未读数置为0
        conversation.markAllMessagesAsRead();
        // 初始化db时，每个conversation加载数目是getChatOptions().getNumberOfMessagesLoaded
        // 这个数目如果比用户期望进入会话界面时显示的个数不一样，就多加载一些
        final List<EMMessage> msgs = conversation.getAllMessages();
        int msgCount = msgs != null ? msgs.size() : 0;
        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
            String msgId = null;
            if (msgs != null && msgs.size() > 0) {
                msgId = msgs.get(0).getMsgId();
            }
            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
        }

    }
}
