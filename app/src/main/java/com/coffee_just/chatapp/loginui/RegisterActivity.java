package com.coffee_just.chatapp.loginui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coffee_just.chatapp.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * 注册的界面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private EditText userName, userPassword;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_step_two);
        userName = findViewById(R.id.et_register_username);
        userPassword = findViewById(R.id.et_register_pwd_input);
        //文本框的改变去控制按钮的启用
        userName.addTextChangedListener(this);
        userPassword.addTextChangedListener(this);
//        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        btnSubmit = findViewById(R.id.bt_register_submit);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.ib_navigation_back:
//                finish();
//                break;
            case R.id.bt_register_submit:
                registerUser(userName.getText().toString(), userPassword.getText().toString());
                break;
        }
    }

    private void registerUser(String username, String pwd) {
        new Thread(
                () -> {
                    try {
                        EMClient.getInstance().createAccount(username, pwd);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplication(), "注册成功了", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        Log.v("com.regiester.user",e.getDescription());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "注册失败，请稍后再试", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
        ).start();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String username = userName.getText().toString().trim();
        String pwd = userPassword.getText().toString().trim();
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
            btnSubmit.setTextColor(getResources().getColor(R.color.white));

        } else {
            btnSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));

        }

    }
}