package com.munchkin.musclediary.src.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class SignUpActivity extends BaseActivity {
    //complete button
    Button mBtnComplete;

    //back button
    ImageButton mBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // set UI Component
        mBtnComplete = findViewById(R.id.sign_up_btn_complete);
        mBtnBack = findViewById(R.id.sign_up_btn_back);

        // set listener method
        this.initOnClickListener();
    }

    private void initOnClickListener() {
        final Intent signInIntent = new Intent(this, SignInActivity.class);

        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"임시 : 로그인 해주세요",Toast.LENGTH_LONG).show();
                startActivity(signInIntent);
                finish();
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
