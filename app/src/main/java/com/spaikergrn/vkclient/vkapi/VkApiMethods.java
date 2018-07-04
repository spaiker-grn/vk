package com.spaikergrn.vkclient.vkapi;

import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import java.util.HashMap;
import java.util.Map;

public final class VkApiMethods {

    private static String getApiMethod(final String pMethod, final Map<String, String> pFields) {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(pMethod, pFields));
        return vkApiBuilder.buildUrl(pMethod, pFields);
    }

    private static String getApiMethod(final String pMethod) {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(pMethod));
        return vkApiBuilder.buildUrl(pMethod);
    }

    private static String getLongPollApiMethod(final String pMethod, final Map<String, String> pFields) {

        final IVkApiBuilder vkApiBuilder = new VkApiBuilder();
        Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(pMethod, pFields));
        return vkApiBuilder.buildUrl(pMethod, pFields);
    }

    public static String getDialogs(final int pStartMessageId, final int pCount) {

        final Map<String, String> map = new HashMap<>();

        if (pStartMessageId != 0) {
            map.put(Constants.URL_BUILDER.COUNT, String.valueOf(pCount));
            map.put(Constants.URL_BUILDER.OFFSET, Constants.Values.STRING_VALUE_ONE);
            map.put(Constants.URL_BUILDER.START_MESSAGE_ID, String.valueOf(pStartMessageId));
            return getApiMethod(Constants.VkApiMethods.GET_DIALOGS, map);
        } else {
            map.put(Constants.URL_BUILDER.COUNT, String.valueOf(pCount));
            return getApiMethod(Constants.VkApiMethods.GET_DIALOGS);
        }
    }

    public static String getLongPollServer() {
        return getApiMethod(Constants.VkApiMethods.MESSAGES_GET_LONG_POLL_SERVER);
    }

    public static String getNews(final String pOffset) {

        final Map<String, String> map = new HashMap<>();
        map.put(Constants.Parser.COUNT, Constants.VkApiMethods.VALUE_20);
        map.put(Constants.VkApiMethods.FILTERS, Constants.VkApiMethods.POST);

        if (pOffset != null) {
            map.put(Constants.VkApiMethods.START_FROM, pOffset);
        }
        return getApiMethod(Constants.VkApiMethods.NEWSFEED_GET, map);
    }

    public static String getMessageHistory(final int pHistoryId, final int pStartMessageId, final int pCount) {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.VkApiMethods.PEER_ID, String.valueOf(pHistoryId));
        map.put(Constants.Parser.COUNT, String.valueOf(pCount));
        if (pStartMessageId != 0) {
            map.put(Constants.URL_BUILDER.START_MESSAGE_ID, String.valueOf(pStartMessageId));
            map.put(Constants.URL_BUILDER.OFFSET, String.valueOf(1));
        }
        return getApiMethod(Constants.VkApiMethods.MESSAGES_GET_HISTORY, map);
    }

    public static String getChatById(final int pChatId) {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.URL_BUILDER.CHAT_ID, String.valueOf(pChatId));
        return getApiMethod(Constants.VkApiMethods.MESSAGES_GET_CHAT, map);
    }

    public static String sendMessage(final int pId, final String pMessage) {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.VkApiMethods.PEER_ID, String.valueOf(pId));
        map.put(Constants.Parser.MESSAGE, pMessage);
        return getApiMethod(Constants.VkApiMethods.MESSAGES_SEND, map);
    }

    public static String getLongPollHistory(final String pTs) {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.URL_BUILDER.FIELDS, Constants.URL_BUILDER.PHOTO_50_PHOTO_100);
        map.put(Constants.URL_BUILDER.TS, pTs);
        return getLongPollApiMethod(Constants.VkApiMethods.MESSAGES_GET_LONG_POLL_HISTORY, map);
    }

    public static String addLike(final String pType, final int pOwnerId, final int pItemId) {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.Parser.TYPE, pType);
        map.put(Constants.Parser.OWNER_ID, String.valueOf(pOwnerId));
        map.put(Constants.VkApiMethods.ITEM_ID, String.valueOf(pItemId));
        return getApiMethod(Constants.VkApiMethods.LIKES_ADD, map);
    }

    public static String deleteLike(final String pType, final int pOwnerId, final int pItemId) {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.Parser.TYPE, pType);
        map.put(Constants.Parser.OWNER_ID, String.valueOf(pOwnerId));
        map.put(Constants.VkApiMethods.ITEM_ID, String.valueOf(pItemId));
        return getApiMethod(Constants.VkApiMethods.LIKES_DELETE, map);
    }

    public static String getPhotoById(final String pPhotoId) {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.VkApiMethods.PHOTOS, pPhotoId);
        map.put(Constants.VkApiMethods.EXTENDED, Constants.Values.STRING_VALUE_ONE);

        return getApiMethod(Constants.VkApiMethods.PHOTOS_GET_BY_ID, map);
    }

    public static String getProfileInfo() {
        final Map<String, String> map = new HashMap<>();
        map.put(Constants.URL_BUILDER.FIELDS, Constants.URL_BUILDER.PHOTO_50_PHOTO_100);
        return getApiMethod(Constants.VkApiMethods.GET_USER_BY_ID, map);
    }

}
