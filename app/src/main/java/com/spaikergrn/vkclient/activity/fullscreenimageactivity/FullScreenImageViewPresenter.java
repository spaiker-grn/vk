package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import com.spaikergrn.vkclient.activity.Presenter;

public interface FullScreenImageViewPresenter extends Presenter {

    String getPhotoSize();

    void loadVkModelPhoto(final String pPhotoId);

}