package com.spaikergrn.vkclient.activity.messagehistoryactivity;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
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
import com.spaikergrn.vkclient.serviceclasses.ProfileInfoHolder;
import com.spaikergrn.vkclient.tools.ForwardMessagesOnClickListener;
import com.spaikergrn.vkclient.tools.TimesUtils;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelMessages;

class RecyclerViewHolderHistory extends RecyclerView.ViewHolder {

    private final Context mContext;
    private final TextView mMessageTextView;
    private final TextView mTimeTextView;
    private final ImageView mImageView;
    private final LayoutInflater mInflater;
    private final int mWidth;
    private final LinearLayout mAttachmentLayout;

    RecyclerViewHolderHistory(final Context pContext, final View pItemView) {
        super(pItemView);
        mContext = pContext;
        mMessageTextView = pItemView.findViewById(R.id.message_history_text_view);
        mTimeTextView = pItemView.findViewById(R.id.time_history_text_view);
        mImageView = pItemView.findViewById(R.id.user_history_image_view);
        mAttachmentLayout = pItemView.findViewById(R.id.attachment_message_history);
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mWidth = getWidthCardView(mContext);
    }

    void bind(final VkModelMessages pVkModelMessages) {

        final AttachmentsFill attachmentsFill = new AttachmentsFill(mContext);
        mAttachmentLayout.removeAllViews();
        mMessageTextView.setText(Constants.Parser.EMPTY_STRING);

        if (pVkModelMessages.getBody().equals(Constants.Parser.EMPTY_STRING)){  //set body to messages text view
            mMessageTextView.setVisibility(View.GONE);
        }else {
            mMessageTextView.setVisibility(View.VISIBLE);
            mMessageTextView.setText(pVkModelMessages.getBody());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mMessageTextView.setTextAppearance(R.style.news_text);
            } else {
                mMessageTextView.setTextAppearance(mContext, R.style.news_text);
            }
            mMessageTextView.setOnClickListener(null);
            mMessageTextView.setPaintFlags(0);
        }

        mTimeTextView.setText(TimesUtils.getTimeHistory(pVkModelMessages.getDate()));   //set time

        if (pVkModelMessages.getVkModelUser() != null && !pVkModelMessages.isOut()) {
            ImageLoader.with(mContext).load(pVkModelMessages.getVkModelUser().getPhoto50()).into(mImageView);
        } else  if (pVkModelMessages.isOut()){
            ImageLoader.with(mContext).load(ProfileInfoHolder.getVkModelUser().getPhoto50()).into(mImageView);
        }

        if (pVkModelMessages.getFwdMessages() != null) {      // set forward message to message text view
            if (!mMessageTextView.getText().equals(Constants.Parser.EMPTY_STRING)){
                mMessageTextView.append("\n");
            }
            mMessageTextView.append(Constants.FWD_MESSAGES);
            mMessageTextView.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mMessageTextView.setTextAppearance(R.style.text_link);
            } else {
                mMessageTextView.setTextAppearance(mContext, R.style.text_link);
            }
            mMessageTextView.setPaintFlags(mMessageTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            mMessageTextView.setOnClickListener(new ForwardMessagesOnClickListener(pVkModelMessages, mContext));
        }

        if (pVkModelMessages.getAttachments() != null && pVkModelMessages.getAttachments().getAttachmentsList().size() >= 1) {
            attachmentsFill.inflateAttachments(pVkModelMessages.getAttachments(), mAttachmentLayout, mWidth, mInflater, mContext, 3);  //fill attachments
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
