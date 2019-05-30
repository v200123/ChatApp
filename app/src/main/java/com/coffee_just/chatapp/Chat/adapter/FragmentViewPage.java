package com.coffee_just.chatapp.Chat.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coffee_just.chatapp.Chat.Fragment.userInformationFragment;
import com.coffee_just.chatapp.Chat.Fragment.contactFragment;
import com.coffee_just.chatapp.Chat.Fragment.weChatFagment;

public class FragmentViewPage extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"信息","通信录","我"};
    public FragmentViewPage(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position ==0)
        {
            return new weChatFagment();
        }
        if(
                position==1
        )
        return new contactFragment();

        return new userInformationFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
