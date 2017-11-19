package com.example.myapplication;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.myapplication.serviceclasses.Constants;

/**
 * Created by Дмитрий on 04.11.2017.
 */

public class ActivityForLogin extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_login);
        initialization();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initialization() {
        final WebView webView = findViewById(R.id.web_view_for_login);
        webView.loadUrl(Constants.URL);
        webView.getSettings().setJavaScriptEnabled(true);
        final com.example.myapplication.clients.WebViewClientLogin webViewLogin = new com.example.myapplication.clients.WebViewClientLogin();
        webView.setWebViewClient(webViewLogin);

    }
}
