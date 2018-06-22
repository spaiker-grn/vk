package com.spaikergrn.vkclient.activity.messagehistoryactivity;

import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelMessagesK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class CallLongPollMessage implements ObservableOnSubscribe<VkModelMessagesK> {

    private final String mTsKey;

    CallLongPollMessage(final String pTsKey) {
        mTsKey = pTsKey;
    }

    @Override
    public void subscribe(final ObservableEmitter<VkModelMessagesK> emitter) throws JSONException, InterruptedException, ExecutionException, IOException {
        final VkModelMessagesK vkModelMessages;
        final JSONObject jsonObject = new JSONObject(VkApiMethods.getLongPollHistory(mTsKey));
        vkModelMessages = new VkModelMessagesK(jsonObject.getJSONObject(Constants.Parser.RESPONSE).
                getJSONObject(Constants.Parser.MESSAGES).getJSONArray(Constants.Parser.ITEMS).getJSONObject(0));
        vkModelMessages.setVkModelUser(new VkModelUser(jsonObject.getJSONObject(Constants.Parser.RESPONSE).
                getJSONArray(Constants.Parser.PROFILES).getJSONObject(0)));

        emitter.onNext(vkModelMessages);
    }
}
