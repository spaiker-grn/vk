package com.spaikergrn.vkclient.loaders;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.serviceclasses.ContextHolder;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoaderProfileInfo extends AsyncTaskLoader<VkModelProfile> {

    public LoaderProfileInfo(final Context pContext) {
        super(pContext);
    }

    @Override
    public VkModelProfile loadInBackground() {
        try {
            final String response = VkApiMethods.getProfileInfo();
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextHolder.getContext());
            final SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.PreferencesKeys.PREFERENCES_PROFILE_INFO, response);
            editor.apply();
            return new VkModelProfile(new JSONObject(response).getJSONObject(Constants.Parser.RESPONSE));
        } catch (JSONException | InterruptedException | ExecutionException | IOException pE) {
            Log.e(Constants.ERROR, "Loader Profile Info: ", pE.getCause());
        }
        return null;
    }
}
