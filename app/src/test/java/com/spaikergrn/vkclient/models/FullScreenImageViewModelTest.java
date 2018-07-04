package com.spaikergrn.vkclient.models;

import com.spaikergrn.vkclient.BuildConfig;
import com.spaikergrn.vkclient.activity.fullscreenimageactivity.FullScreenImageViewContract;
import com.spaikergrn.vkclient.activity.fullscreenimageactivity.FullScreenImageViewModelImpl;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.Callable;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FullScreenImageViewModelTest {

    private FullScreenImageViewContract.FullScreenImageViewModel mFullScreenImageViewModel;

    @Mock
    private Callable<VkModelPhotoK> mVkModelPhotoCallable;

    private final DisposableObserver<VkModelPhotoK> mPhotoDisposableObserver = spy(new DisposableObserver<VkModelPhotoK>() {

        @Override
        public void onNext(final VkModelPhotoK pVkModelPhotoK) {

        }

        @Override
        public void onError(final Throwable pThrowable) {

        }

        @Override
        public void onComplete() {

        }
    });

    @Before
    public void setUp() {
        initMocks(this);

        mFullScreenImageViewModel = spy(new FullScreenImageViewModelImpl());
        doReturn(mVkModelPhotoCallable).when(mFullScreenImageViewModel).initCallable(anyString());
    }

    @Test
    public void getVkModelTest() throws Exception {
        mFullScreenImageViewModel.getVkModelPhoto("", mPhotoDisposableObserver);

        verify(mFullScreenImageViewModel, times(1)).initCallable(anyString());
        verify(mVkModelPhotoCallable, times(1)).call();
    }

}
