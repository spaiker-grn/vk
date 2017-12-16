package com.example.myapplication.vkapi.vkapimodels;

import android.util.SparseArray;

import com.example.myapplication.serviceclasses.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VkModelLongPollHistory extends VkModel {

    private List<VkModelMessages> mMessagesList;
    private SparseArray<VkModelUser> mVkModelUser;

    VkModelLongPollHistory(final JSONObject pJSONObject) throws JSONException {
        parse(pJSONObject);
    }

    @Override
    public VkModel parse(final JSONObject pJSONObject) throws JSONException {

        final JSONObject jsonResponse = pJSONObject.getJSONObject(Constants.Parser.RESPONSE);

        final JSONArray jsonArrayUsers = jsonResponse.getJSONArray(Constants.Parser.PROFILES);
        for (int i = 0; i < jsonArrayUsers.length(); i++) {
            final VkModelUser vkModelUser = new VkModelUser(jsonArrayUsers.getJSONObject(i));
            mVkModelUser.put(vkModelUser.getId(), vkModelUser);
        }

        final JSONArray jsonArrayItems = jsonResponse.getJSONArray(Constants.Parser.ITEMS);
        for (int i = 0; i < jsonArrayItems.length(); i++) {
            final VkModelMessages vkModelMessages = new VkModelMessages(jsonArrayItems.getJSONObject(i));
            vkModelMessages.setVkModelUser(mVkModelUser.get(vkModelMessages.getUserId()));
            mMessagesList.add(vkModelMessages);

        }
        return this;
    }

    public List<VkModelMessages> getMessagesList() {
        return mMessagesList;
    }

    public void setMessagesList(final List<VkModelMessages> pMessagesList) {
        mMessagesList = pMessagesList;
    }

    public SparseArray<VkModelUser> getVkModelUser() {
        return mVkModelUser;
    }

    public void setVkModelUser(final SparseArray<VkModelUser> pVkModelUser) {
        mVkModelUser = pVkModelUser;
    }
}
