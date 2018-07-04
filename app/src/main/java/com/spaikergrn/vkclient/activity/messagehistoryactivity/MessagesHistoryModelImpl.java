package com.spaikergrn.vkclient.activity.messagehistoryactivity;

import com.spaikergrn.vkclient.activity.messagehistoryactivity.messageshistorycontract.MessagesHistoryModel;
import com.spaikergrn.vkclient.callablemodels.CallLongPollMessage;
import com.spaikergrn.vkclient.callablemodels.CallMessagesHistory;
import com.spaikergrn.vkclient.callablemodels.CallSendMessage;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelMessagesK;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MessagesHistoryModelImpl implements MessagesHistoryModel {

    @Override
    public void getVkModelMessages(final int pStartMessageId, final int pHistoryId, final int pCount, final DisposableObserver<List<VkModelMessagesK>> pDisposableObserver) {

        Observable.create(new CallMessagesHistory(pHistoryId, pStartMessageId, pCount))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pDisposableObserver);
    }

    @Override
    public void sendMessage(final int pChatId, final String pText, final DisposableObserver<JSONObject> pSendMessageDisposableObserver) {

        Observable.create(new CallSendMessage(pChatId, pText))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pSendMessageDisposableObserver);
    }

    @Override
    public void getLongPollMessage(final String pTsKey, final DisposableObserver<VkModelMessagesK> pVkModelMessagesDisposableObserver) {

        Observable.create(new CallLongPollMessage(pTsKey))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pVkModelMessagesDisposableObserver);
    }
}