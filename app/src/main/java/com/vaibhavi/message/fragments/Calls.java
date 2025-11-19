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
import com.vaibhavi.message.adapters.CallsAdapter;
import com.vaibhavi.message.adapters.PersistenceHelper;
import com.vaibhavi.message.model.CallModel;

import java.util.ArrayList;
import java.util.List;


public class Calls extends Fragment {

    private RecyclerView recyclerView;
    private CallsAdapter adapter;
    private List<CallModel> list;
    private PersistenceHelper persistence;

    public Calls() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calls, container, false);
        recyclerView = v.findViewById(R.id.calls_recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        persistence = new PersistenceHelper(requireContext());
        list = new ArrayList<>(persistence.loadCallList());

        // pre-populate if empty
        if (list.isEmpty()) {
            list.add(new CallModel("Vaibhavi More", "Today, 10:00 AM", "outgoing", null));
            list.add(new CallModel("Tanvi More", "Yesterday", "outgoing", null));
            list.add(new CallModel("Tanvi More", "Yesterday", "missed", null));
            list.add(new CallModel("Omkar More", "Yesterday", "incoming", null));
            persistence.saveCallList(list);
        }

        adapter = new CallsAdapter(requireContext(), list, persistence);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
