package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import io.reactivex.observers.DisposableObserver;

public interface ImageActivityModel {

    String getPhotoSize();

    void getVkModelPhoto(final String pPhotoId, final DisposableObserver<VkModelPhotoK> pDisposableObserver);

}