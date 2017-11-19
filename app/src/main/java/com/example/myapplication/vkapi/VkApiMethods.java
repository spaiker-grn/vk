package com.example.myapplication.vkapi;

import android.util.Log;

import com.example.myapplication.clients.RequestAsyncTask;
import com.example.myapplication.serviceclasses.Constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class VkApiMethods {

    public static String getApiMethod(final String pMethod, final Map<String, String> pFields) throws ExecutionException, InterruptedException {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        final RequestAsyncTask requestAsyncTask = new RequestAsyncTask();
        requestAsyncTask.execute(vkApiBuilder.buildUrl(pMethod, pFields));
        return requestAsyncTask.get();
    }

    public static String getApiMethod(final String pMethod) throws ExecutionException, InterruptedException {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        final RequestAsyncTask requestAsyncTask = new RequestAsyncTask();
        requestAsyncTask.execute(vkApiBuilder.buildUrl(pMethod));
        return requestAsyncTask.get();
    }

    public static String getDialogs(final int pStartMessage) throws ExecutionException, InterruptedException {

        if (pStartMessage != 0) {
            final Map<String, String> map = new HashMap<>();
            map.put("start_message_id", String.valueOf(pStartMessage));
            return getApiMethod(VkApiBuilder.GET_DIALOGS, map);
        }
        return getApiMethod(VkApiBuilder.GET_DIALOGS);
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
