package com.spaikergrn.vkclient.callablemodels;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.spaikergrn.vkclient.clients.HttpUrlClient;
import com.spaikergrn.vkclient.clients.IHttpUrlClient;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;

public class CallVkModelPhoto implements Callable<VkModelPhotoK> {

    private final String mPhotoId;

    public CallVkModelPhoto(final String pPhotoId) {
        mPhotoId = pPhotoId;
    }

    @Override
    public VkModelPhotoK call() {
        try {
            final String response = getHttpClient().getRequestWithErrorCheck(buildRequest());
            final JSONObject jsonObject = new JSONObject(response).getJSONArray(Constants.Parser.RESPONSE).getJSONObject(0);
            return new VkModelPhotoK(jsonObject);
        } catch (final IOException | JSONException pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE);
        }
        return null;
    }

    @VisibleForTesting
    public IHttpUrlClient getHttpClient(){
        return new HttpUrlClient();
    }

    @VisibleForTesting
    public String buildRequest(){
        return VkApiMethods.getPhotoById(mPhotoId);
    }
}