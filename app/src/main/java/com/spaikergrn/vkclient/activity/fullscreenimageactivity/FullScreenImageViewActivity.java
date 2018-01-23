package com.spaikergrn.vkclient.activity.fullscreenimageactivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.imageloader.ImageLoader;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.tools.LikesOnClickListener;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

public class FullScreenImageViewActivity extends AppCompatActivity {

    ToggleButton mLikesToggleButton;
    ImageView mCommentsImageView;
    ImageView mFullScreenImageView;
    String mPhotoId;
    VkModelPhotoK mVkModelPhoto;
    private String mPhotoSize;

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_full_sreen_image_view);

        mPhotoSize = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(Constants.PreferencesKeys.PHOTO_SIZE, Constants.Parser.EMPTY_STRING);
        mPhotoId = getIntent().getStringExtra(Constants.FULL_SCREEN_IMAGE_VIEW);
        mLikesToggleButton = findViewById(R.id.likes_count_toggle_button);
        mCommentsImageView = findViewById(R.id.commend_image_view);
        mFullScreenImageView = findViewById(R.id.full_screen_image_view);

        final Bundle bundle = new Bundle();
        bundle.putString(Constants.LoadersKeys.PHOTO_LOADER_BUNDLE, mPhotoId);
        getSupportLoaderManager().initLoader(Constants.LoadersKeys.PHOTO_LOADER_ID, bundle, mLoaderCallbacks).forceLoad();

    }

    LoaderManager.LoaderCallbacks<VkModelPhotoK> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<VkModelPhotoK>() {

        @Override
        public Loader<VkModelPhotoK> onCreateLoader(final int pId, final Bundle pArgs) {

            Loader<VkModelPhotoK> photoLoader = null;
            if (pId == Constants.LoadersKeys.PHOTO_LOADER_ID) {
                photoLoader = new PhotoLoaderAsyncTask(FullScreenImageViewActivity.this, pArgs);
            }
            return photoLoader;
        }

        @Override
        public void onLoadFinished(final Loader<VkModelPhotoK> pLoader, final VkModelPhotoK pData) {
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
