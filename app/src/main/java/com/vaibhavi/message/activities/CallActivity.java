package com.vaibhavi.message.activities;



import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.vaibhavi.message.R;

public class CallActivity extends AppCompatActivity {
    private ImageView btnEnd;
    private TextView txtName, txtTimer;
    private Handler handler = new Handler();
    private long seconds = 0;
    private Runnable tick = new Runnable() {
        @Override
        public void run() {
            seconds++;
            long m = seconds / 60;
            long s = seconds % 60;
            txtTimer.setText(String.format("%02d:%02d", m, s));
            handler.postDelayed(this,1000);
        }
    };
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
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_call);
        txtName = findViewById(R.id.txtName);
        txtTimer = findViewById(R.id.txtTimer);
        btnEnd = findViewById(R.id.btnEndCall);
        hideSystemBars();

        txtName.setText("Vaibhavi More");
        handler.post(tick);

        btnEnd.setOnClickListener(v -> {
            handler.removeCallbacks(tick);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(tick);
        super.onDestroy();
    }
}
