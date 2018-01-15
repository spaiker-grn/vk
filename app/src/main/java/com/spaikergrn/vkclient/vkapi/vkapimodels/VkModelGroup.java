package com.spaikergrn.vk_client.vkapi.vkapimodels;

import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.tools.ParseUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelGroup extends VkModel {

    private int mId;
    private String mName;
    private String mScreenName;
    private String mPhoto50;
    private String mPhoto100;
    private boolean mIsMember;
    private boolean mIsAdmin;
    private String mDeactivated;

    public VkModelGroup(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    @Override
    public VkModel parse(final JSONObject pObject) throws JSONException {
        mId = pObject.optInt(Constants.Parser.ID);
        mName = pObject.optString(Constants.Parser.NAME);
        mScreenName = pObject.getString(Constants.Parser.SCREEN_NAME);
        mPhoto50 = pObject.optString(Constants.Parser.PHOTO_50);
        mPhoto100 = pObject.optString(Constants.Parser.PHOTO_100);
        mIsMember = ParseUtils.parseBoolean(pObject, Constants.Parser.IS_MEMBER);
        mIsAdmin = ParseUtils.parseBoolean(pObject, Constants.Parser.IS_ADMIN);
        if (pObject.has(Constants.Parser.DEACTIVATED)) {
            mDeactivated = pObject.optString(Constants.Parser.DEACTIVATED);
        }
        return this;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String pName) {
        mName = pName;
    }

    public String getScreenName() {
        return mScreenName;
    }

    public void setScreenName(final String pScreenName) {
        mScreenName = pScreenName;
    }

    public String getPhoto50() {
        return mPhoto50;
    }

    public void setPhoto50(final String pPhoto50) {
        mPhoto50 = pPhoto50;
    }

    public String getPhoto100() {
        return mPhoto100;
    }

    public void setPhoto100(final String pPhoto100) {
        mPhoto100 = pPhoto100;
    }

    public boolean isMember() {
        return mIsMember;
    }

    public void setMember(final boolean pMember) {
        mIsMember = pMember;
    }

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public void setAdmin(final boolean pAdmin) {
        mIsAdmin = pAdmin;
    }

    public String getDeactivated() {
        return mDeactivated;
    }

    public void setDeactivated(final String pDeactivated) {
        mDeactivated = pDeactivated;
    }
}
