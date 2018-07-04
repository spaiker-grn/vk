package com.spaikergrn.vkclient.callablemodels;

import android.util.Log;
import android.util.SparseArray;

import com.spaikergrn.vkclient.clients.HttpUrlClient;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.tools.GetUsersHelper;
import com.spaikergrn.vkclient.tools.ParseUtils;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelChatK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelMessagesK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class CallMessagesHistory implements ObservableOnSubscribe<List<VkModelMessagesK>> {

    private final int mStartMessageId;
    private final int mHistoryId;
    private final int mCount;
    private final GetUsersHelper mGetUsersHelper = new GetUsersHelper();

    CallMessagesHistory(final int pHistoryId, final int pStartMessageId, final int pCount) {
        mStartMessageId = pStartMessageId;
        mHistoryId = pHistoryId;
        mCount = pCount;
    }

    @Override
    public void subscribe(final ObservableEmitter<List<VkModelMessagesK>> emitter) {

        List<VkModelMessagesK> messagesList = new ArrayList<>();

        try {

            messagesList = getVkModelMessages(new HttpUrlClient().getRequestWithErrorCheck(VkApiMethods.getMessageHistory(mHistoryId, mStartMessageId, mCount)));

            if (messagesList.isEmpty()) {
                emitter.onNext(messagesList);
            }

            final LinkedHashSet<Integer> hashSetUsers = getUsersHashSetFromMessagesList(messagesList);

            final SparseArray<VkModelUser> vkModelUserMap = mGetUsersHelper.getUsersById(new ArrayList<>(hashSetUsers));

            setVkModelUsersToMessageList(messagesList, vkModelUserMap);

            initChatHistoryIfExist(messagesList);

        } catch (IOException | JSONException | ExecutionException | InterruptedException pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.getCause());
        }
        emitter.onNext(messagesList);
        emitter.onComplete();
    }

    private List<VkModelMessagesK> getVkModelMessages(final String pResponse) throws JSONException {
        final JSONArray jsonArray = ParseUtils.getJSONArrayItems(pResponse);
        final List<VkModelMessagesK> messagesList = new ArrayList<>();

        if (jsonArray != null) {
            final int messagesCount = new JSONObject(pResponse).getJSONObject(Constants.Parser.RESPONSE).getInt(Constants.URL_BUILDER.COUNT);

            for (int i = 0; i < jsonArray.length(); i++) {
                final VkModelMessagesK vkModelMessages = new VkModelMessagesK(jsonArray.getJSONObject(i));
                vkModelMessages.setCountMessagesHistory(messagesCount);
                messagesList.add(vkModelMessages);
            }

            return messagesList;
        } else {

            return messagesList;
        }
    }

    private LinkedHashSet<Integer> getUsersHashSetFromMessagesList(final Iterable<VkModelMessagesK> pMessagesList) {
        final LinkedHashSet<Integer> hashSetUsers = new LinkedHashSet<>();

        for (final VkModelMessagesK pVkModelMessage : pMessagesList) {
            if (pVkModelMessage.getFromId() != 0) {
                hashSetUsers.add(pVkModelMessage.getFromId());
            } else {
                hashSetUsers.add(pVkModelMessage.getUserId());
            }
        }
        return hashSetUsers;
    }

    private void setVkModelUsersToMessageList(final List<VkModelMessagesK> pMessageList, final SparseArray<VkModelUser> pVkModelUserMap) {

        for (int i = 0; i < pMessageList.size(); i++) {
            final int userId;

            if (pMessageList.get(i).getFromId() != 0) {
                userId = pMessageList.get(i).getFromId();
            } else {
                userId = pMessageList.get(i).getUserId();
            }

            pMessageList.get(i).setVkModelUser(pVkModelUserMap.get(userId));
        }
    }

    private void initChatHistoryIfExist(final List<VkModelMessagesK> pMessageList) throws InterruptedException, ExecutionException, JSONException, IOException {

        if (pMessageList.get(0).getChatId() != 0) {
            final String chatById = new HttpUrlClient().getRequestWithErrorCheck(VkApiMethods.getChatById(pMessageList.get(0).getChatId()));
            final VkModelChatK chat = new VkModelChatK(new JSONObject(chatById).getJSONObject(Constants.Parser.RESPONSE));

            if (chat.getUsers() != null) {
                final SparseArray<VkModelUser> vkModelUserSparseArray = mGetUsersHelper.getUsersById(Arrays.asList(chat.getUsers()));
                for (int i = 0; i < pMessageList.size(); i++) {
                    pMessageList.get(i).setVkModelUser(vkModelUserSparseArray.get(pMessageList.get(i).getUserId()));
                }

            }
        }
    }
}
