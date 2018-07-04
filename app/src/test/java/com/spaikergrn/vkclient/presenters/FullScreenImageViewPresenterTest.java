package com.spaikergrn.vkclient.presenters;

import com.spaikergrn.vkclient.activity.fullscreenimageactivity.FullScreenImageViewContract;
import com.spaikergrn.vkclient.activity.fullscreenimageactivity.FullScreenImageViewPresenterImpl;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class FullScreenImageViewPresenterTest {

    @Mock
    private FullScreenImageViewContract.FullScreenImageView mView;

    @Mock
    private FullScreenImageViewContract.FullScreenImageViewModel mModel;

    private FullScreenImageViewContract.FullScreenImageViewPresenter mPresenter;

    @Before
    public void setUp() {
        initMocks(this);
        mPresenter = spy(new FullScreenImageViewPresenterImpl(mView, mModel));
    }

    @Test
    public void getPhotoSizeTest() {
        mPresenter.getPhotoSize();

        verify(mModel, times(1)).getPhotoSize();
    }

    @Test
    public void loadVkModelPhotoTest() {
        mPresenter.loadVkModelPhoto("");

        verify(mModel, times(1))
                .getVkModelPhoto(eq(""), ArgumentMatchers.<DisposableObserver<VkModelPhotoK>>any());
    }

}
