package com.spaikergrn.vk_client.activity.fullscreenimageactivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.spaikergrn.vk_client.R;
import com.spaikergrn.vk_client.imageloader.ImageLoader;
import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.tools.LikesOnClickListener;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelPhoto;

public class FullScreenImageViewActivity extends AppCompatActivity {

    int PHOTO_LOADER = 1;
    ToggleButton mLikesToggleButton;
    ImageView mCommentsImageView;
    ImageView mFullScreenImageView;
    String mPhotoId;
    VkModelPhoto mVkModelPhoto;
    private String mPhotoSize;

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.full_sreen_image_view_activity);

        mPhotoSize = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(Constants.PreferencesKeys.PHOTO_SIZE, Constants.Parser.EMPTY_STRING);
        mPhotoId = getIntent().getStringExtra(Constants.FULL_SCREEN_IMAGE_VIEW);
        mLikesToggleButton = findViewById(R.id.likes_count_toggle_button);
        mCommentsImageView = findViewById(R.id.commend_image_view);
        mFullScreenImageView = findViewById(R.id.full_screen_image_view);

        final Bundle bundle = new Bundle();
        bundle.putString(Constants.PHOTO_LOADER, mPhotoId);
        getSupportLoaderManager().initLoader(PHOTO_LOADER, bundle, mLoaderCallbacks).forceLoad();

    }

    LoaderManager.LoaderCallbacks<VkModelPhoto> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<VkModelPhoto>() {

        @Override
        public Loader<VkModelPhoto> onCreateLoader(final int pId, final Bundle pArgs) {

            Loader<VkModelPhoto> photoLoader = null;
            if (pId == PHOTO_LOADER) {
                photoLoader = new PhotoLoaderAsyncTask(FullScreenImageViewActivity.this, pArgs);
            }
            return photoLoader;
        }

        @Override
        public void onLoadFinished(final Loader<VkModelPhoto> pLoader, final VkModelPhoto pData) {
            if (pData != null) {
                mVkModelPhoto = pData;
                mLikesToggleButton.setOnClickListener(new LikesOnClickListener(mVkModelPhoto.getType(), mVkModelPhoto.getOwnerId(), mVkModelPhoto.getId(), mVkModelPhoto));
                ImageLoader.with(FullScreenImageViewActivity.this).load(mVkModelPhoto.getPhotoBySize(mPhotoSize)).into(mFullScreenImageView);
                if (mVkModelPhoto.getUserLike()) {
                    mLikesToggleButton.setChecked(true);
                }
            }
        }

        @Override
        public void onLoaderReset(final Loader pLoader) {

        }
    };
}
