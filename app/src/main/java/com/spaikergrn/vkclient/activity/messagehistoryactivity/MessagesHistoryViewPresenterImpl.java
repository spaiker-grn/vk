package com.spaikergrn.vkclient.activity.messagehistoryactivity;

import com.spaikergrn.vkclient.activity.messagehistoryactivity.messageshistorycontract.MessagesHistoryModel;
import com.spaikergrn.vkclient.activity.messagehistoryactivity.messageshistorycontract.MessagesHistoryView;
import com.spaikergrn.vkclient.activity.messagehistoryactivity.messageshistorycontract.MessagesHistoryViewPresenter;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelMessagesK;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

class MessagesHistoryViewPresenterImpl implements MessagesHistoryViewPresenter {

    private final MessagesHistoryView mView;
    private final MessagesHistoryModel mMessagesHistoryModel;
    private VkMessagesDisposableObserver mVkMessagesDisposableObserver;
    private boolean mIsLoading;

    private final DisposableObserver<JSONObject> mSendMessageDisposableObserver = new DisposableObserver<JSONObject>() {

        @Override
        public void onNext(final JSONObject pResponse) {
            if (!pResponse.has(Constants.Parser.RESPONSE)) {
                mView.showErrorSendMessageToast();
            }
        }

        @Override
        public void onError(final Throwable pThrowable) {
            mView.onError(pThrowable);
        }

        @Override
        public void onComplete() {

        }
    };

    private final DisposableObserver<VkModelMessagesK> mLongPollMessagesDisposableObserver = new DisposableObserver<VkModelMessagesK>() {

        @Override
        public void onNext(final VkModelMessagesK pVkModelMessagesK) {
            mView.onLongPollMessageLoaded(pVkModelMessagesK);
        }

        @Override
        public void onError(final Throwable pThrowable) {
            mView.onError(pThrowable);
        }

        @Override
        public void onComplete() {

        }
    };

    MessagesHistoryViewPresenterImpl(final MessagesHistoryView pMessagesHistoryView) {
        mView = pMessagesHistoryView;
        mMessagesHistoryModel = new MessagesHistoryModelImpl();
    }

    @Override
    public void getMessages(final int pRequestId, final int pStartMessageId, final int pCount) {

        if (mVkMessagesDisposableObserver != null) {
            mVkMessagesDisposableObserver.dispose();
        }

        if (!mIsLoading) {
            mVkMessagesDisposableObserver = new VkMessagesDisposableObserver();
            mMessagesHistoryModel.getVkModelMessages(pStartMessageId, pRequestId, pCount, mVkMessagesDisposableObserver);
        }
    }

    @Override
    public void onPause() {

        if (mVkMessagesDisposableObserver != null) {
            mVkMessagesDisposableObserver.dispose();
        }

        mSendMessageDisposableObserver.dispose();
        mLongPollMessagesDisposableObserver.dispose();
    }

    @Override
    public void sendMessage(final int pChatId, final String pText) {
        mMessagesHistoryModel.sendMessage(pChatId, pText, mSendMessageDisposableObserver);
    }

    @Override
    public void getLongPollMessage(final String pTsKey) {
        mMessagesHistoryModel.getLongPollMessage(pTsKey, mLongPollMessagesDisposableObserver);
    }



    private class VkMessagesDisposableObserver extends DisposableObserver<List<VkModelMessagesK>> {

        @Override
        public void onNext(final List<VkModelMessagesK> pVkModelMessagesKS) {
            mView.onMessageHistoryLoaded(pVkModelMessagesKS);
            mIsLoading = false;
        }

        @Override
        public void onError(final Throwable pThrowable) {
            mView.onError(pThrowable);
            mIsLoading = false;
        }

        @Override
        public void onComplete() {

        }
    }

}
