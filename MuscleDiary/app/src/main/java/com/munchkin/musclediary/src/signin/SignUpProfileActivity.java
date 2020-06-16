package com.munchkin.musclediary.src.signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.MainActivity;
import com.munchkin.musclediary.src.main.setting.dialog.AgeActivity;
import com.munchkin.musclediary.src.main.setting.dialog.GenderActivity;
import com.munchkin.musclediary.src.main.setting.dialog.HeightActivity;
import com.munchkin.musclediary.src.main.setting.dialog.WeightActivity;
import com.munchkin.musclediary.src.main.setting.interfaces.SettingFragmentView;
import com.munchkin.musclediary.src.main.setting.models.GetGoalWeightResponse;
import com.munchkin.musclediary.src.main.setting.models.GetNutritionResponse;
import com.munchkin.musclediary.src.main.setting.models.ProfileResult;
import com.munchkin.musclediary.src.main.setting.services.SettingService;
import com.munchkin.musclediary.src.signin.interfaces.SignInActivityView;
import com.munchkin.musclediary.src.signin.models.SignInResponse;
import com.munchkin.musclediary.src.signin.services.SignInService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.munchkin.musclediary.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.munchkin.musclediary.src.ApplicationClass.sSharedPreferences;

public class SignUpProfileActivity extends BaseActivity implements SignInActivityView, SettingFragmentView {
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

    private int mGender= -1;
    private String mBirth;
    private double mHeight= -1.0;
    private double mWeight= -1.0;

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
                mGender = data.getIntExtra("gender",-1);
                mTvGender.setText(mGender == 1 ? "남성" : "여성");
                break;

            case 2000:
                mBirth = data.getStringExtra("age");
                mTvBirth.setText(mBirth);
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
                if(allEnteredcheck()){
                    Intent doneIntent = new Intent();
                    doneIntent.putExtra("done",true);
                    setResult(RESULT_OK,doneIntent);
                    tryPostSignUp(mEmail,mPassword,mPassword);
                }else{
                    Toast.makeText(getApplicationContext(),"모든 정보를 기입해 주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private boolean allEnteredcheck() {
        if(mGender!=-1 && !mBirth.isEmpty() && mHeight!=-1 && mWeight!=-1){
            return true;
        }else{
            return false;
        }
    }

    //로그인 api호출 시작
    private void tryPostSignIn(String id, String password){
        final SignInService signInService = new SignInService(this);
        signInService.postSignIn(id, password);
    }

    //회원가입 api호출 시작
    private void tryPostSignUp(String id, String password, String rePassword){
        showProgressDialog();
        final SignInService signInService = new SignInService(this);
        signInService.postSignUp(id, password,rePassword);
    }

    //기본정보 수정 api호출 시작
    private void tryPostUpdateProfile(Double height, Double weight, int gender, String birth){
        SettingService settingService = new SettingService(this);
        settingService.postUpdateProfile(height, weight, gender, birth);
    }

    //로그인 성공시
    @Override
    public void SignInSuccess(int code, String message, SignInResponse.Jwt jwt) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        X_ACCESS_TOKEN = jwt.getJwt();
        editor.putString("x-access-token",jwt.getJwt());
        editor.putBoolean("isSignIn",true);
        editor.apply();
        tryPostUpdateProfile(mHeight,mWeight,mGender,mBirth);
    }

    //회원가입 성공시
    @Override
    public void SignUpSuccess(int code, String message) {
        //로그인 시작
        tryPostSignIn(mEmail,mPassword);
    }

    //프로파일 수정 성공시
    @Override
    public void updateProfileSuccess(int code, String message) {
        Intent gotoMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(gotoMain);
        hideProgressDialog();
        finish();
    }

    //NOTE implements 받아왔지만 실질적으로 사용하지 않는 부분
    @Override
    public void profileSuccess(int code, String message, ArrayList<ProfileResult> profileResult) { }
    @Override
    public void postNutritionSuccess(int code, String message) { }
    @Override
    public void getNutritionSuccess(int code, String message, GetNutritionResponse.NutritionResult result) { }

    @Override
    public void getGoalWeightSuccess(int code, String message, GetGoalWeightResponse.Result result) { }

    @Override
    public void postGoalWeightSuccess(int code, String message) { }

    @Override
    public void validateFailure(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        return;
    }
}
