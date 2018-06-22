package com.spaikergrn.vkclient.activity.messagehistoryactivity;

import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelMessagesK;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface MessagesHistoryModel {

    void getVkModelMessages(final int pStartMessageId, final int pHistoryId, final int pCount, DisposableObserver<List<VkModelMessagesK>> pDisposableObserver);

    void sendMessage(final int pChatId, final String pText, final DisposableObserver<JSONObject> pSendMessageDisposableObserver);

    void getLongPollMessage(final String pTsKey, final DisposableObserver<VkModelMessagesK> pVkModelMessagesDisposableObserver);

}
