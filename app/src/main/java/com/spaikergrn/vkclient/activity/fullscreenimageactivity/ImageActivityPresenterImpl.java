package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import io.reactivex.observers.DisposableObserver;

public class ImageActivityPresenterImpl implements ImageActivityPresenter {

    private final ImageViewActivity mImageViewActivity;
    private final ImageActivityModel mImageActivityModel;

    ImageActivityPresenterImpl(final ImageViewActivity pImageViewActivity) {
        mImageViewActivity = pImageViewActivity;
        mImageActivityModel = new ImageActivityModelImpl();
    }

    @Override
    public String getPhotoSize() {
        return mImageActivityModel.getPhotoSize();
    }

    @Override
    public void loadVkModelPhoto(final String pPhotoId) {
        mImageActivityModel.getVkModelPhoto(pPhotoId, mDisposableObserver);
    }

    private final DisposableObserver<VkModelPhotoK> mDisposableObserver = new DisposableObserver<VkModelPhotoK>() {

        @Override
        public void onNext(final VkModelPhotoK pVkModelPhotoK) {
            mImageViewActivity.onVkModelPhotoLoaded(pVkModelPhotoK);
        }

        @Override
        public void onError(final Throwable pThrowable) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    public void onDestroy() {
        mDisposableObserver.dispose();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}