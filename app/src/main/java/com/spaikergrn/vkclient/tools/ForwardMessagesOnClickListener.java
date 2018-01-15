package com.spaikergrn.vk_client.tools;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.spaikergrn.vk_client.activity.ForwardMessagesActivity;
import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelMessages;

public class ForwardMessagesOnClickListener implements View.OnClickListener {

    private final VkModelMessages mVkModelMessages;
    private final Context mContext;

    public ForwardMessagesOnClickListener(final VkModelMessages pVkModelMessages, final Context pContext) {
        mContext = pContext;
        mVkModelMessages = pVkModelMessages;
    }

    @Override
    public void onClick(final View pView) {
        mContext.startActivity(new Intent(mContext, ForwardMessagesActivity.class).putExtra(Constants.FORWARD_MESSAGES_INTENT, mVkModelMessages));
    }
}
