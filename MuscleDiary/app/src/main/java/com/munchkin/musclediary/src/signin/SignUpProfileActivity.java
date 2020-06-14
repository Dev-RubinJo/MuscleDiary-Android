package com.munchkin.musclediary.src.signin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.MainActivity;
import com.munchkin.musclediary.src.main.setting.dialog.AgeActivity;
import com.munchkin.musclediary.src.main.setting.dialog.GenderActivity;
import com.munchkin.musclediary.src.main.setting.dialog.HeightActivity;
import com.munchkin.musclediary.src.main.setting.dialog.WeightActivity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SignUpProfileActivity extends BaseActivity {
    //입력창으로 이어지는 버튼 설정
    private TextView mTvGender;
    private TextView mTvBirth;
    private TextView mTvHeight;
    private TextView mTvWeight;

    //뒤로가기 버튼 & 완료버튼
    private ImageButton mBtnBack;
    private Button mBtnComplete;

    //회원가입을 위한 변수
    private String mEmail;
    private String mPassword;

    private int mGender;
    private Date mBirth;
    private double mHeight;
    private double mWeight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_profile);

        Intent getIntent = getIntent();
        mEmail = getIntent.getStringExtra("email");
        mPassword = getIntent.getStringExtra("password");

        //버튼 연결
        mTvGender = findViewById(R.id.sign_up_profile_btn_input_gender);
        mTvBirth = findViewById(R.id.sign_up_profile_btn_input_birth);
        mTvHeight = findViewById(R.id.sign_up_profile_btn_input_height);
        mTvWeight = findViewById(R.id.sign_up_profile_btn_input_weight);

        mBtnBack = findViewById(R.id.sign_up_profile_btn_back);
        mBtnComplete = findViewById(R.id.sign_up_profile_btn_complete);

        initListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode){
            case 1000:
                mGender = data.getIntExtra("gender",1);
                mTvGender.setText(mGender == 1 ? "남성" : "여성");
                break;

            case 2000:
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //java.util.Date utilDate = dateFormat.format(data.getStringExtra("age"));
                //NOTE 임시로 birth 스트링으로 저장
                String birth = data.getStringExtra("age");
                mTvBirth.setText(birth);
                break;

            case 3000:
                mHeight = data.getDoubleExtra("height",0.0);
                mTvHeight.setText(mHeight+" cm");
                break;

            case 4000:
                mWeight = data.getDoubleExtra("weight",0.0);
                mTvWeight.setText(mWeight+" kg");
        }

    }

    //리스너 초기화 함수
    private void initListener() {
        mTvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setGenderIntent = new Intent(getApplicationContext(), GenderActivity.class);
                startActivityForResult(setGenderIntent,1000);
            }
        });

        mTvBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setBirthIntent = new Intent(getApplicationContext(), AgeActivity.class);
                startActivityForResult(setBirthIntent,2000);
            }
        });

        mTvHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setHeightIntent = new Intent(getApplicationContext(), HeightActivity.class);
                startActivityForResult(setHeightIntent,3000);
            }
        });

        mTvWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setWeightIntent = new Intent(getApplicationContext(), WeightActivity.class);
                startActivityForResult(setWeightIntent,4000);
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 로그인 완료되면 바로 로그인 오류나면 오류 메세지로 연결
                Intent doneIntent = new Intent();
                doneIntent.putExtra("done",true);
                setResult(RESULT_OK,doneIntent);

                Intent gotoMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(gotoMain);
                finish();
            }
        });
    }
}
