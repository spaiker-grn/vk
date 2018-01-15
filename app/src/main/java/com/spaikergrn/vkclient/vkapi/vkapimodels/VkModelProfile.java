package com.spaikergrn.vk_client.vkapi.vkapimodels;

import com.spaikergrn.vk_client.serviceclasses.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelProfile extends VkModel {

    private String mFirstName;
    private String mLastName;
    private String mScreenname;
    private int mSex;
    private int mRelation;
    private String mBDate;
    private int mBDateVisibility;
    private String mHomeTown;
    private int mCountryId;
    private String mCountryTitle;
    private int mCityId;
    private String mCityTitle;
    private String mStatus;
    private String mPhone;

    public VkModelProfile(final JSONObject pJSONObject) throws JSONException {
        parse(pJSONObject);
    }

    @Override
    public VkModel parse(final JSONObject pObject) throws JSONException {

        mFirstName = pObject.optString(Constants.Parser.FIRST_NAME);
        mLastName = pObject.optString(Constants.Parser.LAST_NAME);
        mScreenname = pObject.optString(Constants.Parser.SCREEN_NAME);
        mSex = pObject.optInt(Constants.Parser.SEX);
        mRelation = pObject.optInt(Constants.Parser.RELATION);
        mBDate = pObject.optString(Constants.Parser.BDATE);
        mBDateVisibility = pObject.optInt(Constants.Parser.BDATE_VISIBILITY);
        mHomeTown = pObject.optString(Constants.Parser.HOME_TOWN);
        final JSONObject country = pObject.optJSONObject(Constants.Parser.COUNTRY);
        if (country != null){
            mCountryId = country.optInt(Constants.Parser.ID);
            mCountryTitle = country.optString(Constants.Parser.TITLE);
        }
        final JSONObject city = pObject.optJSONObject(Constants.Parser.CITY);
        if (city != null){
            mCityId = city.optInt(Constants.Parser.ID);
            mCityTitle = city.optString(Constants.Parser.TITLE);
        }
        mStatus = pObject.optString(Constants.Parser.STATUS);
        mPhone = pObject.optString(Constants.Parser.PHONE);
        return this;
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

    public String getScreenname() {
        return mScreenname;
    }

    public void setScreenname(final String pScreenname) {
        mScreenname = pScreenname;
    }

    public int getSex() {
        return mSex;
    }

    public void setSex(final int pSex) {
        mSex = pSex;
    }

    public int getRelation() {
        return mRelation;
    }

    public void setRelation(final int pRelation) {
        mRelation = pRelation;
    }

    public String getBDate() {
        return mBDate;
    }

    public void setBDate(final String pBDate) {
        mBDate = pBDate;
    }

    public int getBDateVisibility() {
        return mBDateVisibility;
    }

    public void setBDateVisibility(final int pBDateVisibility) {
        mBDateVisibility = pBDateVisibility;
    }

    public String getHomeTown() {
        return mHomeTown;
    }

    public void setHomeTown(final String pHomeTown) {
        mHomeTown = pHomeTown;
    }

    public int getCountryId() {
        return mCountryId;
    }

    public void setCountryId(final int pCountryId) {
        mCountryId = pCountryId;
    }

    public String getCountryTitle() {
        return mCountryTitle;
    }

    public void setCountryTitle(final String pCountryTitle) {
        mCountryTitle = pCountryTitle;
    }

    public int getCityId() {
        return mCityId;
    }

    public void setCityId(final int pCityId) {
        mCityId = pCityId;
    }

    public String getCityTitle() {
        return mCityTitle;
    }

    public void setCityTitle(final String pCityTitle) {
        mCityTitle = pCityTitle;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(final String pStatus) {
        mStatus = pStatus;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(final String pPhone) {
        mPhone = pPhone;
    }
}

