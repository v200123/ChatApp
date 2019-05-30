package com.coffee_just.chatapp.Chat;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.coffee_just.chatapp.Chat.adapter.FragmentViewPage;
import com.coffee_just.chatapp.R;
import com.coffee_just.chatapp.Untils.L;
import com.coffee_just.chatapp.bean.Message;
import com.google.android.material.tabs.TabLayout;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.NetUtils;

import java.util.List;

public class ChatViewActivity extends AppCompatActivity {
    private ViewPager mViewPageChatView;
    private TabLayout mTabLayoutChatView;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private EMMessageListener msgListener;//监听消息

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);
        initView();
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
    }

    private void initView() {
        mViewPageChatView = findViewById(R.id.vp_chat_view);
        mTabLayoutChatView = findViewById(R.id.tl_chat_tabs);
        mTabLayoutChatView.setupWithViewPager(mViewPageChatView);
        FragmentViewPage page = new FragmentViewPage(getSupportFragmentManager());
        mViewPageChatView.setAdapter(page);

        one = mTabLayoutChatView.getTabAt(0);
        two = mTabLayoutChatView.getTabAt(1);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        L.d("监听消息已经被注销");
    }

    private class MyConnectionListener implements EMConnectionListener {

        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(int errorCode) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (errorCode == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除

                    } else if (errorCode == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        L.d("账号已经被踢出去了");
                        EMClient.getInstance().logout(true);
                    } else {
                        if (NetUtils.hasNetwork(ChatViewActivity.this)) {

                        }
                        //连接不到聊天服务器
                        else {
                            L.d("网络连接异常");
                        }
                        //当前网络不可用，请检查网络设置
                    }
                }
            });
        }

    }


}
