package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import java.util.concurrent.Callable;

import io.reactivex.observers.DisposableObserver;

public interface FullScreenImageViewContract {

    interface FullScreenImageView {

        void onVkModelPhotoLoaded(final VkModelPhotoK pVkModelPhotoK);

        void onImageLoadedError(final Throwable pThrowable);
    }

    interface FullScreenImageViewModel {

        String getPhotoSize();

        void getVkModelPhoto(final String pPhotoId, final DisposableObserver<VkModelPhotoK> pDisposableObserver);

        Callable<VkModelPhotoK> initCallable(final String pPhotoId);

    }

    interface FullScreenImageViewPresenter {

        String getPhotoSize();

        void loadVkModelPhoto(final String pPhotoId);

        void onPause();

    }

}
