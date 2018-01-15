package com.spaikergrn.vk_client.vkapi.vkapimodels;

import com.spaikergrn.vk_client.serviceclasses.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelSticker extends VkAttachments.VkModelAttachments {

    private int mId;
    private int mProductId;
    private String mPhoto64;
    private String mPhoto128;
    private String mPhoto256;
    private String mPhoto352;
    private String mPhoto512;
    private int mWidth;
    private int mHeight;

    public VkModelSticker(final JSONObject pJSONObject) throws JSONException {
        parse(pJSONObject);
    }

    public VkModelSticker parse(final JSONObject pJSONObject) throws JSONException {

        mId = pJSONObject.optInt(Constants.Parser.ID);
        mProductId = pJSONObject.optInt(Constants.Parser.PRODUCT_ID);
        mPhoto64 = pJSONObject.optString(Constants.Parser.PHOTO_64);
        mPhoto128 = pJSONObject.optString(Constants.Parser.PHOTO_128);
        mPhoto256 = pJSONObject.optString(Constants.Parser.PHOTO_256);
        mPhoto352 = pJSONObject.optString(Constants.Parser.PHOTO_352);
        mPhoto512 = pJSONObject.optString(Constants.Parser.PHOTO_512);
        mWidth = pJSONObject.optInt(Constants.Parser.WIDTH);
        mHeight = pJSONObject.optInt(Constants.Parser.HEIGHT);

        return this;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(final int pProductId) {
        mProductId = pProductId;
    }

    public String getPhoto64() {
        return mPhoto64;
    }

    public void setPhoto64(final String pPhoto64) {
        mPhoto64 = pPhoto64;
    }

    public String getPhoto128() {
        return mPhoto128;
    }

    public void setPhoto128(final String pPhoto128) {
        mPhoto128 = pPhoto128;
    }

    public String getPhoto256() {
        return mPhoto256;
    }

    public void setPhoto256(final String pPhoto256) {
        mPhoto256 = pPhoto256;
    }

    public String getPhoto352() {
        return mPhoto352;
    }

    public void setPhoto352(final String pPhoto352) {
        mPhoto352 = pPhoto352;
    }

    public String getPhoto512() {
        return mPhoto512;
    }

    public void setPhoto512(final String pPhoto512) {
        mPhoto512 = pPhoto512;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(final int pWidth) {
        mWidth = pWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(final int pHeight) {
        mHeight = pHeight;
    }

    @Override
    public String getType() {
        return Constants.Parser.TYPE_STICKER;
    }
}
