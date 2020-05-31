package com.munchkin.musclediary.src.main.food;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.munchkin.musclediary.R;
import android.webkit.WebSettings;
import android.widget.Toast;
import android.util.Log;
import android.view.KeyEvent;

import com.munchkin.musclediary.src.BaseActivity;

public class WebProteinActivity extends BaseActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;
    private long lastTimeBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_protein);

        mWebView = (WebView)findViewById(R.id.webview_protein);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        //홈페이지 링크
        mWebView.loadUrl("https://www.gobsn.com/en-us/product/syntha6");


    }
    @Override
    public void onBackPressed() {


        if (mWebView.canGoBack()) {
            mWebView.goBack();

        } else {
            if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
                finish();
                return;
            }
            lastTimeBackPressed = System.currentTimeMillis();
            Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }



}