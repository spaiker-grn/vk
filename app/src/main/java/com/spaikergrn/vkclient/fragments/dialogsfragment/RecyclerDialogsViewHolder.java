package com.spaikergrn.vkclient.fragments.dialogsfragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.imageloader.ImageLoader;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.serviceclasses.ProfileInfoHolder;
import com.spaikergrn.vkclient.tools.TimesUtils;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelDialogK;

class RecyclerDialogsViewHolder extends RecyclerView.ViewHolder {

    private final TextView mUserIdTextView;
    private final TextView mLastMessageTextView;
    private final TextView mTimeTextView;
    private final TextView mUnreadTextView;
    private final RelativeLayout mLayout;
    private final ImageView mOutImageView;
    private final ImageView mProfileImageView;
    private final Context mContext;

    RecyclerDialogsViewHolder(final Context pContext, final View pItemView) {
        super(pItemView);
        mContext = pContext;
        mLayout = pItemView.findViewById(R.id.dialogs_layout);
        mUserIdTextView = pItemView.findViewById(R.id.user_dialogs_text_view);
        mLastMessageTextView = pItemView.findViewById(R.id.last_message_dialogs_text_view);
        mTimeTextView = pItemView.findViewById(R.id.time_dialogs_text_view);
        mUnreadTextView = pItemView.findViewById(R.id.circle_text_view);
        mOutImageView = pItemView.findViewById(R.id.out_circle_image_view);
        mProfileImageView = pItemView.findViewById(R.id.card_message_profile_image_view);
    }

    void bind(final VkModelDialogK pVkModelDialog) {

        final String emptyString = Constants.Parser.EMPTY_STRING;
        final int colorWhite = mContext.getResources().getColor(R.color.colorWhite);
        final int colorLightGrey = mContext.getResources().getColor(R.color.colorLightGrey);

        if (pVkModelDialog.getMessages().getPhoto50() != null) {      //Check have photo group or users
            ImageLoader.with(mContext, Constants.ImgLoader.IMG_CACHE_FOLDER).
                    load(pVkModelDialog.getMessages().getPhoto50()).into(mProfileImageView);

        } else {
            ImageLoader.with(mContext, Constants.ImgLoader.IMG_CACHE_FOLDER).
                    load(pVkModelDialog.getMessages().getVkModelUser().getPhoto50()).into(mProfileImageView);

        }

        if (pVkModelDialog.getUnread() != 0) {    //Check, show unread_text_view or hide
            mUnreadTextView.setText(String.valueOf(pVkModelDialog.getUnread()));
            mUnreadTextView.setVisibility(View.VISIBLE);
        } else {
            mUnreadTextView.setVisibility(View.INVISIBLE);
        }

        if (!(pVkModelDialog.getMessages().getTitle().equals(emptyString))) { //Check, set Tittle, Full Name or DEACTIVATED
            mUserIdTextView.setText(pVkModelDialog.getMessages().getTitle());
        } else if (pVkModelDialog.getMessages().getVkModelUser() != null) {
            if (pVkModelDialog.getMessages().getVkModelUser().getDeactivated() != null) {
                mUserIdTextView.setText(pVkModelDialog.getMessages().getVkModelUser().getDeactivated().toUpperCase());
            } else {
                mUserIdTextView.setText(pVkModelDialog.getMessages().getVkModelUser().getFullName());
            }
        } else {
            mUserIdTextView.setText(pVkModelDialog.getMessages().getTitle());
        }

        mTimeTextView.setText(TimesUtils.getTimeDialogs(pVkModelDialog.getMessages().getDate()));     // Set Time text_view

        if ((pVkModelDialog.getMessages().getBody()).equals(emptyString)) {   //Check set body, attachment or fwd_messages
            if (pVkModelDialog.getMessages().getAttachments() != null) {
                mLastMessageTextView.setText(Constants.WALL_POST);
            } else {
                if (pVkModelDialog.getMessages().getFwdMessages() != null) {
                    mLastMessageTextView.setText(Constants.FWD_MESSAGES);
                }
            }
        } else {
            mLastMessageTextView.setText(pVkModelDialog.getMessages().getBody());   //Set empty body
        }

        if (pVkModelDialog.getMessages().getReadState()) {      //Check readied message or not and set backgrounds
            mLayout.setBackgroundColor(colorWhite);
            mLastMessageTextView.setBackgroundColor(colorWhite);
        } else {
            if (pVkModelDialog.getMessages().getOut()) {
                mLastMessageTextView.setBackgroundColor(colorLightGrey);
                mLayout.setBackgroundColor(colorWhite);
            } else {
                mLayout.setBackgroundColor(colorLightGrey);
                mLastMessageTextView.setBackgroundColor(colorLightGrey);
            }
        }

        if (pVkModelDialog.getMessages().getOut()) {   //Check set small profile_image or not
            mOutImageView.setVisibility(View.VISIBLE);
            ImageLoader.with(mContext).load(ProfileInfoHolder.getVkModelUser().getPhoto50()).into(mOutImageView);
        } else {
            mOutImageView.setVisibility(View.GONE);
        }
    }
}
