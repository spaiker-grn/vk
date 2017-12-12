package com.example.myapplication.activity.messagehistoryactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.imageloader.ImageLoader;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.TimesUtils;
import com.example.myapplication.vkapi.vkapimodels.VkModel;
import com.example.myapplication.vkapi.vkapimodels.VkModelMessages;
import com.example.myapplication.vkapi.vkapimodels.VkModelUser;

class RecyclerViewHolderHistoryIn extends RecyclerView.ViewHolder {

    private final Context mContext;
    private final TextView mMessageTextView;
    private final TextView mTimeTextView;
    private final ImageView mImageView;

    RecyclerViewHolderHistoryIn(final Context pContext, final View pItemView) {
        super(pItemView);
        mContext = pContext;
        mMessageTextView = pItemView.findViewById(R.id.message_history_text_view);
        mTimeTextView = pItemView.findViewById(R.id.time_history_text_view);
        mImageView = pItemView.findViewById(R.id.user_history_image_view);

    }

    void bind(final VkModelMessages pVkModelMessages, final VkModelUser pVkModelUser) {

        mMessageTextView.setText(pVkModelMessages.getBody());
        mTimeTextView.setText(TimesUtils.getTimeHistory(pVkModelMessages.getDate()));

        if (pVkModelUser != null) {
            ImageLoader.with(mContext).load(pVkModelUser.getPhoto50()).into(mImageView);
        } else {
            ImageLoader.with(mContext).load(pVkModelMessages.getVkModelUser().getPhoto50()).into(mImageView);
        }

        if (pVkModelMessages.getFwdMessages() != null) {
            mMessageTextView.setText(Constants.FWD_MESSAGES);
        }
        if (pVkModelMessages.getAttachments() != null) {
            if (!pVkModelMessages.getAttachments().getAttachmentsList().isEmpty()) {
                final String string = Constants.ATTACHMENT + pVkModelMessages.getAttachments().getAttachmentsList().get(0).getType();
                mMessageTextView.setText(string);
            }
        }
    }
}
