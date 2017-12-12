package com.example.myapplication.activity.messagehistoryactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.SparseArray;

import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.ParseUtils;
import com.example.myapplication.vkapi.VkApiMethods;
import com.example.myapplication.vkapi.vkapimodels.VkModelChat;
import com.example.myapplication.vkapi.vkapimodels.VkModelMessages;
import com.example.myapplication.vkapi.vkapimodels.VkModelUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AsyncTaskMessageHistoryParsing extends AsyncTaskLoader<List<VkModelMessages>> {

    private int mStartMessageId;
    private int mHistoryId;
    private int mMessagesCount;
    private final List<VkModelMessages> mMessagesList = new ArrayList<>();
    private SparseArray<VkModelUser> mVkModelUserSparseArray = new SparseArray<>();

    AsyncTaskMessageHistoryParsing(final Context pContext, final Bundle pArgs) {
        super(pContext);

        if (pArgs != null) {
            mStartMessageId = pArgs.getInt(Constants.URL_BUILDER.START_MESSAGE_ID);
            mHistoryId = pArgs.getInt(Constants.URL_BUILDER.USER_HISTORY);
        }
    }

    @Override
    public List<VkModelMessages> loadInBackground() {

        try {
            String response;
            response = VkApiMethods.getMessageHistory(mHistoryId, mStartMessageId);
            final JSONArray jsonArray;
            jsonArray = ParseUtils.getJSONArrayItems(response);

            if (jsonArray != null) {
                mMessagesCount = new JSONObject(response).getJSONObject(Constants.Parser.RESPONSE).getInt(Constants.URL_BUILDER.COUNT);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final VkModelMessages vkModelMessages = new VkModelMessages(jsonArray.getJSONObject(i));
                    vkModelMessages.setCountMessagesHistory(mMessagesCount);
                    mMessagesList.add(vkModelMessages);
                }
            }

            if (mMessagesList.get(0).getChatId() != 0) {
                response = VkApiMethods.getChatById(mMessagesList.get(0).getChatId());
                final VkModelChat chat = new VkModelChat(new JSONObject(response).getJSONObject(Constants.Parser.RESPONSE));
                mVkModelUserSparseArray = ParseUtils.getUsersById(Arrays.asList(chat.getUsers()));
                for (int i = 0; i < mMessagesList.size(); i++){
                    mMessagesList.get(i).setVkModelUser(mVkModelUserSparseArray.get(mMessagesList.get(i).getUserId()));
                }
            }

        } catch (IOException | JSONException | ExecutionException | InterruptedException pE) {
            Log.e(Constants.MY_TAG, "loadInBackground: ", pE);

        }
        return mMessagesList;
    }
}
