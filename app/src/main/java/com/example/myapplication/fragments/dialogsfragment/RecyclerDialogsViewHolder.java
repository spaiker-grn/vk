package com.example.myapplication.fragments.dialogsfragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.imageloader.ImageLoader;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.TimesUtils;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;

class RecyclerDialogsViewHolder extends RecyclerView.ViewHolder {

    private final DialogsFragment mDialogsFragment;
    private final TextView mUserIdTextView;
    private final TextView mLastMessageTextView;
    private final TextView mTimeTextView;
    private final TextView mUnreadTextView;
    private final RelativeLayout mLayout;
    private final ImageView mOutImageView;
    private final ImageView mProfileImageView;

    RecyclerDialogsViewHolder(final DialogsFragment pDialogsFragment, final View pItemView) {
        super(pItemView);
        mDialogsFragment = pDialogsFragment;
        mLayout = pItemView.findViewById(R.id.dialogs_layout);
        mUserIdTextView = pItemView.findViewById(R.id.user_dialogs_text_view);
        mLastMessageTextView = pItemView.findViewById(R.id.last_message_dialogs_text_view);
        mTimeTextView = pItemView.findViewById(R.id.time_dialogs_text_view);
        mUnreadTextView = pItemView.findViewById(R.id.circle_text_view);
        mOutImageView = pItemView.findViewById(R.id.out_circle_image_view);
        mProfileImageView = pItemView.findViewById(R.id.card_message_profile_image_view);

    }

    void bind(final VkModelDialogs pVkModelDialogs) {

        final String emptyString = Constants.Parser.EMPTY_STRING;
        final int colorWhite = mDialogsFragment.getResources().getColor(R.color.colorWhite);
        final int colorLightGrey = mDialogsFragment.getResources().getColor(R.color.colorLightGrey);

        if (pVkModelDialogs.getMessages().getPhoto50() != null) {      //Check have photo or download
            ImageLoader.with(mDialogsFragment.getContext(), Constants.ImgLoader.IMG_CACHE_FOLDER).
                    load(pVkModelDialogs.getMessages().getPhoto50()).into(mProfileImageView);

        } else {
            ImageLoader.with(mDialogsFragment.getContext(), Constants.ImgLoader.IMG_CACHE_FOLDER).
                    load(pVkModelDialogs.getMessages().getVkModelUser().getPhoto50()).into(mProfileImageView);
        }

        if (pVkModelDialogs.getUnread() != 0) {    //Check, show unread_text_view or hide
            mUnreadTextView.setText(String.valueOf(pVkModelDialogs.getUnread()));
            mUnreadTextView.setVisibility(View.VISIBLE);
        } else {
            mUnreadTextView.setVisibility(View.INVISIBLE);
        }

        if (!(pVkModelDialogs.getMessages().getTitle().equals(emptyString))) { //Check, set Tittle, Full Name or DEACTIVATED
            mUserIdTextView.setText(pVkModelDialogs.getMessages().getTitle());
        } else {
            if (pVkModelDialogs.getMessages().getVkModelUser() != null) {
                if (pVkModelDialogs.getMessages().getVkModelUser().getDeactivated() != null) {
                    mUserIdTextView.setText(pVkModelDialogs.getMessages().getVkModelUser().getDeactivated().toUpperCase());
                } else {
                    mUserIdTextView.setText(pVkModelDialogs.getMessages().getVkModelUser().getFullName());
                }
            } else {
                mUserIdTextView.setText(pVkModelDialogs.getMessages().getTitle());
            }
        }

        mTimeTextView.setText(TimesUtils.getTimeDialogs(pVkModelDialogs.getMessages().getDate()));     // Set Time text_view

        if ((pVkModelDialogs.getMessages().getBody()).equals(emptyString)) {   //Check set body, attachment or fwd_messages
            if (pVkModelDialogs.getMessages().getAttachments() != null) {
                mLastMessageTextView.setText(Constants.WALL_POST);
            } else {
                if (pVkModelDialogs.getMessages().getFwdMessages() != null) {
                    mLastMessageTextView.setText(Constants.FWD_MESSAGES);
                }
            }
        } else {
            mLastMessageTextView.setText(pVkModelDialogs.getMessages().getBody());   //Set empty body
        }

        if (pVkModelDialogs.getMessages().isReadState()) {      //Check readied message or not and set backgrounds
            mLayout.setBackgroundColor(colorWhite);
            mLastMessageTextView.setBackgroundColor(colorWhite);
        } else {
            if (pVkModelDialogs.getMessages().isOut()) {
                mLastMessageTextView.setBackgroundColor(colorLightGrey);
                mLayout.setBackgroundColor(colorWhite);
            } else {
                mLayout.setBackgroundColor(colorLightGrey);
                mLastMessageTextView.setBackgroundColor(colorLightGrey);
            }
        }

        if (pVkModelDialogs.getMessages().isOut()) {   //Check set small profile_image or not
            mOutImageView.setVisibility(View.VISIBLE);

        } else {
            mOutImageView.setVisibility(View.GONE);
        }

    }

}
