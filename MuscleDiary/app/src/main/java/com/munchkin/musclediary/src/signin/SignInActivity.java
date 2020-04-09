package com.munchkin.musclediary.src.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.MainActivity;

public class SignInActivity extends BaseActivity {

    //log in button
    Button mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        // set UI Component
        mSignInButton = findViewById(R.id.login_btn_login);

        // set listener method
        this.initOnClickListener();
    }

    private void initOnClickListener() {
        final Intent signInIntent = new Intent(this, MainActivity.class);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signInIntent);
                finish();
            }
        });
    }
}
