package com.spaikergrn.vkclient.activity.messagehistoryactivity;

import com.spaikergrn.vkclient.vkapi.VkApiMethods;

import org.json.JSONObject;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class CallSendMessage implements ObservableOnSubscribe<JSONObject> {

    private final int mChatId;
    private final String mText;

    CallSendMessage(final int pChatId, final String pText) {
        mChatId = pChatId;
        mText = pText;
    }

    @Override
    public void subscribe(final ObservableEmitter<JSONObject> pEmitter) throws Exception {
        final String response = VkApiMethods.sendMessage(mChatId, mText);

        pEmitter.onNext(new JSONObject(response));
        pEmitter.onComplete();
    }
}
