package com.spaikergrn.vk_client.vkapi.vkapimodels;

import com.spaikergrn.vk_client.serviceclasses.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelDocument extends VkAttachments.VkModelAttachments {

    private int mId;

    private int mOwnerId;
    private String mTitle;
    private long mSize;
    private String mExt;
    private String mUrl;
    private String mPhoto100;
    private String mPhoto130;
    private String mAccessKey;

    public VkModelDocument() {
    }

    public VkModelDocument(final JSONObject pObject) throws JSONException
    {
        parse(pObject);
    }

    public VkModelDocument parse(final JSONObject pObject) {
        mId = pObject.optInt(Constants.Parser.ID);
        mOwnerId = pObject.optInt(Constants.Parser.OWNER_ID);
        mTitle = pObject.optString(Constants.Parser.TITLE);
        mSize = pObject.optLong(Constants.Parser.SIZE);
        mExt = pObject.optString(Constants.Parser.EXT);
        mUrl = pObject.optString(Constants.Parser.URL);
        mAccessKey = pObject.optString(Constants.Parser.ACCESS_KEY);
        mPhoto100 = pObject.optString(Constants.Parser.PHOTO_100);
        mPhoto130 = pObject.optString(Constants.Parser.PHOTO_130);
        return this;
    }

    @Override
    public String getType() {
        return Constants.Parser.TYPE_DOC;
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String pTitle) {
        mTitle = pTitle;
    }

    public long getSize() {
        return mSize;
    }

    public void setSize(final long pSize) {
        mSize = pSize;
    }

    public String getExt() {
        return mExt;
    }

    public void setExt(final String pExt) {
        mExt = pExt;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(final String pUrl) {
        mUrl = pUrl;
    }

    public String getPhoto100() {
        return mPhoto100;
    }

    public void setPhoto100(final String pPhoto100) {
        mPhoto100 = pPhoto100;
    }

    public String getPhoto130() {
        return mPhoto130;
    }

    public void setPhoto130(final String pPhoto130) {
        mPhoto130 = pPhoto130;
    }

    public String getAccessKey() {
        return mAccessKey;
    }

    public void setAccessKey(final String pAccessKey) {
        mAccessKey = pAccessKey;
    }
}
