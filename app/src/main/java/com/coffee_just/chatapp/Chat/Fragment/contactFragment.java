package com.coffee_just.chatapp.Chat.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffee_just.chatapp.Chat.adapter.RecycleViewAdapter;
import com.coffee_just.chatapp.R;
import com.coffee_just.chatapp.Untils.L;
import com.coffee_just.chatapp.bean.contactInfo;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;

import java.util.List;

public class contactFragment extends Fragment {
    private RecyclerView rvWeChat;
    private RecycleViewAdapter mAdapter;
    private boolean isFinsh = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wechat, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvWeChat = view.findViewById(R.id.rv_wechat);
        //获取联系人的列表
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    List<String> list = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    mAdapter = new RecycleViewAdapter(getContext(),list);
                    L.e("setAdapter:" + "获取列表成功了");
                    if (LitePal.count(contactInfo.class) != list.size()) {
                        LitePal.deleteAll(contactInfo.class,"");
                        for (String hxid : list
                        ) {
                            contactInfo contactInfo = new contactInfo(hxid);
                            if (contactInfo.save()) {
                                L.d("联系人数据库操作" + "储存成功");
                            }

                        }

                    }
                    isFinsh = true;
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    L.d("没有获取成功");
                }

            }
        }.start();
        while (!isFinsh)
        {
            L.d("我再等待中");
        }
        rvWeChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWeChat.setAdapter(mAdapter);
        Listener();
    }

    private void Listener(){
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            @Override
            public void onContactAdded(String s) {

            }

            @Override
            public void onContactDeleted(String s) {

            }

            @Override
            public void onContactInvited(String s, String s1) {

            }

            @Override
            public void onFriendRequestAccepted(String s) {

            }

            @Override
            public void onFriendRequestDeclined(String s) {

            }
        });
}}
