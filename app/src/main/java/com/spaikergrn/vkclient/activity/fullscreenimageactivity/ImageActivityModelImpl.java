package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import android.preference.PreferenceManager;

import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.serviceclasses.ContextHolder;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ImageActivityModelImpl implements ImageActivityModel {

    @Override
    public String getPhotoSize() {
        return PreferenceManager
                .getDefaultSharedPreferences(ContextHolder.getContext())
                .getString(Constants.PreferencesKeys.PHOTO_SIZE, Constants.Parser.EMPTY_STRING);
    }

    @Override
    public void getVkModelPhoto(final String pPhotoId, final DisposableObserver<VkModelPhotoK> pDisposableObserver) {
        Observable.fromCallable(new CallVkModelPhoto(pPhotoId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(pDisposableObserver);
    }
}