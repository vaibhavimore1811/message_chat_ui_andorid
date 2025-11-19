package com.vaibhavi.message.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vaibhavi.message.R;
import com.vaibhavi.message.adapters.PersistenceHelper;
import com.vaibhavi.message.adapters.StatusAdapter;
import com.vaibhavi.message.model.StatusModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Status extends Fragment {

    private RecyclerView recyclerView;
    private StatusAdapter adapter;
    private List<StatusModel> list;
    private PersistenceHelper persistence;

    public Status() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status, container, false);
        recyclerView = v.findViewById(R.id.status_recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        persistence = new PersistenceHelper(requireContext());
        list = new ArrayList<>(persistence.loadStatusList()); // load saved or empty

        // if empty, pre-populate with example statuses (including your uploaded image)
        if (list.isEmpty()) {
            list.add(new StatusModel("You", "Today, 9:00 AM", "file:///mnt/data/8db6eb17-5e3a-4040-98e4-55c06fa4cd99.png"));
            list.add(new StatusModel("Vaibhavi More", "Yesterday", null));
            list.add(new StatusModel("Tanvi More", "Today, 2:00 PM", null));
            list.add(new StatusModel("Omkar More", "Today, 7:00 AM", null));
            list.add(new StatusModel("Vaishnavi More", "Yesterday", null));
            persistence.saveStatusList(list);
        }

        adapter = new StatusAdapter(requireContext(), list, persistence);
        recyclerView.setAdapter(adapter);
        return v;
    }

}