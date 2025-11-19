package com.vaibhavi.message.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vaibhavi.message.R;
import com.vaibhavi.message.model.Message;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SENT = 1;
    private static final int TYPE_RECEIVED = 2;

    private List<Message> data;
    private Context ctx;

    public MessageAdapter(Context ctx, List<Message> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        Message m = data.get(position);
        if ("sent".equals(m.getType()) || "audio_sent".equals(m.getType())) return TYPE_SENT;
        return TYPE_RECEIVED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SENT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new RecvHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message m = data.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        if (holder instanceof SentHolder) {
            ((SentHolder) holder).txtMsg.setText(m.getText());
            ((SentHolder) holder).txtTime.setText(sdf.format(m.getTimestamp()));
        } else {
            ((RecvHolder) holder).txtMsg.setText(m.getText());
            ((RecvHolder) holder).txtTime.setText(sdf.format(m.getTimestamp()));
        }
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class SentHolder extends RecyclerView.ViewHolder {
        TextView txtMsg, txtTime;
        SentHolder(View v) {
            super(v);
            txtMsg = v.findViewById(R.id.txtMsgSent);
            txtTime = v.findViewById(R.id.txtTimeSent);
        }
    }

    static class RecvHolder extends RecyclerView.ViewHolder {
        TextView txtMsg, txtTime;
        RecvHolder(View v) {
            super(v);
            txtMsg = v.findViewById(R.id.txtMsgRecv);
            txtTime = v.findViewById(R.id.txtTimeRecv);
        }
    }
}
