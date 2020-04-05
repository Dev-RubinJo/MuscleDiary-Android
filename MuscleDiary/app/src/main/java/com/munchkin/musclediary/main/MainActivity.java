package com.munchkin.musclediary.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.signin.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent tmpIntent = new Intent(this, LoginActivity.class);
        startActivity(tmpIntent);

        finish();
    }
}
