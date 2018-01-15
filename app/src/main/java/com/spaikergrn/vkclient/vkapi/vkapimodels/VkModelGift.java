package com.spaikergrn.vk_client.vkapi.vkapimodels;

import com.spaikergrn.vk_client.serviceclasses.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelGift extends VkAttachments.VkModelAttachments {

    private int mId;
    private String mThumb48;
    private String mThumb96;
    private String mThumb256;

    public VkModelGift(final JSONObject pJSONObject) throws JSONException {
        parse(pJSONObject);
    }

    public VkModelGift parse(final JSONObject pJSONObject) throws JSONException {

        mId = pJSONObject.optInt(Constants.Parser.ID);
        mThumb48 = pJSONObject.optString(Constants.Parser.THUMB_48);
        mThumb96 = pJSONObject.optString(Constants.Parser.THUMB_96);
        mThumb256 = pJSONObject.optString(Constants.Parser.THUMB_256);
        return this;
    }

    @Override
    public String getType() {
        return Constants.Parser.TYPE_GIFT;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public String getThumb48() {
        return mThumb48;
    }

    public void setThumb48(final String pThumb48) {
        mThumb48 = pThumb48;
    }

    public String getThumb96() {
        return mThumb96;
    }

    public void setThumb96(final String pThumb96) {
        mThumb96 = pThumb96;
    }

    public String getThumb256() {
        return mThumb256;
    }

    public void setThumb256(final String pThumb256) {
        mThumb256 = pThumb256;
    }
}
