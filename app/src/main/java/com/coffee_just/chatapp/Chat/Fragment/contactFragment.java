package com.coffee_just.chatapp.Chat.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.coffee_just.chatapp.bean.ModelContactInfo;
import com.coffee_just.chatapp.bean.contactInfo;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class contactFragment extends Fragment {
    private RecyclerView rv_contact;
    private RecycleViewAdapter mAdapter;
    private ArrayList<contactInfo> mContactInfos;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    rv_contact.setAdapter(mAdapter);
                    rv_contact.setLayoutManager(new LinearLayoutManager(getActivity()));
                    ListenContact();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wechat, container, false);
        rv_contact = v.findViewById(R.id.rv_wechat);

        new Thread(new getContact()).start();

        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

//从服务器中获取好友列表，并且储存在数据库中
    class getContact implements Runnable {

        @Override
        public void run() {
            try {
                List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();

                if (usernames.size() < LitePal.count(contactInfo.class)) {
                    LitePal.deleteAll(contactInfo.class, "");
                }

                for (String name : usernames
                ) {
                    contactInfo info = new contactInfo(name);
                    if (!LitePal.isExist(contactInfo.class, "userName =  ? ", info.getUserName()))
                        info.save();
                }
                L.d("数据加载完成");
                mContactInfos = ModelContactInfo.instance(getActivity()).getContactInfos();
                mAdapter = new RecycleViewAdapter(getActivity(), mContactInfos);
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    private void ListenContact() {

        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {
                contactInfo info = new contactInfo(s);
                mContactInfos.add(info);
                getActivity().runOnUiThread(() -> {
                    mAdapter.notifyItemInserted(mContactInfos.size());
                    L.d("数据已经在异步得到更新");
                });

            }

            @Override
            public void onContactDeleted(String s) {
                for (int i =0;i<mContactInfos.size();i++ ) {
                    if (mContactInfos.get(i).getUserName().equals(s)) {
                        mContactInfos.remove(i);
                        break;
                    }
                }
                L.d("遍历完毕，并且已经删除了");
                getActivity().runOnUiThread(() -> {
                    mAdapter.notifyDataSetChanged();
                    L.d("数据已经在异步得到更新->删除操作");
                });
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
}
