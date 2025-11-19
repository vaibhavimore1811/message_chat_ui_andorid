package com.vaibhavi.message.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vaibhavi.message.R;

public class IntroActivity extends AppCompatActivity {
   Button agreeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        agreeButton = findViewById(R.id.agreeButton);
        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, LoginRegisterActivity.class);
                // If you use Firebase Auth you can choose based on auth state:
                // FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                // Intent intent = u == null ? new Intent(...SignInActivity) : new Intent(...UsersActivity);

                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

    }
}