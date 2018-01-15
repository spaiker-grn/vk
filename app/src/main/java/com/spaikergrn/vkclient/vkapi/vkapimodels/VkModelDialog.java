package com.spaikergrn.vkclient.vkapi.vkapimodels;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelDialog implements VkModel {

    private int mDialogsCount;
    private int mUnread;
    private VkModelMessages mMessages;

    public VkModelDialog(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    public VkModelDialog parse(final JSONObject pObject) throws JSONException {

        mUnread = pObject.optInt(Constants.Parser.UNREAD);
        mMessages = new VkModelMessages(pObject.optJSONObject(Constants.Parser.MESSAGE));
        return this;
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


}
