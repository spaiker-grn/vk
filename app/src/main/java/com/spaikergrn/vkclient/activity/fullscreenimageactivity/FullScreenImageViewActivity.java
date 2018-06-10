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

public class FullScreenImageViewActivity extends AppCompatActivity implements ImageViewActivity {

    ToggleButton mLikesToggleButton;
    ImageView mCommentsImageView;
    ImageView mFullScreenImageView;
    String mPhotoId;
    VkModelPhotoK mVkModelPhoto;
    private String mPhotoSize;
    ImageActivityPresenter mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_full_sreen_image_view);
        mPresenter = new ImageActivityPresenterImpl(this);
        initViews();
        mPhotoSize = mPresenter.getPhotoSize();
        mPhotoId = getIntent().getStringExtra(Constants.FULL_SCREEN_IMAGE_VIEW);
        mPresenter.loadVkModelPhoto(mPhotoId);
    }

    private void initViews() {
        mLikesToggleButton = findViewById(R.id.likes_count_toggle_button);
        mCommentsImageView = findViewById(R.id.commend_image_view);
        mFullScreenImageView = findViewById(R.id.full_screen_image_view);
    }

    @Override
    public void onVkModelPhotoLoaded(final VkModelPhotoK pVkModelPhotoK) {
        if (pVkModelPhotoK != null) {
            mVkModelPhoto = pVkModelPhotoK;
            mLikesToggleButton.setOnClickListener(new LikesOnClickListener(mVkModelPhoto.getType(), mVkModelPhoto.getOwnerId(), mVkModelPhoto.getId(), mVkModelPhoto));
            ImageLoader.with(this).load(mVkModelPhoto.getPhotoBySize(mPhotoSize)).into(mFullScreenImageView);
            if (mVkModelPhoto.getUserLike()) {
                mLikesToggleButton.setChecked(true);
            }
        }
    }

}