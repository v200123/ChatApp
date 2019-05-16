package com.coffee_just.chatapp.Chat.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffee_just.chatapp.Chat.adapter.RecycleViewAdapter;
import com.coffee_just.chatapp.R;
import com.coffee_just.chatapp.Untils.L;
import com.coffee_just.chatapp.bean.ModelContactInfo;
import com.coffee_just.chatapp.bean.contactInfo;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class contactFragment extends Fragment {
    private RecyclerView rvWeChat;
    private RecycleViewAdapter mAdapter;
    private boolean isFinsh = false;
    private Button addNewFriend;
    private static final int ADD_CONTACT = 0;
    private static final int DELECT_CONTACT = 1;

    private ArrayList<contactInfo> infos;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    infos.add((contactInfo) msg.obj);
                    mAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    infos = ModelContactInfo.instance(getContext()).getContactInfos();
                    mAdapter.notifyDataSetChanged();
                    mAdapter.notifyItemRemoved(infos.size()-1);
                    break;
            }
        }
    };


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
        addNewFriend = view.findViewById(R.id.add_new_friend);

        //获取联系人的列表
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    List<String> list = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    if (LitePal.count(contactInfo.class) != list.size()) {
                        LitePal.deleteAll(contactInfo.class, "");
                        for (String hxid : list
                        ) {
                            contactInfo contactInfo = new contactInfo(hxid);
                            if (contactInfo.save()) {
                                L.d("联系人数据库操作" + "储存成功");
                            }
                        }
                    }
                    isFinsh = true;
                    L.e("setAdapter:" + "获取列表成功了");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    L.d("没有获取成功");
                }

            }
        }.start();
        while (!isFinsh) {
            L.d("我再等待中");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        infos = ModelContactInfo.instance(getContext()).getContactInfos();
        mAdapter = new RecycleViewAdapter(getContext(), infos);
        rvWeChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWeChat.setAdapter(mAdapter);
        Listener();
    }

    /**
     * 监听联系表的
     */
    private void Listener() {
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            @Override
            public void onContactAdded(String s) {
                contactInfo info = new contactInfo(s);
                editContact(ADD_CONTACT,info);
                L.d("新增储存成功");
                Message message = new Message();
                message.what = 1;
                message.obj = info;
                mHandler.sendMessage(message);


            }

            @Override
            public void onContactDeleted(String s) {
                contactInfo info = new contactInfo(s);

                editContact(DELECT_CONTACT,info);
                L.d("正在删除中");
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessage(message);

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
    }

    /**
     * @param Flag 判断是删除还是增加
     */
    private void editContact(int Flag, contactInfo info) {
        switch (Flag) {
            case 0:
                info.save();
                break;
            case 1:
                LitePal.deleteAll(contactInfo.class,"userName = ?",info.getUserName());
                L.d("删除成功");
                break;

        }
    }
}
