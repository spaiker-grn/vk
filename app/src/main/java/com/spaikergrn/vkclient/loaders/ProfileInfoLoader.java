package com.spaikergrn.vkclient.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.serviceclasses.ProfileInfoHolder;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ProfileInfoLoader extends AsyncTaskLoader<VkModelUser> {

    public ProfileInfoLoader(final Context pContext) {
        super(pContext);
    }

    @Override
    public VkModelUser loadInBackground() {
        try {
            final String response = VkApiMethods.getProfileInfo();
            if (new JSONObject(response).has(Constants.Parser.RESPONSE)){
                ProfileInfoHolder.saveProfileInfoPreferences(response);
                return new VkModelUser(new JSONObject(response).getJSONArray(Constants.Parser.RESPONSE).getJSONObject(0));
            }
        } catch (JSONException | InterruptedException | ExecutionException | IOException pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        }
        return null;
    }

}
