package com.munchkin.musclediary.src.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class SignUpActivity extends BaseActivity {
    //complete button
    Button mBtnComplete;

    //back button
    ImageButton mBtnBack;

    //입력창
    private EditText mEtEmail;
    private EditText mEtPassword;
    private EditText mEtPasswordCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // set UI Component
        mBtnComplete = findViewById(R.id.sign_up_btn_complete);
        mBtnBack = findViewById(R.id.sign_up_btn_back);

        mEtEmail = findViewById(R.id.sign_in_et_id);
        mEtPassword = findViewById(R.id.sign_in_et_pw);
        mEtPasswordCheck = findViewById(R.id.sign_in_et_pw_check);

        // set listener method
        this.initOnClickListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=RESULT_OK) {
            return;
        }

        switch (requestCode){
            case 777:
                if (data.getBooleanExtra("done",false)){
                    finish();
                }
                break;
        }
    }

    private void initOnClickListener() {
        final Intent signInIntent = new Intent(this, SignUpProfileActivity.class);

        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean idCheck, pwCheck;
                String id = mEtEmail.getText().toString();

                if(id.isEmpty()){
                    idCheck = false;
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else{
                    idCheck = true;
                }

                String password = mEtPassword.getText().toString();
                String passwordCheck = mEtPasswordCheck.getText().toString();

                if(!password.equals(passwordCheck)){
                    pwCheck = false;
                    Toast.makeText(getApplicationContext(),"입력하신 두 비밀번호가 다릅니다",Toast.LENGTH_SHORT).show();
                }else if(password.length()<7 || password.length() > 14){
                    pwCheck = false;
                    Toast.makeText(getApplicationContext(),"비밀번호는 8자리 이상 14자리 이하의\n영문, 숫자 조합이어야 합니다",Toast.LENGTH_SHORT).show();
                }else{
                    pwCheck = true;
                }

                if(idCheck&&pwCheck&&!password.isEmpty()){
                    signInIntent.putExtra("email",id);
                    signInIntent.putExtra("password",password);

                    startActivityForResult(signInIntent,777);
                }else{
                    Toast.makeText(getApplicationContext(),"빈칸없이 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
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
