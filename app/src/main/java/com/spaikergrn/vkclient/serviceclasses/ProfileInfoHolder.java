package com.spaikergrn.vkclient.serviceclasses;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.spaikergrn.vkclient.loaders.ProfileInfoLoader;
import com.spaikergrn.vkclient.loaders.ProfileInfoOnLoadCompleteListener;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser;

import org.json.JSONException;
import org.json.JSONObject;

public final class ProfileInfoHolder {

    private static VkModelUser mVkModelUser;

    public static VkModelUser getVkModelUser() {
        return mVkModelUser;
    }

    public static void setVkModelUser(final VkModelUser pVkModelUser) {
        mVkModelUser = pVkModelUser;
    }

    public static void initVkModelUser() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextHolder.getContext());
        final String profileInfo = preferences.getString(Constants.PreferencesKeys.PREFERENCES_PROFILE_INFO, null);
        if (profileInfo != null){
            try {
                mVkModelUser = new VkModelUser(new JSONObject(profileInfo).getJSONArray(Constants.Parser.RESPONSE).getJSONObject(0));
            } catch (final JSONException pE) {
                Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
            }
        } else {
            final ProfileInfoLoader profileInfoLoader = new ProfileInfoLoader(ContextHolder.getContext());
            profileInfoLoader.registerListener(profileInfoLoader.getId(), new ProfileInfoOnLoadCompleteListener());
            profileInfoLoader.forceLoad();
        }
    }

    public static void saveProfileInfoPreferences(final String pResponse) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextHolder.getContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.PreferencesKeys.PREFERENCES_PROFILE_INFO, pResponse);
        editor.apply();
    }

    public static void deleteProfileInfoPreferences() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextHolder.getContext());
        preferences.edit().remove(Constants.PreferencesKeys.PREFERENCES_PROFILE_INFO).apply();
    }
}
