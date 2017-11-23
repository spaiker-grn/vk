package com.example.myapplication.vkapi;

import android.icu.text.LocaleDisplayNames;
import android.util.Log;

import com.example.myapplication.clients.RequestAsyncTask;
import com.example.myapplication.serviceclasses.Constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public final class VkApiMethods {

    private static String getApiMethod(final String pMethod, final Map<String, String> pFields) throws ExecutionException, InterruptedException {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        final RequestAsyncTask requestAsyncTask = new RequestAsyncTask();
        requestAsyncTask.execute(vkApiBuilder.buildUrl(pMethod, pFields));
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(pMethod, pFields));
        return requestAsyncTask.get();
    }

    private static String getApiMethod(final String pMethod) throws ExecutionException, InterruptedException {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        final RequestAsyncTask requestAsyncTask = new RequestAsyncTask();
        requestAsyncTask.execute(vkApiBuilder.buildUrl(pMethod));
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(pMethod));
        return requestAsyncTask.get();
    }

    public static String getDialogs(final int pOffset, final int pCount) throws ExecutionException, InterruptedException {

        final Map<String, String> map = new HashMap<>();
        map.put("count", String.valueOf(pCount));

        if (pOffset != 0) {
            map.put("offset", String.valueOf(pOffset));
            return getApiMethod(VkApiBuilder.GET_DIALOGS, map);
        }
        return getApiMethod(VkApiBuilder.GET_DIALOGS, map);
    }

    public static String getUserById(final int pId) throws ExecutionException, InterruptedException {
        Map<String, String> map = new HashMap<>();
        map.put("user_ids", String.valueOf(pId));
        map.put("fields", "photo_50, photo_100");
        return getApiMethod(VkApi.GET_USER_BY_ID, map);
    }




    public static String getNews() {

        final Map<String, String> map = new HashMap<>();
        map.put("count", "20");
        final VkApiBuilder vkApiBuilder = new VkApiBuilder();
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(VkApiBuilder.NEWSFEED_GET, map));
        final RequestAsyncTask asyncTask = new RequestAsyncTask();
        asyncTask.execute(vkApiBuilder.buildUrl(VkApiBuilder.NEWSFEED_GET, map));
        try {
            return asyncTask.get();
        } catch (final InterruptedException pE) {
            pE.getMessage();

        } catch (final ExecutionException pE) {
            pE.getMessage();
        }
        return null;
    }

    public static String getFriendsOnline() {

        final VkApiBuilder vkApiBuilder = new VkApiBuilder();
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(VkApiBuilder.ONLINE_FRIENDS));
        final RequestAsyncTask asyncTask = new RequestAsyncTask();
        asyncTask.execute(vkApiBuilder.buildUrl(VkApiBuilder.ONLINE_FRIENDS));
        try {
            return asyncTask.get();
        } catch (final InterruptedException pE) {
            pE.getMessage();

        } catch (final ExecutionException pE) {
            pE.getMessage();
        }
        return null;
    }

    public static String getUsers(final int[] pStrings) {
        final Map<String, String> map = new HashMap<>();
        final String response = Arrays.toString(pStrings);
        final String code = (VkApiBuilder.GET_USERS_START + response) + VkApiBuilder.GET_USERS_END;
        map.put("code", code);
        final VkApiBuilder vkApiBuilder = new VkApiBuilder();
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(VkApiBuilder.EXECUTE, map));
        final RequestAsyncTask asyncTask = new RequestAsyncTask();
        asyncTask.execute(vkApiBuilder.buildUrl(VkApiBuilder.EXECUTE, map));
        try {
            return asyncTask.get();
        } catch (final InterruptedException pE) {
            pE.getMessage();

        } catch (final ExecutionException pE) {
            pE.getMessage();
        }
        return null;
    }

}
