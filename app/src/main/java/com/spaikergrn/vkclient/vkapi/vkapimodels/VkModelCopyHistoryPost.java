package com.spaikergrn.vk_client.vkapi.vkapimodels;

import com.spaikergrn.vk_client.serviceclasses.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelCopyHistoryPost extends VkModel {

    private int mId;
    private int mOwnerId;
    private int mFromId;
    private long mDate;
    private String mText;
    private String mType;
    private VkAttachments mVkAttachments;

    public VkModelCopyHistoryPost(final JSONObject pJSONObject) throws JSONException {
        parse(pJSONObject);
    }

    @Override
    public VkModel parse(final JSONObject pJSONObject) throws JSONException {

        mId = pJSONObject.optInt(Constants.Parser.ID);
        mOwnerId = pJSONObject.optInt(Constants.Parser.OWNER_ID);
        mFromId = pJSONObject.optInt(Constants.Parser.FROM_ID);
        mDate = pJSONObject.optLong(Constants.Parser.DATE);
        mType = pJSONObject.optString(Constants.Parser.POST_TYPE);
        mText = pJSONObject.optString(Constants.Parser.TEXT);
        if (pJSONObject.has(Constants.Parser.ATTACHMENTS)) {
            mVkAttachments = new VkAttachments(pJSONObject.getJSONArray(Constants.Parser.ATTACHMENTS));
        }

        return this;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public int getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(final int pOwnerId) {
        mOwnerId = pOwnerId;
    }

    public int getFromId() {
        return mFromId;
    }

    public void setFromId(final int pFromId) {
        mFromId = pFromId;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(final long pDate) {
        mDate = pDate;
    }

    public String getText() {
        return mText;
    }

    public void setText(final String pText) {
        mText = pText;
    }

    public String getType() {
        return mType;
    }

    public void setType(final String pType) {
        mType = pType;
    }

    public VkAttachments getVkAttachments() {
        return mVkAttachments;
    }

    public void setVkAttachments(final VkAttachments pVkAttachments) {
        mVkAttachments = pVkAttachments;
    }
}
