package com.spaikergrn.vkclient.fragments.newsfragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.imageloader.ImageLoader;
import com.spaikergrn.vkclient.serviceclasses.AttachmentsFill;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.tools.LikesOnClickListener;
import com.spaikergrn.vkclient.tools.TimesUtils;

import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelCopyHistoryPost;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelGroup;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelNewsFeeds;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelNewsPost;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser;

class RecyclerNewsFeedViewHolder extends RecyclerView.ViewHolder {

    private static final int SCALE = 3;
    private final ImageView mProfileImageView;
    private final TextView mName;
    private final TextView mTime;
    private final TextView mDescription;
    private final TextView mWatchersCount;
    private final TextView mLikesTextView;
    private final NewsFragment mNewsFragment;
    private final LinearLayout mLinearLayoutAttachments;
    private final int mWidth;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final TextView mCopyHistoryNameTextView;
    private final TextView mCopyHistoryTextTextView;
    private final LinearLayout mCopyHistoryLLAttachments;
    private final VkModelNewsFeeds mVkModelNewsFeeds;
    private final View mCopyHistory;
    private final ToggleButton mLikesToggleButton;

    RecyclerNewsFeedViewHolder(final NewsFragment pNewsFragment, final VkModelNewsFeeds pVkModelNewsFeeds, final View pItemView) {
        super(pItemView);
        mNewsFragment = pNewsFragment;
        mProfileImageView = pItemView.findViewById(R.id.card_news_profile_image_view);
        mName = pItemView.findViewById(R.id.card_news_name_text_view);
        mTime = pItemView.findViewById(R.id.card_news_time_after_update_text_view);
        mDescription = pItemView.findViewById(R.id.card_news_description_text_view);
        mWatchersCount = pItemView.findViewById(R.id.watchers_count_text_view);
        mLikesTextView = pItemView.findViewById(R.id.likes_count_text_view);
        mLikesToggleButton = pItemView.findViewById(R.id.likes_count_toggle_button);
        mLinearLayoutAttachments = pItemView.findViewById(R.id.attachment_fragment);
        mCopyHistory = pItemView.findViewById(R.id.copy_history_layout);
        mCopyHistoryNameTextView = pItemView.findViewById(R.id.card_news_name_copy_history_text_view);
        mCopyHistoryTextTextView = pItemView.findViewById(R.id.text_copy_history_text_view);
        mCopyHistoryLLAttachments = pItemView.findViewById(R.id.attachment_copy_history);
        mVkModelNewsFeeds = pVkModelNewsFeeds;
        mContext = mNewsFragment.getContext();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mWidth = getWidthCardView(mContext);
        ImageLoader.with(mContext).setPlaceHolder(R.drawable.ic_images);
    }

    void bind(final VkModelNewsPost pVkModelNewsPost) {

        final AttachmentsFill attachmentsFill = new AttachmentsFill(mContext);
        mLinearLayoutAttachments.removeAllViews();
        mCopyHistoryLLAttachments.removeAllViews();
        mLikesToggleButton.setOnClickListener(new LikesOnClickListener(pVkModelNewsPost.getPostType(),
                pVkModelNewsPost.getSourceID(), pVkModelNewsPost.getPostID(), pVkModelNewsPost));

        mLikesToggleButton.setBackgroundResource(R.drawable.favorite_selector);

        if (pVkModelNewsPost.getVkModelUser() != null) {
            ImageLoader.with(mContext).load(pVkModelNewsPost.getVkModelUser().getPhoto50()).into(mProfileImageView);
            mName.setText(pVkModelNewsPost.getVkModelUser().getFullName());
        }
        if (pVkModelNewsPost.getVkModelGroup() != null) {
            ImageLoader.with(mContext).load(pVkModelNewsPost.getVkModelGroup().getPhoto50()).into(mProfileImageView);
            mName.setText(pVkModelNewsPost.getVkModelGroup().getName());
        }

        mTime.setText(TimesUtils.getTimeDialogs(pVkModelNewsPost.getDate()));
        mDescription.setText(pVkModelNewsPost.getText());
        mWatchersCount.setText(String.valueOf(pVkModelNewsPost.getViewsCount()));
        mLikesTextView.setText(String.valueOf(pVkModelNewsPost.getLikesCount()));
        mCopyHistory.setVisibility(View.GONE);

        if (pVkModelNewsPost.getUserLike()) {
            mLikesToggleButton.setChecked(true);
        } else {
            mLikesToggleButton.setChecked(false);
        }

        if (pVkModelNewsPost.getCopyHistory() != null) {
            mCopyHistory.setVisibility(View.VISIBLE);
            final VkModelCopyHistoryPost vkModelPostHistory = pVkModelNewsPost.getCopyHistory().get(0);
            if (!vkModelPostHistory.getText().equals(Constants.Parser.EMPTY_STRING)) {
                mCopyHistoryTextTextView.setVisibility(View.VISIBLE);
                mCopyHistoryTextTextView.setText(vkModelPostHistory.getText());
            } else {
                mCopyHistoryTextTextView.setVisibility(View.GONE);
            }
            if (vkModelPostHistory.getFromId() < 0) {
                final VkModelGroup vkModelGroup = mVkModelNewsFeeds.getGroupMap().get(Math.abs(vkModelPostHistory.getFromId()));
                mCopyHistoryNameTextView.setText(vkModelGroup.getName());
            } else {
                final VkModelUser vkModelUser = mVkModelNewsFeeds.getUserMap().get(vkModelPostHistory.getFromId());
                mCopyHistoryNameTextView.setText(vkModelUser.getFullName());
            }

            attachmentsFill.inflateAttachments(vkModelPostHistory.getVkAttachments(), mLinearLayoutAttachments, mWidth, mInflater, mContext, SCALE);
        }
        attachmentsFill.inflateAttachments(pVkModelNewsPost.getVkAttachments(), mLinearLayoutAttachments, mWidth, mInflater, mContext, SCALE);
    }

    private int getWidthCardView(final Context pContext) {
        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager wm = (WindowManager) pContext.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(metrics);
        return (int) (metrics.widthPixels - pContext.getResources().getDimension(R.dimen.margin_card_views_news) * 2);
    }
}
