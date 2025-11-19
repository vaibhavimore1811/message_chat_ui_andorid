package com.vaibhavi.message.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vaibhavi.message.R;
import com.vaibhavi.message.model.StatusModel;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.VH> {

    private final Context ctx;
    private final List<StatusModel> data;
    private final PersistenceHelper persistence;

    public StatusAdapter(Context ctx, List<StatusModel> data, PersistenceHelper p) {
        this.ctx = ctx;
        this.data = data;
        this.persistence = p;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.status_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        StatusModel s = data.get(position);
        holder.name.setText(s.getName());
        holder.time.setText(s.getTime());

        if (s.getImageUrl() != null) {
            Glide.with(ctx).load(s.getImageUrl()).into(holder.avatar);
        } else {
            holder.avatar.setImageResource(R.mipmap.ic_launcher_round);
        }

        // long-press to remove status example
        holder.itemView.setOnLongClickListener(v -> {
            data.remove(position);
            notifyItemRemoved(position);
            persistence.saveStatusList(data);
            return true;
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView avatar;
        android.widget.TextView name, time;
        public VH(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.status_avatar);
            name = itemView.findViewById(R.id.status_name);
            time = itemView.findViewById(R.id.status_time);
        }
    }
}

