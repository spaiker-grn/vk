package com.example.myapplication.vkapi;

import android.util.Log;

import com.example.myapplication.clients.HttpUrlClient;
import com.example.myapplication.clients.IHttpUrlClient;
import com.example.myapplication.serviceclasses.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public final class VkApiMethods {

    private static String getApiMethod(final String pMethod, final Map<String, String> pFields) throws ExecutionException, InterruptedException, IOException {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        final IHttpUrlClient httpUrlClient = new HttpUrlClient();
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(pMethod, pFields));
        return httpUrlClient.getRequest(vkApiBuilder.buildUrl(pMethod, pFields));

    }

    private static String getApiMethod(final String pMethod) throws ExecutionException, InterruptedException, IOException {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        final IHttpUrlClient httpUrlClient = new HttpUrlClient();
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(pMethod));
        return httpUrlClient.getRequest(vkApiBuilder.buildUrl(pMethod));

    }

/*    public static String getDialogs(final int pOffset, final int pCount) throws ExecutionException, InterruptedException, IOException {

        final Map<String, String> map = new HashMap<>();
        map.put(Constants.URL_BUILDER.COUNT, String.valueOf(pCount));

        if (pOffset != 0) {
            map.put(Constants.URL_BUILDER.OFFSET, String.valueOf(pOffset));
            return getApiMethod(Constants.VkApiMethods.GET_DIALOGS, map);
        }
        return getApiMethod(Constants.VkApiMethods.GET_DIALOGS, map);
    }*/

    public static String getDialogs(final int pStartMessageId) throws ExecutionException, InterruptedException, IOException {

        final Map<String, String> map = new HashMap<>();

        if (pStartMessageId != 0) {
            map.put(Constants.URL_BUILDER.OFFSET, "1");
            map.put(Constants.URL_BUILDER.START_MESSAGE_ID, String.valueOf(pStartMessageId));
            return getApiMethod(Constants.VkApiMethods.GET_DIALOGS, map);
        }
        return getApiMethod(Constants.VkApiMethods.GET_DIALOGS);
    }

    public static String getUserById(final int pId) throws ExecutionException, InterruptedException, IOException {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.URL_BUILDER.USER_IDS, String.valueOf(pId));
        map.put(Constants.URL_BUILDER.FIELDS, Constants.URL_BUILDER.PHOTO_50_PHOTO_100);
        return getApiMethod(Constants.VkApiMethods.GET_USER_BY_ID, map);
    }

    public static String getLongPollDate() throws ExecutionException, InterruptedException, IOException {
        return getApiMethod(Constants.Parser.MESSAGES_GET_LONG_POLL_SERVER);
    }

    public static String getNews(final String pOffset) throws InterruptedException, ExecutionException, IOException {

        final Map<String, String> map = new HashMap<>();
        map.put(Constants.Parser.COUNT, Constants.VkApiMethods.VALUE_20);
        map.put(Constants.VkApiMethods.FILTERS, Constants.VkApiMethods.POST_PHOTO);

        if (pOffset != null) {
            map.put(Constants.VkApiMethods.START_FROM, pOffset);
        }
        return getApiMethod(Constants.VkApiMethods.NEWSFEED_GET, map);
    }

    public static String getMessageHistory(final int pHistoryId, final int pStartMessageId) throws InterruptedException, ExecutionException, IOException {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.VkApiMethods.PEER_ID, String.valueOf(pHistoryId));
        if (pStartMessageId != 0){
            map.put(Constants.URL_BUILDER.START_MESSAGE_ID, String.valueOf(pStartMessageId));
            map.put(Constants.URL_BUILDER.OFFSET, String.valueOf(1));
        }

        return getApiMethod(Constants.VkApiMethods.MESSAGES_GET_HISTORY, map);
    }

    public static String getChatById(final int pChatId) throws ExecutionException, InterruptedException, IOException {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.URL_BUILDER.CHAT_ID, String.valueOf(pChatId));
        return getApiMethod(Constants.VkApiMethods.MESSAGES_GET_CHAT, map);
    }


    public static String getFriendsOnline() throws ExecutionException, InterruptedException, IOException {

        return getApiMethod(Constants.VkApiMethods.ONLINE_FRIENDS);
    }

}
