package com.vaibhavi.message.adapters;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity; // or Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vaibhavi.message.fragments.Calls;
import com.vaibhavi.message.fragments.Chats;
import com.vaibhavi.message.fragments.Status;

public class SectionsAdapter extends FragmentStateAdapter {

    public SectionsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    // If you instantiate with Fragment (not FragmentActivity), use:
    // public SectionsAdapter(@NonNull Fragment fragment) { super(fragment); }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Chats();
            case 1: return new Status();
            case 2: return new Calls();
            default: return new Chats();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
