package com.example.myapplication.fragments.dialogsfragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.TimesUtils;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;
import com.squareup.picasso.Picasso;

class RecyclerDialogsViewHolder extends RecyclerView.ViewHolder {

    private final DialogsFragment mDialogsFragment;
    private final TextView mUserIdTextView;
    private final TextView mLastMessageTextView;
    private final TextView mTimeTextView;
    private final TextView mUnreadTextView;
    private final RelativeLayout mLayout;
    private final ImageView mOutImageView;
    private final ImageView mProfileImageView;

    RecyclerDialogsViewHolder(final DialogsFragment pDialogsFragment, final View itemView) {
        super(itemView);
        mDialogsFragment = pDialogsFragment;
        mLayout = itemView.findViewById(R.id.dialogs_layout);
        mUserIdTextView = itemView.findViewById(R.id.user_dialogs_text_view);
        mLastMessageTextView = itemView.findViewById(R.id.last_message_dialogs_text_view);
        mTimeTextView = itemView.findViewById(R.id.time_dialogs_text_view);
        mUnreadTextView = itemView.findViewById(R.id.circle_text_view);
        mOutImageView = itemView.findViewById(R.id.out_circle_image_view);
        mProfileImageView = itemView.findViewById(R.id.card_message_profile_image_view);

    }

    void bindReader(final VkModelDialogs pVkModelDialogs) {
        mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
        mLastMessageTextView.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
    }

    void bindUnReaded(final VkModelDialogs pVkModelDialogs) {
        if (pVkModelDialogs.getMessages().isOut()) {
            mLastMessageTextView.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorLightGrey));
            mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
        } else {
            mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorLightGrey));
            mLastMessageTextView.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
        }

    }

    void newBind(final VkModelDialogs pVkModelDialogs) {

        if (pVkModelDialogs.getUnread() != 0) {
            mUnreadTextView.setText(String.valueOf(pVkModelDialogs.getUnread()));
            mUnreadTextView.setVisibility(View.VISIBLE);
        } else {
            mUnreadTextView.setVisibility(View.INVISIBLE);
        }

        if (pVkModelDialogs.getMessages().getPhoto50() != null) {
            Picasso.with(mDialogsFragment.getContext())
                    .load(pVkModelDialogs.getMessages().getPhoto50())
                    .into(mProfileImageView);
        } else {
            Picasso.with(mDialogsFragment.getContext())
                    .load(pVkModelDialogs.getUser().getPhoto50())
                    .into(mProfileImageView);
        }

        if (pVkModelDialogs.getUser() != null) {
            if (pVkModelDialogs.getUser().getDeactivated() != null) {
                mUserIdTextView.setText(pVkModelDialogs.getUser().getDeactivated().toUpperCase());
            } else {
                mUserIdTextView.setText(pVkModelDialogs.getUser().getFullName());
            }

        } else {
            mUserIdTextView.setText(pVkModelDialogs.getMessages().getTitle());
        }

        if ((pVkModelDialogs.getMessages().getBody()).equals(Constants.Parser.EMPTY_STRING)) {
            if (pVkModelDialogs.getMessages().getAttachments() != null) {
                mLastMessageTextView.setText(Constants.WALL_POST);
            } else {
                if (pVkModelDialogs.getMessages().getFwdMessages() != null) {
                    mLastMessageTextView.setText(Constants.FWD_MESSAGE);
                }
            }
        } else {
            mLastMessageTextView.setText(pVkModelDialogs.getMessages().getBody());
        }

        if (pVkModelDialogs.getMessages().isOut()) {
            mOutImageView.setVisibility(View.VISIBLE);

        } else {
            mOutImageView.setVisibility(View.GONE);
        }

        mTimeTextView.setText(TimesUtils.getTime(pVkModelDialogs.getMessages().getDate()));
    }

    void bind(final VkModelDialogs pVkModelDialogs) {

        if (pVkModelDialogs.getMessages().getPhoto50() != null) {
            Picasso.with(mDialogsFragment.getContext())
                    .load(pVkModelDialogs.getMessages().getPhoto50())
                    .into(mProfileImageView);
        } else {
            Picasso.with(mDialogsFragment.getContext())
                    .load(pVkModelDialogs.getUser().getPhoto50())
                    .into(mProfileImageView);
        }

        if (pVkModelDialogs.getUnread() != 0) {
            mUnreadTextView.setText(String.valueOf(pVkModelDialogs.getUnread()));
            mUnreadTextView.setVisibility(View.VISIBLE);
        } else {
            mUnreadTextView.setVisibility(View.INVISIBLE);
        }

        if (!(pVkModelDialogs.getMessages().getTitle().equals(Constants.Parser.EMPTY_STRING))) {
            mUserIdTextView.setText(pVkModelDialogs.getMessages().getTitle());
        } else {
            if (pVkModelDialogs.getUser() != null) {
                if (pVkModelDialogs.getUser().getDeactivated() != null) {
                    mUserIdTextView.setText(pVkModelDialogs.getUser().getDeactivated().toUpperCase());
                } else {
                    mUserIdTextView.setText(pVkModelDialogs.getUser().getFullName());
                }
            } else {
                mUserIdTextView.setText(pVkModelDialogs.getMessages().getTitle());
            }
        }

        mTimeTextView.setText(TimesUtils.getTime(pVkModelDialogs.getMessages().getDate()));

        if ((pVkModelDialogs.getMessages().getBody()).equals(Constants.Parser.EMPTY_STRING)) {
            if (pVkModelDialogs.getMessages().getAttachments() != null) {
                mLastMessageTextView.setText(Constants.WALL_POST);
            } else {
                mLastMessageTextView.setText(Constants.FWD_MESSAGE);
            }
        } else {
            mLastMessageTextView.setText(pVkModelDialogs.getMessages().getBody());
        }

        if (pVkModelDialogs.getMessages().isReadState()) {
            mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
            mLastMessageTextView.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
        } else {
            if (pVkModelDialogs.getMessages().isOut()) {
                mLastMessageTextView.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorLightGrey));
                mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
            } else {
                mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorLightGrey));
                mLastMessageTextView.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorLightGrey));
            }
        }

        if (pVkModelDialogs.getMessages().isOut()) {
            mOutImageView.setVisibility(View.VISIBLE);

        } else {
            mOutImageView.setVisibility(View.GONE);
        }

    }

}
