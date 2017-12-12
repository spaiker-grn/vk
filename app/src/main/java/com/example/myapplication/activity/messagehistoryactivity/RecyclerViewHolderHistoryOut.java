package com.example.myapplication.activity.messagehistoryactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.TimesUtils;
import com.example.myapplication.vkapi.vkapimodels.VkModelMessages;

class RecyclerViewHolderHistoryOut extends RecyclerView.ViewHolder {

    private final Context mContext;
    private final TextView mMessageTextView;
    private final TextView mTimeTextView;
    private final ImageView mImageView;

    RecyclerViewHolderHistoryOut(final Context pContext, final View pItemView) {
        super(pItemView);
        mContext = pContext;
        mMessageTextView = pItemView.findViewById(R.id.message_history_text_view);
        mTimeTextView = pItemView.findViewById(R.id.time_history_text_view);
        mImageView = pItemView.findViewById(R.id.user_history_image_view);
    }

    void bind(final VkModelMessages pVkModelMessages) {

        mMessageTextView.setText(pVkModelMessages.getBody());
        mTimeTextView.setText(TimesUtils.getTimeHistory(pVkModelMessages.getDate()));
        mImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.avatar));
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
