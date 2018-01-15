package com.spaikergrn.vk_client.activity.messagehistoryactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.SparseArray;

import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.tools.ParseUtils;
import com.spaikergrn.vk_client.vkapi.VkApiMethods;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelChat;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelMessages;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AsyncTaskMessageHistoryParsing extends AsyncTaskLoader<List<VkModelMessages>> {

    private int mStartMessageId;
    private int mHistoryId;
    private int mCount;
    private final List<VkModelMessages> mMessagesList = new ArrayList<>();

    AsyncTaskMessageHistoryParsing(final Context pContext, final Bundle pArgs) {
        super(pContext);

        if (pArgs != null) {
            mStartMessageId = pArgs.getInt(Constants.URL_BUILDER.START_MESSAGE_ID);
            mHistoryId = pArgs.getInt(Constants.URL_BUILDER.USER_HISTORY);
            mCount = pArgs.getInt(Constants.URL_BUILDER.COUNT);
        }
    }

    @Override
    public List<VkModelMessages> loadInBackground() {

        try {
            String response;
            final JSONArray jsonArray;
            response = VkApiMethods.getMessageHistory(mHistoryId, mStartMessageId, mCount);
            jsonArray = ParseUtils.getJSONArrayItems(response);

            if (jsonArray != null) {
                final SparseArray<VkModelUser> vkModelUserMap;
                final LinkedHashSet<Integer> hashSetUsers = new LinkedHashSet<>();

                final int messagesCount = new JSONObject(response).getJSONObject(Constants.Parser.RESPONSE).getInt(Constants.URL_BUILDER.COUNT);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final VkModelMessages vkModelMessages = new VkModelMessages(jsonArray.getJSONObject(i));
                    vkModelMessages.setCountMessagesHistory(messagesCount);
                    mMessagesList.add(vkModelMessages);
                    if (vkModelMessages.getFromId() != 0 ){
                        hashSetUsers.add(vkModelMessages.getFromId());
                    } else {
                        hashSetUsers.add(vkModelMessages.getUserId());
                    }
                }
                vkModelUserMap = ParseUtils.getUsersById(new ArrayList<>(hashSetUsers));
                for (int i = 0; i < mMessagesList.size(); i++) {
                    final int userId;
                    if (mMessagesList.get(i).getFromId() != 0 ){
                        userId = mMessagesList.get(i).getFromId();
                    } else {
                        userId = mMessagesList.get(i).getUserId();
                    }
                    mMessagesList.get(i).setVkModelUser(vkModelUserMap.get(userId));
                }
            }

            if (mMessagesList.get(0).getChatId() != 0) {
                response = VkApiMethods.getChatById(mMessagesList.get(0).getChatId());
                final VkModelChat chat = new VkModelChat(new JSONObject(response).getJSONObject(Constants.Parser.RESPONSE));
                final SparseArray<VkModelUser> vkModelUserSparseArray = ParseUtils.getUsersById(Arrays.asList(chat.getUsers()));
                for (int i = 0; i < mMessagesList.size(); i++){
                    mMessagesList.get(i).setVkModelUser(vkModelUserSparseArray.get(mMessagesList.get(i).getUserId()));
                }
            }

        } catch (IOException | JSONException | ExecutionException | InterruptedException pE) {
            Log.e(Constants.MY_TAG, "loadInBackground: ", pE);
        }
        return mMessagesList;
    }
}
