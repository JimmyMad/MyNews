package com.sweethome.jimmy.mynews.Controllers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.sweethome.jimmy.mynews.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        WebView myWebView = new WebView(this);
        setContentView(myWebView);
        myWebView.loadUrl(url);
    }
}
