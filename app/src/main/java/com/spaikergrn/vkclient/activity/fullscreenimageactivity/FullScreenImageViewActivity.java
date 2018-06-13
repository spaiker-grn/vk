package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.imageloader.ImageLoader;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.tools.LikesOnClickListener;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

public class FullScreenImageViewActivity extends AppCompatActivity implements FullScreenImageView {

    private ToggleButton mLikesToggleButton;
    private ImageView mCommentsImageView;
    private ImageView mFullScreenImageView;
    private String mPhotoSize;
    private FullScreenImageViewPresenter mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_full_sreen_image_view);
        mPresenter = new FullScreenImageViewPresenterImpl(this);
        initViews();
        mPhotoSize = mPresenter.getPhotoSize();
        final String photoId = getIntent().getStringExtra(Constants.FULL_SCREEN_IMAGE_VIEW);
        mPresenter.loadVkModelPhoto(photoId);
    }

    private void initViews() {
        mLikesToggleButton = findViewById(R.id.likes_count_toggle_button);
        mCommentsImageView = findViewById(R.id.commend_image_view);
        mFullScreenImageView = findViewById(R.id.full_screen_image_view);
    }

    @Override
    public void onVkModelPhotoLoaded(final VkModelPhotoK pVkModelPhotoK) {

        if (pVkModelPhotoK != null) {
            mLikesToggleButton.setOnClickListener(new LikesOnClickListener(pVkModelPhotoK.getType(), pVkModelPhotoK.getOwnerId(), pVkModelPhotoK.getId(), pVkModelPhotoK));
            ImageLoader.with(this).load(pVkModelPhotoK.getPhotoBySize(mPhotoSize)).into(mFullScreenImageView);

            if (pVkModelPhotoK.getUserLike()) {
                mLikesToggleButton.setChecked(true);
            }
        }
    }

}