package com.spaikergrn.vkclient.vkapi.vkapimodels;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

public class VkModelChat implements VkModel {

    private int mId;
    private String mType;
    private String mTitle;
    private int mAdminId;
    private Integer[] mUsers;

    public VkModelChat(final JSONObject pObject) {
        parse(pObject);
    }

    public VkModelChat parse(final JSONObject pObject) {
        mId = pObject.optInt(Constants.Parser.ID);
        mType = pObject.optString(Constants.Parser.TYPE);
        mTitle = pObject.optString(Constants.Parser.TITLE);
        mAdminId = pObject.optInt(Constants.Parser.ADMIN_ID);
        final JSONArray users = pObject.optJSONArray(Constants.Parser.USERS);
        if(users != null) {
            mUsers = new Integer[users.length()];
            for(int i = 0; i < mUsers.length; i++) {
                mUsers[i] = users.optInt(i);
            }
        }
        return this;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public String getType() {
        return mType;
    }

    public void setType(final String pType) {
        mType = pType;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String pTitle) {
        mTitle = pTitle;
    }

    public int getAdminId() {
        return mAdminId;
    }

    public void setAdminId(final int pAdminId) {
        mAdminId = pAdminId;
    }

    public Integer[] getUsers() {
        return mUsers;
    }
}
