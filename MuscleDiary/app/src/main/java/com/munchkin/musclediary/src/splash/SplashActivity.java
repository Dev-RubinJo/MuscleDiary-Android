package com.munchkin.musclediary.src.splash;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.munchkin.musclediary.R;

import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.signin.SignInActivity;

public class SplashActivity extends BaseActivity {

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle params = new Bundle();
        params.putString("req", "splash_Android");
        mFirebaseAnalytics.logEvent("splash_Android", params);

        Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
        startActivity(intent);

        finish();
    }
}
