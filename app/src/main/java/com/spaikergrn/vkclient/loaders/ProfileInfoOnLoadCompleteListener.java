package com.spaikergrn.vkclient.loaders;

import android.support.v4.content.Loader;

import com.spaikergrn.vkclient.serviceclasses.ProfileInfoHolder;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelProfile;

public class ProfileInfoOnLoadCompleteListener implements Loader.OnLoadCompleteListener<VkModelProfile> {

    @Override
    public void onLoadComplete(final Loader<VkModelProfile> pLoader, final VkModelProfile pData) {
        ProfileInfoHolder.setVkModelProfile(pData);
    }
}
