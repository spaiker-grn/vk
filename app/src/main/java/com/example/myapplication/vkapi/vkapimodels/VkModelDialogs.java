package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.serviceclasses.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VkModelDialogs extends VkModel {

    private int mDialogsCount;
    private int mUnread;
    private VkModelMessages mMessages;
    private VkModelUser mUser;

    public VkModelDialogs(final JSONObject pObject) throws JSONException {
        parse(pObject);

    }

    public VkModelDialogs parse(final JSONObject pObject) throws JSONException {

        mUnread = pObject.optInt(Constants.Parser.UNREAD);
        mMessages = new VkModelMessages(pObject.optJSONObject(Constants.Parser.MESSAGE));
        return this;
    }

    public static List<VkModelDialogs> initFromJsomArray(final JSONArray pJSONArray) throws JSONException {
        final List<VkModelDialogs> vkModelDialogsList = new ArrayList<>();
        for (int i = 0; i < pJSONArray.length(); i++) {
            vkModelDialogsList.add(new VkModelDialogs(pJSONArray.getJSONObject(i)));
        }
        return vkModelDialogsList;
    }

    public int getDialogsCount() {
        return mDialogsCount;
    }

    public void setDialogsCount(final int pDialogsCount) {
        mDialogsCount = pDialogsCount;
    }

    public int getUnread() {
        return mUnread;
    }

    public void setUnread(final int pUnread) {
        mUnread = pUnread;
    }

    public VkModelMessages getMessages() {
        return mMessages;
    }

    public void setMessages(final VkModelMessages pMessages) {
        mMessages = pMessages;
    }

    public VkModelUser getUser() {
        return mUser;
    }

    public void setUser(final VkModelUser pUser) {
        mUser = pUser;
    }

}
