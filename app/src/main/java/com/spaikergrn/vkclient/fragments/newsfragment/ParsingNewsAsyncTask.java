package com.spaikergrn.vkclient.fragments.newsfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.spaikergrn.vkclient.clients.HttpUrlClient;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelNewsFeedsK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ParsingNewsAsyncTask extends AsyncTaskLoader<VkModelNewsFeedsK> {

    private VkModelNewsFeedsK mVkModelNewsFeeds;
    private final String mStartFrom;

    ParsingNewsAsyncTask(final Context pContext, final Bundle pBundle) {
        super(pContext);

        mStartFrom = pBundle.getString(Constants.Parser.NEXT_FROM);
    }


    @Override
    public VkModelNewsFeedsK loadInBackground() {

        try {
            final String request = new HttpUrlClient().getRequestWithErrorCheck(VkApiMethods.getNews(mStartFrom));
            mVkModelNewsFeeds = new VkModelNewsFeedsK(new JSONObject(request));
        } catch (IOException | JSONException pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        }
        return mVkModelNewsFeeds;
    }
}
