package com.spaikergrn.vkclient.activity.messagehistoryactivity;

import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelMessagesK;

import java.util.List;

public interface MessagesHistoryView {

    void onMessageHistoryLoaded(final List<VkModelMessagesK> pVkModelMessages);

    void showErrorSendMessageToast();

    void onError(final Throwable pThrowable);

    void onLongPollMessageLoaded(final VkModelMessagesK pVkModelMessagesK);
}
