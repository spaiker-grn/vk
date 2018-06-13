package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import io.reactivex.observers.DisposableObserver;

public class FullScreenImageViewPresenterImpl implements FullScreenImageViewPresenter {

    private final FullScreenImageView mFullScreenImageView;
    private final FullScreenImageViewModel mFullScreenImageViewModel;

    FullScreenImageViewPresenterImpl(final FullScreenImageView pFullScreenImageView) {
        mFullScreenImageView = pFullScreenImageView;
        mFullScreenImageViewModel = new FullScreenImageViewModelImpl();
    }

    @Override
    public String getPhotoSize() {
        return mFullScreenImageViewModel.getPhotoSize();
    }

    @Override
    public void loadVkModelPhoto(final String pPhotoId) {
        mFullScreenImageViewModel.getVkModelPhoto(pPhotoId, mDisposableObserver);
    }

    private final DisposableObserver<VkModelPhotoK> mDisposableObserver = new DisposableObserver<VkModelPhotoK>() {

        @Override
        public void onNext(final VkModelPhotoK pVkModelPhotoK) {
            mFullScreenImageView.onVkModelPhotoLoaded(pVkModelPhotoK);
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

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        mDisposableObserver.dispose();
    }
}