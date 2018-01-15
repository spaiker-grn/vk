package com.spaikergrn.vk_client.activity.fullscreenimageactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.vkapi.VkApiMethods;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelPhoto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PhotoLoaderAsyncTask extends AsyncTaskLoader<VkModelPhoto> {

    private final String mPhotoId;

    PhotoLoaderAsyncTask(final Context pContext, final Bundle pBundle) {
        super(pContext);
        mPhotoId = pBundle.getString(Constants.PHOTO_LOADER);
    }

    @Override
    public VkModelPhoto loadInBackground() {

        try {
            final String string = VkApiMethods.getPhotoById(mPhotoId);
            final JSONObject jsonObject = new JSONObject(string).getJSONArray(Constants.Parser.RESPONSE).getJSONObject(0);
            return new VkModelPhoto(jsonObject);
        } catch (InterruptedException | ExecutionException | JSONException | IOException pE) {
            Log.e(Constants.ERROR, "Get photo byId run: ", pE);
        }
        return null;
    }
}
