package com.coffee_just.chatapp.Chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coffee_just.chatapp.Chat.ChatActivity;
import com.coffee_just.chatapp.R;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private Context mContext;

    public RecycleViewAdapter(Context context, List<String> list) {
        if (list != null)
            mContactLab = list;
        else {
            list = new ArrayList<>();
        }
        mContext = context;
    }

    private List<String> mContactLab;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_contactname.setText(mContactLab.get(position));
        holder.layoutGotoChat.setOnClickListener(l -> {
//                    Intent i  = new Intent()
            // todo  这里需要跳转，并且通信instance
            Intent intent = ChatActivity.instance(mContext, mContactLab.get(position));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return mContactLab == null ? 0 : mContactLab.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_contactname;
        private LinearLayout layoutGotoChat;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_contactname = itemView.findViewById(R.id.tv_contactName);
            layoutGotoChat = itemView.findViewById(R.id.layout_goto_chat);
        }
    }
}
