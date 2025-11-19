package com.vaibhavi.message.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vaibhavi.message.R;
import com.vaibhavi.message.activities.ChatActivity;
import com.vaibhavi.message.activities.IntroActivity;
import com.vaibhavi.message.activities.LoginRegisterActivity;
import com.vaibhavi.message.model.ChatsModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<ChatsModel> mData;

    public RecyclerViewAdapter(Context context, List<ChatsModel> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatsModel item = mData.get(position);
        holder.chatName.setText(item.getName());
        holder.chatMessage.setText(item.getMessage());
        holder.chatTime.setText(item.getTime());
        holder.chatMessageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                // If you use Firebase Auth you can choose based on auth state:
                // FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                // Intent intent = u == null ? new Intent(...SignInActivity) : new Intent(...UsersActivity);

                mContext.startActivity(intent);
            }
        });

        // load image with Glide (better than setImageResource for many items)
        Glide.with(mContext)
                .load(item.getPhoto()) // resource id or URL
                .placeholder(R.drawable.gates)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        final de.hdodenhof.circleimageview.CircleImageView image;
        final android.widget.TextView chatName;
        final android.widget.TextView chatMessage;
        final android.widget.TextView chatTime;
        RelativeLayout chatMessageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.chat_img);
            chatName = itemView.findViewById(R.id.chatName);
            chatMessage = itemView.findViewById(R.id.chatMessage);
            chatTime = itemView.findViewById(R.id.timestamp);
            chatMessageView = itemView.findViewById(R.id.chatMessageView);
        }
    }
}
