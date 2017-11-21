package com.example.myapplication.fragments.DialogsFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

class RecyclerViewHolderDialogs extends RecyclerView.ViewHolder {

    private static final int END_INDEX = 70;
    private final DialogsFragment mDialogsFragment;
    private final TextView mUserId;
    private final TextView mLastMessage;
    private final TextView mTime;
    private final TextView mUnread;
    private final RelativeLayout mLayout;
    private final ImageView mOut;

    RecyclerViewHolderDialogs(final DialogsFragment pDialogsFragment, final View itemView) {
        super(itemView);
        mDialogsFragment = pDialogsFragment;
        mLayout = itemView.findViewById(R.id.dialogs_layout);
        mUserId = itemView.findViewById(R.id.user_dialogs_text_view);
        mLastMessage = itemView.findViewById(R.id.last_message_dialogs_text_view);
        mTime = itemView.findViewById(R.id.time_dialogs_text_view);
        mUnread = itemView.findViewById(R.id.circle_text_view);
        mOut = itemView.findViewById(R.id.out_circle_image_view);

    }


    void bind(final VkModelDialogs pVkModelDialogs) {


        if (pVkModelDialogs.unread != 0) {
            mUnread.setText(String.valueOf(pVkModelDialogs.unread));
            mUnread.setVisibility(View.VISIBLE);
        } else {
            mUnread.setVisibility(View.INVISIBLE);
        }

        if ("".equals(pVkModelDialogs.message.title)) {
            if(pVkModelDialogs.user !=null){mUserId.setText(pVkModelDialogs.user.getFullName());}

        } else {
            mUserId.setText(pVkModelDialogs.message.title);
        }

        mTime.setText(getTime(pVkModelDialogs.message.date));

        if ("".equals(pVkModelDialogs.message.body)) {
            if (pVkModelDialogs.message.attachments != null) {
                mLastMessage.setText(Constants.WALL_POST);
            } else {
                mLastMessage.setText(Constants.FWD_MESSAGE);
            }
        } else {
            mLastMessage.setText(subString(pVkModelDialogs.message.body, END_INDEX));
        }

        if (!pVkModelDialogs.message.out && !pVkModelDialogs.message.read_state) {
            mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorLightGrey));
        } else {
            mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
        }

        if (!pVkModelDialogs.message.read_state) {
            if (pVkModelDialogs.message.out) {
                mLastMessage.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorLightGrey));
                mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
            } else {
                mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorLightGrey));
                mLastMessage.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorLightGrey));
            }



        } else {
            mLayout.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
            mLastMessage.setBackgroundColor(mDialogsFragment.getResources().getColor(R.color.colorWhite));
        }

        if (pVkModelDialogs.message.out) {
            mOut.setVisibility(View.VISIBLE);
        } else {
            mOut.setVisibility(View.GONE);
        }

    }

    String getTime(final long date) {
        final Locale locale = new Locale("ru");
        final DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
        final String[] shortMonths = {
                "янв.", "фев.", "март", "апр.", "май", "июн.",
                "июл.", "авг.", "сен.", "окт.", "нояб.", "дек."};
        dfs.setMonths(shortMonths);
        final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM", locale);
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
        sdf.setDateFormatSymbols(dfs);
        return sdf.format(date * 1000);

    }

    String subString(final String pS, final int pLength) {
        if (pS != null && pS.length() > pLength) {
            return pS.substring(0, pLength) + "...";
        } else {
            return pS;
        }

    }

}
