package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import io.reactivex.observers.DisposableObserver;

public class FullScreenImageViewPresenterImpl implements FullScreenImageViewContract.FullScreenImageViewPresenter {

    private final FullScreenImageViewContract.FullScreenImageView mFullScreenImageView;
    private final FullScreenImageViewContract.FullScreenImageViewModel mFullScreenImageViewModel;

    private final DisposableObserver<VkModelPhotoK> mDisposableObserver = new DisposableObserver<VkModelPhotoK>() {

        @Override
        public void onNext(final VkModelPhotoK pVkModelPhotoK) {
            mFullScreenImageView.onVkModelPhotoLoaded(pVkModelPhotoK);
        }

        @Override
        public void onError(final Throwable pThrowable) {
            mFullScreenImageView.onImageLoadedError(pThrowable);
        }

        @Override
        public void onComplete() {

        }
    };

    public FullScreenImageViewPresenterImpl(final FullScreenImageViewContract.FullScreenImageView pFullScreenImageView,
                                            final FullScreenImageViewContract.FullScreenImageViewModel pScreenImageViewModel) {
        mFullScreenImageView = pFullScreenImageView;
        mFullScreenImageViewModel = pScreenImageViewModel;
    }

    @Override
    public String getPhotoSize() {
        return mFullScreenImageViewModel.getPhotoSize();
    }

    @Override
    public void loadVkModelPhoto(final String pPhotoId) {
        mFullScreenImageViewModel.getVkModelPhoto(pPhotoId, mDisposableObserver);
    }

    @Override
    public void onPause() {
        mDisposableObserver.dispose();
    }

}