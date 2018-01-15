package com.spaikergrn.vk_client.fragments.newsfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.vkapi.VkApiMethods;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelNewsFeeds;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ParsingNewsAsyncTask extends AsyncTaskLoader<VkModelNewsFeeds> {

    private VkModelNewsFeeds mVkModelNewsFeeds;
    private final String mStartFrom;

    ParsingNewsAsyncTask(final Context pContext, final Bundle pBundle) {
        super(pContext);

        /*if (pBundle.getString(Constants.Parser.NEXT_FROM) != null)*/
        mStartFrom = pBundle.getString(Constants.Parser.NEXT_FROM);
    }


    @Override
    public VkModelNewsFeeds loadInBackground() {

        try {
            final String request = VkApiMethods.getNews(mStartFrom);
            mVkModelNewsFeeds = new VkModelNewsFeeds(new JSONObject(request));
        } catch (InterruptedException | ExecutionException | IOException | JSONException pE) {
            Log.e(Constants.ERROR, "loadInBackground News Feed: ", pE);
        }
        return mVkModelNewsFeeds;
    }
}
