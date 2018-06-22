package com.spaikergrn.vkclient.activity.messagehistoryactivity;

interface MessagesHistoryViewPresenter {

    void getMessages(int pRequestId, int pStartMessageId, int pCount);

    void sendMessage(final int pRequestId, final String pText);

    void getLongPollMessage(final String pTsKey);

    void onPause();
}
