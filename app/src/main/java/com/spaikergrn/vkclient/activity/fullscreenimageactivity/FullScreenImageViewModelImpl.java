package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import android.preference.PreferenceManager;
import android.support.annotation.VisibleForTesting;

import com.spaikergrn.vkclient.callablemodels.CallVkModelPhoto;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.serviceclasses.ContextHolder;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FullScreenImageViewModelImpl implements FullScreenImageViewContract.FullScreenImageViewModel {

    @Override
    public String getPhotoSize() {
        return PreferenceManager
                .getDefaultSharedPreferences(ContextHolder.getContext())
                .getString(Constants.PreferencesKeys.PHOTO_SIZE, Constants.Parser.EMPTY_STRING);
    }

    @Override
    public void getVkModelPhoto(final String pPhotoId, final DisposableObserver<VkModelPhotoK> pDisposableObserver) {
        Observable.fromCallable(initCallable(pPhotoId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pDisposableObserver);
    }

    @VisibleForTesting
    @Override
    public Callable<VkModelPhotoK> initCallable(final String pPhotoId){
        return new CallVkModelPhoto(pPhotoId);
    }

}