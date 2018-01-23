package com.spaikergrn.vkclient.tools;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.spaikergrn.vkclient.activity.ForwardMessagesActivity;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelMessagesK;

public class ForwardMessagesOnClickListener implements View.OnClickListener {

    private final VkModelMessagesK mVkModelMessages;
    private final Context mContext;

    public ForwardMessagesOnClickListener(final VkModelMessagesK pVkModelMessages, final Context pContext) {
        mContext = pContext;
        mVkModelMessages = pVkModelMessages;
    }

    @Override
    public void onClick(final View pView) {
        mContext.startActivity(new Intent(mContext, ForwardMessagesActivity.class).putExtra(Constants.FORWARD_MESSAGES_INTENT, mVkModelMessages));
    }
}
