package com.coffee_just.chatapp.Chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.coffee_just.chatapp.R;
import com.coffee_just.chatapp.Untils.L;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSend;
    private EditText chat_input;
    private Toolbar  chatToolbar;
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

    }
//初始化各个控件
    private  void initView(){
        btnSend = findViewById(R.id.btn_send);
        chat_input = findViewById(R.id.chat_input);
        chat_input.addTextChangedListener(enableButton());
        btnSend.setOnClickListener(this);
        btnSend.setEnabled(false);
        chatToolbar = findViewById(R.id.toolbar);

}

private void setToolbar(){
        String title = getIntent().getStringExtra(EXTRA_CHAT);
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

    }
}
