package com.spaikergrn.vkclient.activity.messagehistoryactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.imageloader.ImageLoader;
import com.spaikergrn.vkclient.serviceclasses.AttachmentsFill;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.tools.ForwardMessagesOnClickListener;
import com.spaikergrn.vkclient.tools.TimesUtils;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelMessages;

class RecyclerViewHolderHistoryIn extends RecyclerView.ViewHolder {

    private final Context mContext;
    private final TextView mMessageTextView;
    private final TextView mTimeTextView;
    private final ImageView mImageView;
    private final LayoutInflater mInflater;
    private final int mWidth;
    private final LinearLayout mAttachmentLayout;
    private final LinearLayout mLinearLayout;

    RecyclerViewHolderHistoryIn(final Context pContext, final View pItemView) {
        super(pItemView);
        mContext = pContext;
        mMessageTextView = pItemView.findViewById(R.id.message_history_text_view);
        mTimeTextView = pItemView.findViewById(R.id.time_history_text_view);
        mImageView = pItemView.findViewById(R.id.user_history_image_view);
        mAttachmentLayout = pItemView.findViewById(R.id.attachment_message_history);
        mLinearLayout = pItemView.findViewById(R.id.history_message_body_linear_layout);
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mWidth = getWidthCardView(mContext);
    }

    void bind(final VkModelMessages pVkModelMessages) {

        final AttachmentsFill attachmentsFill = new AttachmentsFill(mContext);
        mAttachmentLayout.removeAllViews();

        if (pVkModelMessages.getBody().equals(Constants.Parser.EMPTY_STRING)){
            mMessageTextView.setVisibility(View.GONE);
        }else {
            mMessageTextView.setVisibility(View.VISIBLE);
            mMessageTextView.setText(pVkModelMessages.getBody());
        }

        mTimeTextView.setText(TimesUtils.getTimeHistory(pVkModelMessages.getDate()));

        if (pVkModelMessages.getVkModelUser() != null && !pVkModelMessages.isOut()) {
            ImageLoader.with(mContext).load(pVkModelMessages.getVkModelUser().getPhoto50()).into(mImageView);
        }

        if (pVkModelMessages.getFwdMessages() != null) {
            mMessageTextView.append(Constants.FWD_MESSAGES);
            mMessageTextView.setVisibility(View.VISIBLE);
            mMessageTextView.setOnClickListener(new ForwardMessagesOnClickListener(pVkModelMessages, mContext));
        }

        if (pVkModelMessages.getAttachments() != null && pVkModelMessages.getAttachments().getAttachmentsList().size() >= 1) {
            attachmentsFill.inflateAttachments(pVkModelMessages.getAttachments(), mAttachmentLayout, mWidth, mInflater, mContext, 3);
        }
    }

    private int getWidthCardView(final Context pContext) {
        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager wm = (WindowManager) pContext.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(metrics);
        return (int) (metrics.widthPixels - pContext.getResources().getDimension(R.dimen.margin_start_dialogs) * 2 - pContext.getResources().getDimension(R.dimen.margin_profile_image_view) * 2 - pContext.getResources().getDimension(R.dimen.size_profile_image_view));
    }

}
