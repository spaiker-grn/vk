package com.spaikergrn.vkclient.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.clients.WebViewClientLogin;
import com.spaikergrn.vkclient.serviceclasses.Constants;

public class LoginActivity extends AppCompatActivity {

    public static void start(final Context pContext){
        final Intent intent = new Intent(pContext, LoginActivity.class);
        intent.putExtra(Constants.Parser.URL, Constants.HTTPS_VK_COM);
        pContext.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_login);
        final Intent intent = getIntent();
        initWebView(intent.getStringExtra(Constants.Parser.URL));
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(final String pStringExtra) {
        final WebView webView = findViewById(R.id.web_view_for_login);
        final WebViewClientLogin webViewLogin = new WebViewClientLogin();
        webView.setWebViewClient(webViewLogin);
        if (pStringExtra != null){
            webView.loadUrl(pStringExtra);
        }else {
            webView.loadUrl(Constants.AUTHORIZATION_URL);
        }
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
