package com.vaibhavi.message.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.vaibhavi.message.R;
import com.vaibhavi.message.adapters.SectionsAdapter;
import com.vaibhavi.message.fragments.Calls;
import com.vaibhavi.message.fragments.Chats;
import com.vaibhavi.message.fragments.Status;

public class DashboardActivity extends AppCompatActivity {

    private ViewPager2 mViewPager;
    private SectionsAdapter sectionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("VB Connect");
        hideSystemBars();

        // ViewPager2 + adapter
        mViewPager = findViewById(R.id.container);
        sectionsAdapter = new SectionsAdapter(this);
        mViewPager.setAdapter(sectionsAdapter);

        // TabLayout
        TabLayout tabLayout = findViewById(R.id.tabs);

        // Connect TabLayout with ViewPager2 using TabLayoutMediator
        new TabLayoutMediator(tabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: tab.setText("CHATS"); break;
                    case 1: tab.setText("STATUS"); break;
                    case 2: tab.setText("CALLS"); break;
                }
            }
        }).attach();

        // FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "New Chat Clicked", Snackbar.LENGTH_LONG)
                .setAction("OK", null).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); // ensure this exists
        return true;
    }
    private void hideSystemBars() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Snackbar.make(findViewById(android.R.id.content), "Settings clicked", Snackbar.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
