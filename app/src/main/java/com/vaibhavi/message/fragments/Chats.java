package com.vaibhavi.message.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vaibhavi.message.R;
import com.vaibhavi.message.adapters.RecyclerViewAdapter;
import com.vaibhavi.message.model.ChatsModel;

import java.util.ArrayList;
import java.util.List;

public class Chats extends Fragment {

    private RecyclerView mRecyclerView;
    private List<ChatsModel> listChat;

    public Chats() { /* required empty constructor */ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        // prepare data BEFORE creating adapter
        if (listChat == null) listChat = createSampleChats();

        mRecyclerView = view.findViewById(R.id.chats_recView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(requireContext(), listChat);
        mRecyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    // populate list (you can replace with real data later)
    private List<ChatsModel> createSampleChats() {
        List<ChatsModel> items = new ArrayList<>();
        items.add(new ChatsModel("Tanvi More", "Introducing IGTV on Instagram!", R.drawable.zuckerberg, "9:11 PM"));
        items.add(new ChatsModel("Vaibhavi More", "Tried Linux and I loved it :)", R.drawable.gates, "8:47 PM"));
        items.add(new ChatsModel("Omkar More", "Adding ML into everything!", R.drawable.pichai, "7:07 PM"));
        items.add(new ChatsModel("Vaishnavi More", "Are you up for a chat?", R.drawable.musk, "6:33 PM"));
     // add duplicates or more items as needed
        return items;
    }
}
