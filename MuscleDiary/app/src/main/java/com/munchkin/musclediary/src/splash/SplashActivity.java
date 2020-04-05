package com.munchkin.musclediary.src.splash;

import android.content.Intent;
import android.os.Bundle;

import com.munchkin.musclediary.R;

import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.signin.SignInActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
        startActivity(intent);

        finish();
    }
}
