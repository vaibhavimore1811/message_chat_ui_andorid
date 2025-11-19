package com.vaibhavi.message.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vaibhavi.message.R;
import com.vaibhavi.message.model.CallModel;

import java.util.List;

public class CallsAdapter extends RecyclerView.Adapter<CallsAdapter.VH> {

    private final Context ctx;
    private final List<CallModel> data;
    private final PersistenceHelper persistence;

    public CallsAdapter(Context ctx, List<CallModel> data, PersistenceHelper p) {
        this.ctx = ctx;
        this.data = data;
        this.persistence = p;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.call_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CallModel item = data.get(position);
        holder.name.setText(item.getName());
        holder.time.setText(item.getTime());
        holder.type.setText(item.getType());

        if (item.getAvatarUrl() != null) {
            Glide.with(ctx).load(item.getAvatarUrl()).into(holder.avatar);
        } else {
            holder.avatar.setImageResource(R.drawable.ic_call);
        }

        // long press to delete call entry
        holder.itemView.setOnLongClickListener(v -> {
            data.remove(position);
            notifyItemRemoved(position);
            persistence.saveCallList(data);
            return true;
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView avatar;
        android.widget.TextView name, time, type;
        public VH(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.call_avatar);
            name = itemView.findViewById(R.id.call_name);
            time = itemView.findViewById(R.id.call_time);
            type = itemView.findViewById(R.id.call_type);
        }
    }
}
