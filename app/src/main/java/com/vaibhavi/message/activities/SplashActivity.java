package com.vaibhavi.message.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.vaibhavi.message.R;

public class SplashActivity extends AppCompatActivity {
    // Splash duration (ms)
    private static final long SPLASH_DURATION = 1500L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Use the splash theme to avoid window white flash
        setTheme(R.style.SplashTheme);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        ImageView imgLogo = findViewById(R.id.imgLogo);

        // Fade-in animation for the logo
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imgLogo.startAnimation(fadeIn);

        // After delay, fade out and start main activity
        new Handler().postDelayed(() -> {
            Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            imgLogo.startAnimation(fadeOut);

            // Wait for fade-out to finish then start main
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {}
                @Override public void onAnimationRepeat(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    // Decide where to go next (SignIn, Users, Main, etc.)
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    // If you use Firebase Auth you can choose based on auth state:
                    // FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                    // Intent intent = u == null ? new Intent(...SignInActivity) : new Intent(...UsersActivity);

                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            });
        }, SPLASH_DURATION);
    }
}