package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PhotoLoaderAsyncTask extends AsyncTaskLoader<VkModelPhotoK> {

    private final String mPhotoId;

    PhotoLoaderAsyncTask(final Context pContext, final Bundle pBundle) {
        super(pContext);
        mPhotoId = pBundle.getString(Constants.LoadersKeys.PHOTO_LOADER_BUNDLE);
    }

    @Override
    public VkModelPhotoK loadInBackground() {

        try {
            final String string = VkApiMethods.getPhotoById(mPhotoId);
            final JSONObject jsonObject = new JSONObject(string).getJSONArray(Constants.Parser.RESPONSE).getJSONObject(0);
            return new VkModelPhotoK(jsonObject);
        } catch (InterruptedException | ExecutionException | JSONException | IOException pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        }
        return null;
    }
}
