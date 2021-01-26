package com.example.cleanup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        checkUser();
    }

    private void checkUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (user != null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },200);
    }
}