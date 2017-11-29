package com.example.myapplication.vkapi;

import android.util.Log;

import com.example.myapplication.clients.HttpUrlClient;
import com.example.myapplication.clients.IHttpUrlClient;
import com.example.myapplication.serviceclasses.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public final class VkApiMethods {

    private static String getApiMethod(final String pMethod, final Map<String, String> pFields) throws ExecutionException, InterruptedException {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        final IHttpUrlClient httpUrlClient = new HttpUrlClient();
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(pMethod, pFields));
        return httpUrlClient.getRequest(vkApiBuilder.buildUrl(pMethod, pFields));

    }

    private static String getApiMethod(final String pMethod) throws ExecutionException, InterruptedException {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        final IHttpUrlClient httpUrlClient = new HttpUrlClient();
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(pMethod));
        return httpUrlClient.getRequest(vkApiBuilder.buildUrl(pMethod));

    }

    public static String getDialogs(final int pOffset, final int pCount) throws ExecutionException, InterruptedException {

        final Map<String, String> map = new HashMap<>();
        map.put(Constants.URL_BUILDER.COUNT, String.valueOf(pCount));

        if (pOffset != 0) {
            map.put(Constants.URL_BUILDER.OFFSET, String.valueOf(pOffset));
            return getApiMethod(Constants.VkApiMethods.GET_DIALOGS, map);
        }
        return getApiMethod(Constants.VkApiMethods.GET_DIALOGS, map);
    }

    public static String getUserById(final int pId) throws ExecutionException, InterruptedException {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.URL_BUILDER.USER_IDS, String.valueOf(pId));
        map.put(Constants.URL_BUILDER.FIELDS, Constants.URL_BUILDER.PHOTO_50_PHOTO_100);
        return getApiMethod(Constants.VkApiMethods.GET_USER_BY_ID, map);
    }

/*    public static String getNews(String pCount, String pOffset) {

        final Map<String, String> map = new HashMap<>();
        map.put("count", "20");

    }*/

    public static String getFriendsOnline() throws ExecutionException, InterruptedException {

        return getApiMethod(Constants.VkApiMethods.ONLINE_FRIENDS);
    }

}
