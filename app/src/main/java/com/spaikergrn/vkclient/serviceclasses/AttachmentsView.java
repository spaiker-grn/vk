package com.spaikergrn.vkclient.serviceclasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.activity.fullscreenimageactivity.FullScreenImageViewActivity;
import com.spaikergrn.vkclient.imageloader.ImageLoader;
import com.spaikergrn.vkclient.services.MediaPlayerService;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkAttachmentsI;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkImageAttachment;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelAttachmentsI;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelAudioK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelGiftK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelLinkK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelStickerK;

import java.util.List;

public class AttachmentsView extends InflateLinearLayout {

    private ImageView mMainImageView;
    private LinearLayout mImagesLinearLayout;
    private TextView mLinkTextView;
    private ImageView mStickerImageView;
    private ImageView mGiftImageView;
    private TextView mDocTextView;
    private TextView mWallTextView;
    private TextView mTextViewAlbum;
    private TextView mTextViewTitle;
    private ImageView mAudioImageView;

    private String mPhotoSize = Constants.Parser.PHOTO_1280;
    private boolean mIsPhotoEnable = true;
    private boolean mIsMainViewEmpty = true;
    private Context mContext;
    private final int mPhotosInLineSize = 3;
    private View mAudioLayout;

    public AttachmentsView(final Context pContext) {
        super(pContext);
    }

    public AttachmentsView(final Context pContext, @Nullable final AttributeSet pAttributeSet) {
        super(pContext, pAttributeSet);
    }

    public AttachmentsView(final Context pContext, @Nullable final AttributeSet pAttributeSet, final int pDefStyleAttr) {
        super(pContext, pAttributeSet, pDefStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AttachmentsView(final Context pContext, final AttributeSet pAttributeSet, final int pDefStyleAttr, final int pDefStyleRes) {
        super(pContext, pAttributeSet, pDefStyleAttr, pDefStyleRes);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttributeSet) {
        initViews();
        mContext = pContext;
    }

    public void initViews() {
        mMainImageView = findViewById(R.id.attachment_main_image_view);
        mImagesLinearLayout = findViewById(R.id.attachment_images_layout);
        mLinkTextView = findViewById(R.id.link_text_view);
        mStickerImageView = findViewById(R.id.attachment_sticker);
        mGiftImageView = findViewById(R.id.attachment_gift);
        mDocTextView = findViewById(R.id.attachment_doc_text_view);
        mWallTextView = findViewById(R.id.attachment_wall_text_view);
        mAudioLayout = findViewById(R.id.attachment_audio_layout);
        mTextViewAlbum = findViewById(R.id.artist_text_view);
        mTextViewTitle = findViewById(R.id.title_text_view);
        mAudioImageView = findViewById(R.id.play_image_view);
    }

    public void setAttachmentParametrs(final String pPhotoSize, final boolean isPhotoEnable) {
        mPhotoSize = pPhotoSize;
        mIsPhotoEnable = isPhotoEnable;
    }

    public void initAttachment(final VkAttachmentsI pVkAttachmentsK, final int pWidth) {

        final List<VkModelAttachmentsI> vkAttachments = pVkAttachmentsK.getAttachmentsList();

        for (int i = 0; i < vkAttachments.size(); i++) {
            final VkModelAttachmentsI vkModelAttachments = vkAttachments.get(i);

            if (vkModelAttachments == null) {
                return;
            }

            final String type = vkModelAttachments.getType();

            switch (type) {
                case Constants.Parser.TYPE_PHOTO:
                case Constants.Parser.TYPE_VIDEO:
                case Constants.Parser.TYPE_POSTED_PHOTO:
                    initPhotoAttachment((VkImageAttachment) vkModelAttachments, pWidth);
                    break;
                case Constants.Parser.TYPE_LINK:
                    initLinkAttachment(vkModelAttachments, pWidth);
                    break;
                case Constants.Parser.TYPE_AUDIO:
                    initAudioAttachment(vkModelAttachments);
                    break;
                case Constants.Parser.TYPE_STICKER:
                    initStickerAttachment(vkModelAttachments);
                    break;
                case Constants.Parser.TYPE_GIFT:
                    initGiftAttachment(vkModelAttachments);
                    break;
                case Constants.Parser.TYPE_DOC:
                    initDocAttachment(vkModelAttachments);
                    break;
                case Constants.Parser.TYPE_POST:
                    initWallAttachment(vkModelAttachments);
                    break;
            }
        }
    }

    private void initWallAttachment(final VkModelAttachmentsI pVkModelAttachments) {
        final String textAttachments = Constants.ATTACHMENT + pVkModelAttachments.getType();

        mWallTextView.setVisibility(VISIBLE);
        mWallTextView.setText(textAttachments);
    }

    private void initDocAttachment(final VkModelAttachmentsI pVkModelAttachments) {
        final String textAttachments = Constants.ATTACHMENT + pVkModelAttachments.getType();

        mDocTextView.setVisibility(VISIBLE);
        mDocTextView.setText(textAttachments);
    }

    private void initGiftAttachment(final VkModelAttachmentsI pVkModelAttachments) {
        final VkModelGiftK vkModelGift = (VkModelGiftK) pVkModelAttachments;

        mGiftImageView.setVisibility(VISIBLE);
        ImageLoader.with(mContext).load(vkModelGift.getThumb256()).into(mGiftImageView);

    }

    private void initStickerAttachment(final VkModelAttachmentsI pVkModelAttachments) {
        final VkModelStickerK vkModelSticker = (VkModelStickerK) pVkModelAttachments;

        mStickerImageView.setVisibility(VISIBLE);
        ImageLoader.with(mContext).load(vkModelSticker.getPhoto352()).into(mStickerImageView);
    }

    private void initAudioAttachment(final VkModelAttachmentsI pVkModelAttachments) {
        final VkModelAudioK vkModelAudio = (VkModelAudioK) pVkModelAttachments;

        mAudioLayout.setVisibility(VISIBLE);
        mTextViewAlbum.setText(vkModelAudio.getArtist());
        mTextViewTitle.setText(vkModelAudio.getTitle());

        mAudioImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View pView) {
                Log.d(Constants.MY_TAG, "onClickPlay: " + vkModelAudio.getUrl());
                Toast.makeText(mContext, "Start to play " + vkModelAudio.getUrl(), Toast.LENGTH_LONG).show();
                if (vkModelAudio.getUrl() != null && !vkModelAudio.getUrl().isEmpty()) {
                    mContext.startService(new Intent(mContext, MediaPlayerService.class).putExtra(Constants.AUDIO_URL, vkModelAudio.getUrl()));

                }
            }
        });
    }

    private void initPhotoAttachment(final VkImageAttachment pVkImageAttachments, final int pWidth) {

        if (mIsPhotoEnable) {

            if (mIsMainViewEmpty) {
                addImageToMainImageView(pVkImageAttachments, pWidth);
            } else {
                addImageToSecondaryLayout(pVkImageAttachments, pWidth);
            }
        }

    }

    private void addImageToMainImageView(final VkImageAttachment pVkModelImage, final int pWidth) {
        final int height;
        mMainImageView.setVisibility(VISIBLE);

        if (pVkModelImage.getImageWidth() != 0 && pVkModelImage.getImageHeight() != 0) {
            height = pWidth * pVkModelImage.getImageHeight() / pVkModelImage.getImageWidth();
            final LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            mMainImageView.setLayoutParams(layoutParams);
        }

        ImageLoader.with(mContext).load(pVkModelImage.getMainPhotoForNews(mPhotoSize)).into(mMainImageView);

        mIsMainViewEmpty = false;
    }

    private void addImageToSecondaryLayout(final VkImageAttachment pVkModelImage, final int pWidth) {
        mImagesLinearLayout.setVisibility(VISIBLE);

        if (mImagesLinearLayout.getChildCount() == 0 || checkIsChildLayoutFull(mImagesLinearLayout)) {
            final LinearLayout newLinearLayout = initLinearLayout(mImagesLinearLayout, mContext);
            final ImageView imageView = initSmallImageView(newLinearLayout, pVkModelImage, pWidth, mPhotosInLineSize, mContext);
            ImageLoader.with(mContext).load(pVkModelImage.getSmallPhotoForNews()).into(imageView);
        } else {
            final LinearLayout imagesLinearLayout = (LinearLayout) mImagesLinearLayout.getChildAt(mImagesLinearLayout.getChildCount() - 1);
            final ImageView imageView = initSmallImageView(imagesLinearLayout, pVkModelImage, pWidth, mPhotosInLineSize, mContext);
            ImageLoader.with(mContext).load(pVkModelImage.getSmallPhotoForNews()).into(imageView);
        }

    }

    private LinearLayout initLinearLayout(final LinearLayout mParentLinearLayout, final Context pContext) {
        final LinearLayout linearLayoutForPhotos = new LinearLayout(pContext);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 4, 0, 0);
        linearLayoutForPhotos.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutForPhotos.setLayoutParams(layoutParams);
        mParentLinearLayout.addView(linearLayoutForPhotos);
        return linearLayoutForPhotos;
    }

    private ImageView initSmallImageView(final LinearLayout pLinearLayoutForPhotos, final VkImageAttachment pVkModelImage, final int pWidth, final int pScale, final Context pContext) {

        final int width = pWidth / pScale;
        final ImageView imageView = new ImageView(pContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final LinearLayout.LayoutParams paramsImageView = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsImageView.setMargins(0, 0, 2, 0);
        imageView.setLayoutParams(paramsImageView);
        if (pVkModelImage instanceof VkModelPhotoK) {
            final String photoId = ((VkModelPhotoK) pVkModelImage).getOwnerId() + "_" + ((VkModelPhotoK) pVkModelImage).getId() + "_" + ((VkModelPhotoK) pVkModelImage).getAccessKey();
            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(final View pView) {
                    FullScreenImageViewActivity.start(ContextHolder.getContext(), photoId);
                }
            });
        }
        pLinearLayoutForPhotos.addView(imageView);
        return imageView;
    }

    private void initLinkAttachment(final VkModelAttachmentsI pVkModelAttachments, final int pWidth) {
        final VkModelLinkK vkModelLink = (VkModelLinkK) pVkModelAttachments;
        mLinkTextView.setVisibility(VISIBLE);

        if (mIsMainViewEmpty && vkModelLink.getVkModelPhoto() != null) {
            addImageToMainImageView(vkModelLink.getVkModelPhoto(), pWidth);
        }

        mLinkTextView.setText(vkModelLink.getTitle());
        mLinkTextView.setTag(vkModelLink.getUrl());

        mLinkTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View pView) {
                final Uri uri = Uri.parse((String) pView.getTag());
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });

    }

    public void clearViews() {
        mMainImageView.setVisibility(GONE);
        mLinkTextView.setVisibility(GONE);
        mAudioLayout.setVisibility(GONE);
        mStickerImageView.setVisibility(GONE);
        mGiftImageView.setVisibility(GONE);
        mDocTextView.setVisibility(GONE);
        mWallTextView.setVisibility(GONE);
        mIsMainViewEmpty = true;
        mImagesLinearLayout.removeAllViews();
    }

    private boolean checkIsChildLayoutFull(final LinearLayout pLinearLayout) {
        final LinearLayout imagesLinearLayout = (LinearLayout) pLinearLayout.getChildAt(pLinearLayout.getChildCount() - 1);
        return (imagesLinearLayout.getChildCount() == mPhotosInLineSize);
    }

    @Override
    protected int getLayout() {
        return R.layout.attachments_view;
    }
}
