package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.VkApiMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class VkModelUser extends VkModel {

    private int mId;
    private String mFirstName;
    private String mLastName;
    private String mPhoto50;
    private String mPhoto100;
    private String mDeactivated;

    public VkModelUser(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    public VkModelUser parse(final JSONObject pObject) throws JSONException {

        mId = pObject.optInt(Constants.Parser.ID);
        mFirstName = pObject.optString(Constants.Parser.FIRST_NAME);
        mLastName = pObject.optString(Constants.Parser.LAST_NAME);
        mPhoto50 = pObject.optString(Constants.Parser.PHOTO_50);
        mPhoto100 = pObject.optString(Constants.Parser.PHOTO_100);
        if (pObject.has(Constants.Parser.DEACTIVATED)) {
            mDeactivated = pObject.optString(Constants.Parser.DEACTIVATED);
        }
        return this;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }


    public VkModelUser getUserById(final int pId) throws ExecutionException, InterruptedException {

        try {

            final JSONObject jsonObject = new JSONObject(VkApiMethods.getUserById(pId));

            return parse(jsonObject.getJSONArray(Constants.Parser.RESPONSE).getJSONObject(0));

        } catch (final JSONException pE) {
            pE.getMessage();

        }
        return null;
    }


    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public String getDeactivated() {
        return mDeactivated;
    }

    public void setDeactivated(final String pDeactivated) {
        mDeactivated = pDeactivated;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(final String pFirstName) {
        mFirstName = pFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(final String pLastName) {
        mLastName = pLastName;
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
}
