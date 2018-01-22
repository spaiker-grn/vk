package com.spaikergrn.vkclient.serviceclasses;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelAttachmentsI;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelAudioK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelGiftK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelLinkK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelStickerK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelVideoK;

import java.util.List;

public class AttachmentsFillK {


    private static final int GIFT_SIZE_IN_DP = 120;
    private final boolean sIsLoadPhoto;
    private final String sPhotoSize;

    public AttachmentsFillK(final Context pContext) {

        sPhotoSize = PreferenceManager
                .getDefaultSharedPreferences(pContext)
                .getString(Constants.PreferencesKeys.PHOTO_SIZE, Constants.Parser.EMPTY_STRING);

        sIsLoadPhoto = PreferenceManager
                .getDefaultSharedPreferences(pContext)
                .getBoolean(Constants.PreferencesKeys.ENABLE_PHOTO, true);
    }

    public void inflateAttachments(final VkAttachmentsI pVkModelAttachment,
                                   final LinearLayout pParentLinearLayout, final int pWidth,
                                   final LayoutInflater pInflater, final Context pContext, final int pScale) {

        if (pVkModelAttachment == null) {
            return;
        }

        String type;

        final List<VkModelAttachmentsI> pVkAttachmentsList = pVkModelAttachment.getAttachmentsList();

        for (int i = 0; i < pVkAttachmentsList.size(); i++) {
            type = pVkAttachmentsList.get(i).getType();
            if (Constants.Parser.TYPE_PHOTO.equals(type)) {
                inflateImage(pVkAttachmentsList.get(i), pParentLinearLayout, pWidth, pContext, pScale);
            }
        }

        for (int i = 0; i < pVkAttachmentsList.size(); i++) {
            type = pVkAttachmentsList.get(i).getType();
            if (Constants.Parser.TYPE_VIDEO.equals(type)) {
                inflateVideo(pVkAttachmentsList.get(i), pParentLinearLayout, pWidth, pContext, pScale);
            }
        }

        for (int i = 0; i < pVkAttachmentsList.size(); i++) {
            type = pVkAttachmentsList.get(i).getType();
            if (Constants.Parser.TYPE_LINK.equals(type)) {
                inflateLink(pVkAttachmentsList.get(i), pParentLinearLayout, pWidth, pContext);
            }
        }

        for (int i = 0; i < pVkAttachmentsList.size(); i++) {
            type = pVkAttachmentsList.get(i).getType();
            if (Constants.Parser.TYPE_AUDIO.equals(type)) {
                inflateAudio(pVkAttachmentsList.get(i), pParentLinearLayout, pInflater, pContext);
            }
        }

        for (int i = 0; i < pVkAttachmentsList.size(); i++) {
            type = pVkAttachmentsList.get(i).getType();
            if (Constants.Parser.TYPE_POSTED_PHOTO.equals(type)) {
                inflateImage(pVkAttachmentsList.get(i), pParentLinearLayout, pWidth, pContext, pScale);
            }
        }

        for (int i = 0; i < pVkAttachmentsList.size(); i++) {
            type = pVkAttachmentsList.get(i).getType();
            if (Constants.Parser.TYPE_STICKER.equals(type)) {
                inflateSticker(pVkAttachmentsList.get(i), pParentLinearLayout, pContext);
            }
        }

        for (int i = 0; i < pVkAttachmentsList.size(); i++) {
            type = pVkAttachmentsList.get(i).getType();
            if (Constants.Parser.TYPE_GIFT.equals(type)) {
                inflateGift(pVkAttachmentsList.get(i), pParentLinearLayout, pContext);
            }
        }

        for (int i = 0; i < pVkAttachmentsList.size(); i++) {
            type = pVkAttachmentsList.get(i).getType();
            if (Constants.Parser.TYPE_DOC.equals(type)) {
                inflateDoc(pVkAttachmentsList.get(i), pParentLinearLayout, pContext);
            }
        }

        for (int i = 0; i < pVkAttachmentsList.size(); i++) {
            type = pVkAttachmentsList.get(i).getType();
            if (Constants.Parser.TYPE_POST.equals(type)) {
                inflateWall(pVkAttachmentsList.get(i), pParentLinearLayout, pContext);
            }
        }
    }

    private static void inflateSticker(final VkModelAttachmentsI pVkModelAttachments,
                                       final LinearLayout mParentLinearLayout, final Context pContext) {

        final VkModelStickerK vkModelSticker = (VkModelStickerK) pVkModelAttachments;
        final int width = 120;
        final int height = width * vkModelSticker.getHeight() / vkModelSticker.getWidth();
        final ImageView imageView = new ImageView(pContext);
        final LinearLayout.LayoutParams paramsImageView = new LinearLayout.LayoutParams(convertDpToPixel(width, pContext), convertDpToPixel(height, pContext));
        imageView.setLayoutParams(paramsImageView);
        imageView.setBackgroundColor(Color.TRANSPARENT);
        mParentLinearLayout.addView(imageView);
        ImageLoader.with(pContext).load(vkModelSticker.getPhoto352()).into(imageView);
    }

    private static void inflateGift(final VkModelAttachmentsI pVkModelAttachments,
                                    final LinearLayout mParentLinearLayout, final Context pContext) {

        final VkModelGiftK vkModelGift = (VkModelGiftK) pVkModelAttachments;
        final int size = GIFT_SIZE_IN_DP;
        final ImageView imageView = new ImageView(pContext);
        final LinearLayout.LayoutParams paramsImageView = new LinearLayout.LayoutParams(convertDpToPixel(size, pContext), convertDpToPixel(size, pContext));
        imageView.setLayoutParams(paramsImageView);
        imageView.setBackgroundColor(Color.TRANSPARENT);
        mParentLinearLayout.addView(imageView);
        ImageLoader.with(pContext).load(vkModelGift.getThumb256()).into(imageView);
    }

    private void inflateImage(final VkModelAttachmentsI pVkModelAttachments,
                              final LinearLayout mParentLinearLayout, final int pWidth, final Context pContext, final int pScale) {

        if (!sIsLoadPhoto) {
            return;
        }

        final VkModelPhotoK vkModelPhoto = (VkModelPhotoK) pVkModelAttachments;
        final ImageView imageView;
        LinearLayout linearLayoutForPhotos;

        if (mParentLinearLayout.getChildCount() == 0) {

            mParentLinearLayout.setGravity(Gravity.CENTER);
            imageView = initMainImageView(mParentLinearLayout, vkModelPhoto, pWidth, pContext);
            ImageLoader.with(pContext).load(vkModelPhoto.getPhotoBySize(sPhotoSize)).into(imageView);

        } else {
            if (mParentLinearLayout.getChildCount() > 1 && mParentLinearLayout.getChildAt(mParentLinearLayout.getChildCount() - 1) instanceof LinearLayout) {
                linearLayoutForPhotos = (LinearLayout) mParentLinearLayout.getChildAt(mParentLinearLayout.getChildCount() - 1);

                if (linearLayoutForPhotos.getChildCount() > 0 && linearLayoutForPhotos.getChildCount() < pScale) {
                    imageView = initSmallImageView(linearLayoutForPhotos, pWidth, pScale, vkModelPhoto, pContext);
                    ImageLoader.with(pContext).load(vkModelPhoto.getPhotoBySize(sPhotoSize)).into(imageView);
                    return;
                }
            }
            linearLayoutForPhotos = initLinearLayout(mParentLinearLayout, pContext);
            imageView = initSmallImageView(linearLayoutForPhotos, pWidth, pScale, vkModelPhoto, pContext);
            ImageLoader.with(pContext).load(vkModelPhoto.getSmallPhotoForNews()).into(imageView);
        }
    }

    private void inflateVideo(final VkModelAttachmentsI pVkModelAttachments,
                              final LinearLayout mParentLinearLayout, final int pWidth, final Context pContext, final int pScale) {

        final VkModelVideoK vkModelVideo = (VkModelVideoK) pVkModelAttachments;
        final LinearLayout.LayoutParams paramsImageView;
        final ImageView imageView;
        LinearLayout linearLayoutForPhotos;

        if (!sIsLoadPhoto) {
            return;
        }

        if (mParentLinearLayout.getChildCount() == 0) {
            mParentLinearLayout.setGravity(Gravity.CENTER);
            imageView = new ImageView(pContext);
            if (vkModelVideo.getHeight() != 0 && vkModelVideo.getWidth() != 0) {
                final int height = pWidth * vkModelVideo.getHeight() / vkModelVideo.getWidth();
                paramsImageView = new LinearLayout.LayoutParams(pWidth, height);
            } else {
                paramsImageView = new LinearLayout.LayoutParams(pWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            paramsImageView.setMargins(0, 0, 0, 4);
            imageView.setLayoutParams(paramsImageView);
            mParentLinearLayout.addView(imageView, paramsImageView);
            ImageLoader.with(pContext).load(vkModelVideo.getPhotoForNews()).into(imageView);

        } else {
            if (mParentLinearLayout.getChildCount() > 1 && mParentLinearLayout.getChildAt(mParentLinearLayout.getChildCount() - 1) instanceof LinearLayout) {
                linearLayoutForPhotos = (LinearLayout) mParentLinearLayout.getChildAt(mParentLinearLayout.getChildCount() - 1);
                if (linearLayoutForPhotos.getChildCount() > 0 && linearLayoutForPhotos.getChildCount() < pScale) {
                    imageView = initSmallImageView(linearLayoutForPhotos, pWidth, pScale, null, pContext);
                    ImageLoader.with(pContext).load(vkModelVideo.getSmallPhotoForNews()).into(imageView);
                    return;
                }
            }
            linearLayoutForPhotos = initLinearLayout(mParentLinearLayout, pContext);
            imageView = initSmallImageView(linearLayoutForPhotos, pWidth, pScale, null, pContext);
            ImageLoader.with(pContext).load(vkModelVideo.getSmallPhotoForNews()).into(imageView);
        }
    }

    private void inflateAudio(final VkModelAttachmentsI pVkModelAttachments, final LinearLayout mParentLinearLayout,
                              final LayoutInflater pInflater, final Context pContext) {

        final VkModelAudioK vkModelAudio = (VkModelAudioK) pVkModelAttachments;
        final View view = pInflater.inflate(R.layout.audio_layout, mParentLinearLayout, false);
        final TextView textViewAlbum = view.findViewById(R.id.artist_text_view);
        textViewAlbum.setText(vkModelAudio.getArtist());
        final TextView textViewTitle = view.findViewById(R.id.title_text_view);
        textViewTitle.setText(vkModelAudio.getTitle());
        final ImageView imageView = view.findViewById(R.id.play_image_view);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View pView) {
                Log.d(Constants.MY_TAG, "onClickPlay: " + vkModelAudio.getUrl());
                Toast.makeText(pContext, "Start to play " + vkModelAudio.getUrl(), Toast.LENGTH_LONG).show();
                if (!vkModelAudio.getUrl().equals(Constants.Parser.EMPTY_STRING)) {
                    pContext.startService(new Intent(pContext, MediaPlayerService.class).putExtra(Constants.AUDIO_URL, vkModelAudio.getUrl()));

                }
            }
        });
        mParentLinearLayout.addView(view);
    }

    private void inflateLink(final VkModelAttachmentsI pVkModelAttachments,
                             final LinearLayout mParentLinearLayout, final int pWidth, final Context pContext) {

        final VkModelLinkK vkModelLink = (VkModelLinkK) pVkModelAttachments;
        if (mParentLinearLayout.getChildCount() == 0 && vkModelLink.getVkModelPhoto() != null) {
            final ImageView imageView = initMainImageView(mParentLinearLayout, vkModelLink.getVkModelPhoto(), pWidth, pContext);
            ImageLoader.with(pContext).load(vkModelLink.getVkModelPhoto().getPhotoBySize(sPhotoSize)).into(imageView);
        }

        final TextView linkTextView = new TextView(pContext);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linkTextView.setText(vkModelLink.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            linkTextView.setTextAppearance(R.style.text_link);
        } else {
            linkTextView.setTextAppearance(pContext, R.style.text_link);
        }
        linkTextView.setTag(vkModelLink.getUrl());
        linkTextView.setPadding(8, 4, 4, 4);
        linkTextView.setMaxLines(1);
        linkTextView.setPaintFlags(linkTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        linkTextView.setLayoutParams(layoutParams);
        linkTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View pView) {
                final Uri uri = Uri.parse(vkModelLink.getUrl());
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                pContext.startActivity(intent);
            }
        });
        mParentLinearLayout.addView(linkTextView);
    }

    private void inflateDoc(final VkModelAttachmentsI pVkModelAttachments,
                            final LinearLayout mParentLinearLayout, final Context pContext) {
        final TextView textView = new TextView(pContext);
        final LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTextView.setMargins(0, 0, 0, 4);
        textView.setLayoutParams(paramsTextView);
        final String textAttachments = Constants.ATTACHMENT + pVkModelAttachments.getType();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(pContext, R.style.news_text);
        } else {
            textView.setTextAppearance(R.style.news_text);
        }
        textView.setText(textAttachments);
        mParentLinearLayout.addView(textView);
    }

    private void inflateWall(final VkModelAttachmentsI pVkModelAttachments,
                             final LinearLayout mParentLinearLayout, final Context pContext) {
        final TextView textView = new TextView(pContext);
        final LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTextView.setMargins(0, 0, 0, 4);
        textView.setLayoutParams(paramsTextView);
        final String textAttachments = Constants.ATTACHMENT + pVkModelAttachments.getType();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(pContext, R.style.news_text);
        } else {
            textView.setTextAppearance(R.style.news_text);
        }
        textView.setText(textAttachments);
        mParentLinearLayout.addView(textView);

    }

    private static ImageView initMainImageView(final LinearLayout mParentLinearLayout,
                                               final VkModelPhotoK pVkModelPhoto, final int pWidth, final Context pContext) {
        final ImageView imageView = new ImageView(pContext);
        final int height;
        if (pVkModelPhoto.getWidth() != 0 && pVkModelPhoto.getHeight() != 0){
            height = pWidth * pVkModelPhoto.getHeight() / pVkModelPhoto.getWidth();
        } else {
            height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }
        final LinearLayout.LayoutParams paramsImageView = new LinearLayout.LayoutParams(pWidth, height);
        paramsImageView.setMargins(0, 0, 0, 4);
        imageView.setLayoutParams(paramsImageView);
        final String photoId = pVkModelPhoto.getOwnerId() + "_" + pVkModelPhoto.getId() + "_" + pVkModelPhoto.getAccessKey();
        Log.d(Constants.MY_TAG, "initMainImageView: " + photoId);
        imageView.setTag(R.string.key_tag_photo, photoId);
        imageView.setOnClickListener(onClickListenerImageView);
        mParentLinearLayout.addView(imageView);
        return imageView;
    }

    private static ImageView initSmallImageView(final LinearLayout pLinearLayoutForPhotos, final int pWidth, final int pScale, final VkModelPhotoK pVkModelPhoto, final Context pContext) {

        final int width = pWidth / pScale;
        final ImageView imageView = new ImageView(pContext);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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

    private static LinearLayout initLinearLayout(final LinearLayout mParentLinearLayout, final Context pContext) {
        final LinearLayout linearLayoutForPhotos = new LinearLayout(pContext);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 4);
        linearLayoutForPhotos.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutForPhotos.setLayoutParams(layoutParams);
        mParentLinearLayout.addView(linearLayoutForPhotos);
        return linearLayoutForPhotos;
    }

    private static int convertDpToPixel(final float pDp, final Context pContext) {
        final Resources resources = pContext.getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) pDp * metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
    }

    private static final View.OnClickListener onClickListenerImageView = new View.OnClickListener() {

        @Override
        public void onClick(final View pView) {
            ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(),
                    FullScreenImageViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(Constants.FULL_SCREEN_IMAGE_VIEW, (String) pView.getTag(R.string.key_tag_photo)));
        }
    };

}
