package com.coffee_just.chatapp.Chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.coffee_just.chatapp.R;
import com.coffee_just.chatapp.bean.contactInfo;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddFridensDialogFragment extends DialogFragment implements TextWatcher, View.OnClickListener {
    private EditText edInputName;
    private Button btnSubmit;
    private ArrayList <contactInfo>mList = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_addfriend,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edInputName = view.findViewById(R.id.et_input_name);
        btnSubmit =  view.findViewById(R.id.button);
        edInputName.addTextChangedListener(this);
    mList = (ArrayList<contactInfo>) LitePal.findAll(contactInfo.class);
    btnSubmit.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(count == 3)
            {
                for (contactInfo addedFriend:mList
                     ) {
                    String userName = addedFriend.getUserName();
                    if(userName.contentEquals(s))
                    {
                        Toast.makeText(getContext(),"当前用户已经添加了",Toast.LENGTH_SHORT).show();
                    }
                }
            }
    }

    @Override
    public void afterTextChanged(Editable s) {
        for (contactInfo addedFriend:mList
        ) {
            String userName = addedFriend.getUserName();
            if(userName.contentEquals(s))
            {
                Toast.makeText(getContext(),"当前用户已经添加了",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        String name = edInputName.getText().toString().trim();
        AtomicBoolean isSuccess = new AtomicBoolean(false);
            new Thread(()->{
                try {
                    EMClient.getInstance().contactManager().addContact(name, "小姐姐");
                    isSuccess.set(true);
                    if(isSuccess.get()){
                        getDialog().dismiss();}
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"由于未知原因，添加失败了"+e.getDescription(),Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }).start();

            }


}
