package com.spaikergrn.vk_client.clients;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.serviceclasses.ContextHolder;
import com.spaikergrn.vk_client.tools.URLParser;

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
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextHolder.getContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.ACCESS_TOKEN, pToken);
        editor.apply();
    }

    public static String loadToken() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextHolder.getContext());
        return preferences.getString(Constants.ACCESS_TOKEN, null);
    }

    public static void deleteToken() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextHolder.getContext());
        preferences.edit().remove(Constants.ACCESS_TOKEN).apply();
    }
}
