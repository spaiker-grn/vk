package com.example.myapplication.clients;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.serviceclasses.MyApplication;
import com.example.myapplication.tools.URLParser;

public class WebViewClientLogin extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(final WebView pView, final String pUrl) {
        if (pUrl.contains(Constants.URL_BUILDER.HTTPS_OAUTH_VK)) {
            final URLParser parser = new URLParser();
            saveToken(parser.parse(pUrl));
            Log.d(Constants.MY_TAG, loadToken());
        }
        return false;
    }
    private static void saveToken(final String pToken) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.ACCESS_TOKEN, pToken);
        editor.apply();
    }

    public static String loadToken() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getString(Constants.ACCESS_TOKEN, null);
    }
}
