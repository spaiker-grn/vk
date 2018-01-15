package com.spaikergrn.vkclient.vkapi.vkapimodels;

import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.tools.ParseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VkModelMessages implements Serializable, VkModel {

    private int mId;
    private int mUserId;
    private int mFromId;
    private long mDate;
    private boolean mReadState;
    private boolean mOut;
    private String mTitle;
    private String mBody;
    private VkAttachments mAttachments;
    private List<VkModelMessages> mFwdMessages;
    private String mPhoto50;
    private int mCountMessagesHistory;
    private int mChatId;
    private VkModelUser mVkModelUser;

    public VkModelMessages(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    public VkModelMessages parse(final JSONObject pObject) throws JSONException {
        mId = pObject.optInt(Constants.Parser.ID);
        mFromId = pObject.optInt(Constants.Parser.FROM_ID);
        mUserId = pObject.optInt(Constants.Parser.USER_ID);
        mDate = pObject.optLong(Constants.Parser.DATE);
        mReadState = ParseUtils.parseBoolean(pObject, Constants.Parser.READ_STATE);
        mOut = ParseUtils.parseBoolean(pObject, Constants.Parser.OUT);
        mTitle = pObject.optString(Constants.Parser.TITLE);
        mBody = pObject.optString(Constants.Parser.BODY);

        if (pObject.has(Constants.Parser.ATTACHMENTS)) {
            mAttachments = new VkAttachments(pObject.getJSONArray(Constants.Parser.ATTACHMENTS));
        }
        if (pObject.has(Constants.Parser.FWD_MESSAGE)) {
            mFwdMessages = new ArrayList<>();
            final JSONArray jsonArray = pObject.getJSONArray(Constants.Parser.FWD_MESSAGE);
            for (int i = 0; i < jsonArray.length(); i++) {
                mFwdMessages.add(new VkModelMessages(jsonArray.getJSONObject(i)));
            }
        }

        if (pObject.has(Constants.Parser.PHOTO_50)) {
            mPhoto50 = pObject.optString(Constants.Parser.PHOTO_50);
        }
        if (pObject.has(Constants.Parser.CHAT_ID)) {
            mChatId = pObject.optInt(Constants.Parser.CHAT_ID);
        }
        return this;
    }

    public VkModelUser getVkModelUser() {
        return mVkModelUser;
    }

    public void setVkModelUser(final VkModelUser pVkModelUser) {
        mVkModelUser = pVkModelUser;
    }

    public VkAttachments getAttachments() {
        return mAttachments;
    }

    public List<VkModelMessages> getFwdMessages() {
        return mFwdMessages;
    }

    public void setAttachments(final VkAttachments pAttachments) {
        mAttachments = pAttachments;
    }

    public void setFwdMessages(final List<VkModelMessages> pFwdMessages) {
        mFwdMessages = pFwdMessages;
    }

    public int getCountMessagesHistory() {
        return mCountMessagesHistory;
    }

    public void setCountMessagesHistory(final int pCountMessagesHistory) {
        mCountMessagesHistory = pCountMessagesHistory;
    }

    public int getChatId() {
        return mChatId;
    }

    public void setChatId(final int pChatId) {
        mChatId = pChatId;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public String getPhoto50() {
        return mPhoto50;
    }

    public void setPhoto50(final String pPhoto50) {
        mPhoto50 = pPhoto50;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(final int pUserId) {
        mUserId = pUserId;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(final long pDate) {
        mDate = pDate;
    }

    public boolean isReadState() {
        return mReadState;
    }

    public void setReadState(final boolean pReadState) {
        mReadState = pReadState;
    }

    public boolean isOut() {
        return mOut;
    }

    public void setOut(final boolean pOut) {
        mOut = pOut;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String pTitle) {
        mTitle = pTitle;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(final String pBody) {
        mBody = pBody;
    }

    public int getFromId() {
        return mFromId;
    }

    public void setFromId(final int pFromId) {
        mFromId = pFromId;
    }
}
