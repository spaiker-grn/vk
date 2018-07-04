package com.spaikergrn.vkclient.serviceclasses;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.activity.fullscreenimageactivity.FullScreenImageViewActivity;
import com.spaikergrn.vkclient.imageloader.ImageLoader;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkAttachmentsI;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelAttachmentsI;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import java.util.List;

public class AttachmentsView extends InflateLinearLayout {

    private ImageView mMainImageView;
    private LinearLayout mImagesLinearLayout;
    private TextView mLinkTextView;
    private ImageView mStickerImageView;
    private ImageView mGiftImageView;
    private TextView mDocTextView;
    private TextView mWallTextView;
    private String mPhotoSize = Constants.Parser.PHOTO_1280;
    private boolean mIsPhotoEnable = true;
    private boolean mIsMainViewEmpty = true;
    private Context mContext;
    private final int mPhotosInLineSize = 3;

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
                    initPhotoAttachment(vkModelAttachments, pWidth);
                    break;
                default:
                    break;
/*                case Constants.Parser.TYPE_VIDEO:
                    initVideoAttachment(vkModelAttachments);
                    break;
                case Constants.Parser.TYPE_LINK:
                    initLinkAttachment(vkModelAttachments);
                    break;
                case Constants.Parser.TYPE_AUDIO:
                    initAudioAttachment(vkModelAttachments);
                    break;
                case Constants.Parser.TYPE_POSTED_PHOTO:
                    initPhotoAttachment(vkModelAttachments);
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
                    break;*/
            }
        }
    }

    private void initPhotoAttachment(final VkModelAttachmentsI pVkModelAttachments, final int pWidth) {

        if (mIsPhotoEnable) {
            final VkModelPhotoK vkModelPhoto = (VkModelPhotoK) pVkModelAttachments;

            if (mIsMainViewEmpty) {
                addImageToMainImageView(vkModelPhoto, pWidth);
            } else {
                addImageToSecondaryLayout(vkModelPhoto, pWidth);
            }
        }

    }

    private void addImageToMainImageView(final VkModelPhotoK pVkModelPhoto, final int pWidth) {
        final int height;
        mMainImageView.setVisibility(VISIBLE);

        if (pVkModelPhoto.getWidth() != 0 && pVkModelPhoto.getHeight() != 0) {
            height = pWidth * pVkModelPhoto.getHeight() / pVkModelPhoto.getWidth();
            final LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            mMainImageView.setLayoutParams(layoutParams);
        }

        ImageLoader.with(mContext).load(pVkModelPhoto.getPhotoBySize(mPhotoSize)).into(mMainImageView);
        mIsMainViewEmpty = false;
    }

    private void addImageToSecondaryLayout(final VkModelPhotoK pVkModelPhoto, final int pWidth) {
        mImagesLinearLayout.setVisibility(VISIBLE);

        if (mImagesLinearLayout.getChildCount() == 0 || checkIsChildLayoutFull(mImagesLinearLayout)) {
            final LinearLayout newLinearLayout = initLinearLayout(mImagesLinearLayout, mContext);
            final ImageView imageView = initSmallImageView(newLinearLayout, pVkModelPhoto, pWidth, mPhotosInLineSize,  mContext);
            ImageLoader.with(mContext).load(pVkModelPhoto.getPhotoBySize(mPhotoSize)).into(imageView);
        } else {
            final LinearLayout imagesLinearLayout = (LinearLayout) mImagesLinearLayout.getChildAt(mImagesLinearLayout.getChildCount() - 1);
            final ImageView imageView = initSmallImageView(imagesLinearLayout, pVkModelPhoto, pWidth, mPhotosInLineSize,  mContext);
            ImageLoader.with(mContext).load(pVkModelPhoto.getPhotoBySize(mPhotoSize)).into(imageView);
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

    private ImageView initSmallImageView(final LinearLayout pLinearLayoutForPhotos, final VkModelPhotoK pVkModelPhoto, final int pWidth, final int pScale,  final Context pContext) {

        final int width = pWidth / pScale;
        final ImageView imageView = new ImageView(pContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final LinearLayout.LayoutParams paramsImageView = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsImageView.setMargins(0, 0, 2, 0);
        imageView.setLayoutParams(paramsImageView);
        if (pVkModelPhoto != null) {
            final String photoId = pVkModelPhoto.getOwnerId() + "_" + pVkModelPhoto.getId() + "_" + pVkModelPhoto.getAccessKey();
            imageView.setTag(R.string.key_tag_photo, photoId);
            imageView.setOnClickListener(onClickListenerImageView);
        }
        pLinearLayoutForPhotos.addView(imageView);
        return imageView;
    }

    private final View.OnClickListener onClickListenerImageView = new View.OnClickListener() {

        @Override
        public void onClick(final View pView) {
            FullScreenImageViewActivity.start(ContextHolder.getContext(), ((String) pView.getTag(R.string.key_tag_photo)));
        }
    };

    public void clearViews() {
        mMainImageView.setVisibility(GONE);
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
