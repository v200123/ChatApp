package com.coffee_just.chatapp.Chat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coffee_just.chatapp.R;
import com.coffee_just.chatapp.loginui.MainActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

public class userInformationFragment extends Fragment {
    private TextView tvName;
    private Button btnExitLogin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userinformation,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = view.findViewById(R.id.tv_username);
        btnExitLogin = view.findViewById(R.id.btnExitLogin);
        btnExitLogin.setOnClickListener(l->{
            EMClient.getInstance().logout(true);
            Toast.makeText(getContext(),"退出登录",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });
        tvName.setText(EMClient.getInstance().getCurrentUser());

    }
}
