package com.example.myapplication;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import com.example.myapplication.clients.WebViewClientLogin;

import com.example.myapplication.serviceclasses.Constants;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_login);

        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        final WebView webView = findViewById(R.id.web_view_for_login);
        final WebViewClientLogin webViewLogin = new WebViewClientLogin();
        webView.setWebViewClient(webViewLogin);
        webView.loadUrl(Constants.AUTHORIZATION_URL);
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
