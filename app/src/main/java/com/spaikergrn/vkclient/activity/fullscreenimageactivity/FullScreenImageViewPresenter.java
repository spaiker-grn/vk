package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

public interface FullScreenImageViewPresenter {

    String getPhotoSize();

    void loadVkModelPhoto(final String pPhotoId);

    void onPause();

}