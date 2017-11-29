package com.example.myapplication.vkapi;

import android.net.Uri;

import com.example.myapplication.clients.WebViewClientLogin;
import com.example.myapplication.serviceclasses.Constants;

import java.util.Map;

public class VkApiBuilder implements IVkApiBuilder {

    private final String mToken;

    public VkApiBuilder() {

        mToken = WebViewClientLogin.loadToken();

    }

    public String buildUrl(final String pMethod) {

        final Uri.Builder builder = build(pMethod);
        return builder.build().toString();

    }

    public String buildUrl(final String pMethod, final Map<String, String> pParameters) {

        final Uri.Builder builder = build(pMethod);
        for (final Map.Entry<String, String> entry : pParameters.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build().toString();

    }

    private Uri.Builder build(final String pMethod) {
        final Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constants.URL_BUILDER.HTTPS);
        builder.authority(Constants.URL_BUILDER.API_VK_COM);
        builder.appendPath(Constants.URL_BUILDER.METHOD);
        builder.appendPath(pMethod);
        builder.appendQueryParameter(Constants.URL_BUILDER.VERSION, Constants.URL_BUILDER.VERSION_VALUE);
        builder.appendQueryParameter(Constants.URL_BUILDER.ACCESS_TOKEN, mToken);
        return builder;
    }

}
