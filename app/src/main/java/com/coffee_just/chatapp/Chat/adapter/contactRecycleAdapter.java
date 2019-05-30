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
import com.coffee_just.chatapp.Untils.L;
import com.coffee_just.chatapp.bean.contactInfo;
import com.coffee_just.chatapp.impl.OnremoveListnner;

import java.util.ArrayList;


public class contactRecycleAdapter extends RecyclerView.Adapter<contactRecycleAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<contactInfo> mContactLab;
    private OnremoveListnner mOnremoveListnner;
    public contactRecycleAdapter(Context context, ArrayList<contactInfo> list) {
        if (list != null)
            mContactLab = list;
        else {
            mContactLab = new ArrayList<>();
        }
        mContext = context;
    }


    public void serOnremoveContactListnner(OnremoveListnner onremoveListnner)
    {
        this.mOnremoveListnner = onremoveListnner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_contactname.setText(mContactLab.get(position).getUserName());
        holder.layoutGotoChat.setOnClickListener(l -> {
//                    Intent i  = new Intent()
            // todo  这里需要跳转，并且通信instance
            Intent intent = ChatActivity.instance(mContext, mContactLab.get(position).getUserName());
            mContext.startActivity(intent);
        });
        holder.layoutGotoChat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnremoveListnner!=null)
                {
                    mOnremoveListnner.delectContact(position);
                    L.d("已经得到了删除");
                }
                return true;
            }
        });
    }



    @Override
    public int getItemCount() {

        return mContactLab == null ? 0 : mContactLab.size();
    }
    public void addData(contactInfo contactInfo) {
        mContactLab.add(contactInfo);
        notifyDataSetChanged();
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
