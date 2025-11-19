package com.vaibhavi.message.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vaibhavi.message.R;

public class LoginRegisterActivity extends AppCompatActivity {
    Button nextButton;
    EditText phoneEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_register);
        phoneEdit = findViewById(R.id.phoneEdit);
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneEdit.getText().toString().trim().length() ==10)
                {
                    Intent intent = new Intent(LoginRegisterActivity.this, DashboardActivity.class);
                    // If you use Firebase Auth you can choose based on auth state:
                    // FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                    // Intent intent = u == null ? new Intent(...SignInActivity) : new Intent(...UsersActivity);

                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }else {
                    Toast.makeText(LoginRegisterActivity.this, "Please Check Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}