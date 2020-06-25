package com.munchkin.musclediary.src.signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.MainActivity;
import com.munchkin.musclediary.src.signin.interfaces.SignInActivityView;
import com.munchkin.musclediary.src.signin.models.SignInResponse;
import com.munchkin.musclediary.src.signin.services.SignInService;

import static com.munchkin.musclediary.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.munchkin.musclediary.src.ApplicationClass.sSharedPreferences;

public class SignInActivity extends BaseActivity implements SignInActivityView {

    //log in button
    Button mSignInButton;
    //Sign up button
    TextView mSignUpText;

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle params = new Bundle();
        params.putString("req", "sign_in_Android");
        mFirebaseAnalytics.logEvent("sign_in_Android", params);

        //자동로그인 기능
        if(sSharedPreferences.getBoolean("isSignIn", false)){
            X_ACCESS_TOKEN = sSharedPreferences.getString("x-access-token","X_ACCESS_TOKEN");
            Log.d("Debug",X_ACCESS_TOKEN);
            Intent signInIntent = new Intent(this, MainActivity.class);
            startActivity(signInIntent);
            finish();
        }



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

                Bundle params = new Bundle();
                params.putString("req", "press_sign_in_button_Android");
                mFirebaseAnalytics.logEvent("press_sign_in_button_Android", params);

                //editText 생성
                EditText etId = findViewById(R.id.login_id);
                EditText etPassword = findViewById(R.id.login_password);

                //id password저장
                String id = etId.getText().toString();
                String password = etPassword.getText().toString();

                //api 연결 시도
                tryPostSignIn(id, password);

                /*
                startActivity(signInIntent);
                finish();

                 */

            }
        });

        mSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putString("req", "press_sign_up_button_Android");
                mFirebaseAnalytics.logEvent("press_sign_up_button_Android", params);

                startActivity(signUpIntent);
            }
        });
    }

    //로그인 api호출 시작
    private void tryPostSignIn(String id, String password){
        showProgressDialog();
        final SignInService signInService = new SignInService(this);
        signInService.postSignIn(id, password);
    }

    //성공 시 메인으로 넘어가고 자동로그인 상태 저장
    @Override
    public void SignInSuccess(int code, String message, SignInResponse.Jwt jwt) {
        if(code == 101){
            SharedPreferences.Editor editor = sSharedPreferences.edit();
            X_ACCESS_TOKEN = jwt.getJwt();
            editor.putString("x-access-token",jwt.getJwt());
            editor.putBoolean("isSignIn",true);
            editor.apply();

            Bundle params = new Bundle();
            params.putString("req", "auto_sign_in_Android");
            mFirebaseAnalytics.logEvent("auto_sign_in_Android", params);

            Intent signInIntent = new Intent(this, MainActivity.class);
            startActivity(signInIntent);
            finish();
        } else {
            showCustomToast(message);
        }
        hideProgressDialog();
    }

    @Override
    public void validateFailure(String message) {
        Log.d("test", "failuer");
        showCustomToast(message);
        hideProgressDialog();
    }

    //로그인 화면에서는 안씀
    @Override
    public void SignUpSuccess(int code, String message) { }
}
