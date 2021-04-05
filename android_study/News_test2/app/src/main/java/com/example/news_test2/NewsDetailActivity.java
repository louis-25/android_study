package com.example.news_test2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class NewsDetailActivity extends Activity {

    private NewsData news;
    private TextView TextView_title, TextView_content;
    private WebView mWebView; // 웹뷰 선언
    private WebSettings mWebSettings; // 웹뷰세팅

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        setComp();
        getNewsDetail();
        setNews();
    }

    //기본 컴포넌트 셋팅
    @SuppressLint("SetJavaScriptEnabled")
    public void setComp() {
        TextView_title = findViewById(R.id.TextView_title);
        TextView_content = findViewById(R.id.TextView_content);
        mWebView = findViewById(R.id.webView);
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true); //웹페이지 자바스크립트 허용

    }

    //이전 액티비티에서 받아오는 인텐트
    public void getNewsDetail() {
        Intent intent = getIntent();
        if(intent != null) {
            Bundle bld = intent.getExtras();

            Object obj = bld.get("news");
            if(obj != null && obj instanceof NewsData) {
                this.news = (NewsData) obj;
            }
        }
    }

    //이전 액티비티에서 받아오는 인텐트에서 정보를 확인하여 뉴스표시
    public void setNews() {
        if(this.news != null) {
            String title = this.news.getUrl();
            if(title != null) {
                //TextView_title.setText(title);

            }
            if(mWebView != null) {
                mWebView.loadUrl(this.news.getUrl());
            }
            String content = this.news.getContent();
            if(content != null) {
                //전체 본문은 url 값의 실제 뉴스 사이트에 있으며, 해당 전체 본문을 불러오기 위해서는 스크래핑 (크롤링) 기술로 읽어와야 합니다.
                //TextView_content.setText(content);
            }

        }
    }
}
