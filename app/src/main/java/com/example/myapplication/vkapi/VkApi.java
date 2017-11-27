package com.example.myapplication.vkapi;

import android.net.Uri;

import com.example.myapplication.clients.WebViewClientLogin;

import java.util.Map;

/**
 * Created by Дмитрий on 05.11.2017.
 */

public class VkApi implements IVkApiBuilder {

    private final String mToken;
    public static String ONLINE_FRIENDS = "friends.getOnline";
    public static String NEWSFEED_GET = "newsfeed.get";
    public static String GET_DIALOGS = "messages.getDialogs";
    public static String GET_USER_BY_ID = "users.get";
    public static String EXECUTE = "execute";
    public static String GET_USERS_START = "return {\"items\" : API.users.get({\"user_ids\" : \"";
    public static String GET_USERS_END = "\",  \"fields\" : \"photo_50, photo_100\"})} ;";


    public VkApi() {

        mToken = WebViewClientLogin.loadToken();

    }

    public String buildUrl(final String pMethod) {

        final Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("api.vk.com");
        builder.appendPath("method");
        builder.appendPath(pMethod);
        builder.appendQueryParameter("v", "5.68");
        builder.appendQueryParameter("access_token", mToken);
        return builder.build().toString();

    }

    public String buildUrl(final String pMethod, final Map<String, String> pParameters) {

        final Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("api.vk.com");
        builder.appendPath("method");
        builder.appendPath(pMethod);
        for (final Map.Entry<String, String> entry : pParameters.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        builder.appendQueryParameter("v", "5.68");
        builder.appendQueryParameter("access_token", mToken);
        return builder.build().toString();

    }


}
