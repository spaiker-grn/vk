package com.spaikergrn.vkclient.loaders;

import android.support.v4.content.Loader;

import com.spaikergrn.vkclient.serviceclasses.ProfileInfoHolder;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser;

public class ProfileInfoOnLoadCompleteListener implements Loader.OnLoadCompleteListener<VkModelUser> {

    @Override
    public void onLoadComplete(final Loader<VkModelUser> pLoader, final VkModelUser pData) {
        ProfileInfoHolder.setVkModelUser(pData);
    }
}
