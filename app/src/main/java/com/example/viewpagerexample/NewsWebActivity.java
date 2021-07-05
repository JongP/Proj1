package com.example.viewpagerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsWebActivity extends AppCompatActivity {

    private WebView webView;
    private String url_1 = "https://www.google.com/search?q=", url_2 = "&tbm=nws";
    private String url = url_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        url = url_1+name+url_2;

        webView= findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)&&webView.canGoBack()){
            webView.goBack();
            return super.onKeyDown(keyCode, event);  //check
        }

        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}