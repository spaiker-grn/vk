package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class CallVkModelPhoto implements Callable<VkModelPhotoK> {

    private final String mPhotoId;

    CallVkModelPhoto(final String pPhotoId) {
        mPhotoId = pPhotoId;
    }

    @Override
    public VkModelPhotoK call() {
        try {

            return VkApiMethods.getPhotoById(mPhotoId);
        } catch (InterruptedException | ExecutionException | JSONException | IOException pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        }
        return null;
    }
}