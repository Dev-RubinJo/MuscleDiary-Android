package com.munchkin.musclediary.src.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.MainActivity;

public class SignInActivity extends BaseActivity {

    //log in button
    Button mSignInButton;
    //Sign up button
    TextView mSignUpText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // set UI Component
        mSignInButton = findViewById(R.id.login_btn_login);
        mSignUpText = findViewById(R.id.login_tv_sign_up);

        // set listener method
        this.initOnClickListener();
    }

    private void initOnClickListener() {
        final Intent signInIntent = new Intent(this, MainActivity.class);
        final Intent signUpIntent = new Intent(this, SignUpActivity.class);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signInIntent);
                finish();
            }
        });

        mSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUpIntent);
            }
        });
    }
}
