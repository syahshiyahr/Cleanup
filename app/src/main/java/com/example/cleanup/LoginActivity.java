package com.example.cleanup;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cleanup.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        LoginFragment mLoginFragment = new LoginFragment();
        Fragment fragment = mFragmentManager.findFragmentByTag(LoginFragment.class.getSimpleName());

        if (!(fragment instanceof LoginFragment)) {
            mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, mLoginFragment, LoginFragment.class.getSimpleName())
                    .commit();
        }
    }
}