package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.ParseUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VkModelMessages extends VkModel {

    private int mId;
    private int mUserId;
    private long mDate;
    private boolean mReadState;
    private boolean mOut;
    private String mTitle;
    private String mBody;
    private String mAttachments;
    private String mFwdMessages;
    private String mPhoto50;

    private VkAttachments attachments;

    private List<VkModelMessages> fwd_messages;
    VkModelMessages(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    public VkModelMessages parse(final JSONObject pObject) throws JSONException {
        mId = pObject.optInt(Constants.Parser.ID);
        mUserId = pObject.optInt(Constants.Parser.USER_ID);
        mDate = pObject.optLong(Constants.Parser.DATE);
        mReadState = ParseUtils.parseBoolean(pObject, Constants.Parser.READ_STATE);
        mOut = ParseUtils.parseBoolean(pObject, Constants.Parser.OUT);
        mTitle = pObject.optString(Constants.Parser.TITLE);
        mBody = pObject.optString(Constants.Parser.BODY);
        if(Constants.Parser.EMPTY_STRING.equals(mBody)) {
            if (pObject.has(Constants.Parser.ATTACHMENTS)) {
                mAttachments = (pObject.optJSONArray(Constants.Parser.ATTACHMENTS).getJSONObject(0).optString(Constants.Parser.TYPE));
            }
            if (pObject.has(Constants.Parser.FWD_MESSAGE)) {
                mFwdMessages = (pObject.optJSONArray(Constants.Parser.FWD_MESSAGE).getJSONObject(0).optString(Constants.Parser.BODY));
            }
        }
        if (pObject.has(Constants.Parser.PHOTO_50)){
            mPhoto50 = pObject.optString(Constants.Parser.PHOTO_50);
        }

        return this;
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

    public String getAttachments() {
        return mAttachments;
    }

    public void setAttachments(final VkAttachments pAttachments) {
        attachments = pAttachments;
    }

    public List<VkModelMessages> getFwd_messages() {
        return fwd_messages;
    }

    public void setFwd_messages(final List<VkModelMessages> pFwd_messages) {
        fwd_messages = pFwd_messages;
    }

    public void setAttachments(final String pAttachments) {
        mAttachments = pAttachments;
    }

    public String getFwdMessages() {
        return mFwdMessages;
    }

    public void setFwdMessages(final String pFwdMessages) {
        mFwdMessages = pFwdMessages;
    }

}
