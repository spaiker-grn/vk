package com.spaikergrn.vkclient.vkapi.vkapimodels;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import org.json.JSONObject;

public class VkModelLongPollServer implements VkModel {

    private String mKey;
    private String mServer;
    private String mTs;

    public VkModelLongPollServer(final JSONObject pObject) {
        parse(pObject);
    }

    public VkModelLongPollServer parse(final JSONObject pJSONObject) {

        final JSONObject JSONObject = pJSONObject.optJSONObject(Constants.Parser.RESPONSE);
        mKey = JSONObject.optString(Constants.Parser.KEY);
        mServer = JSONObject.optString(Constants.Parser.SERVER);
        mTs = JSONObject.optString(Constants.Parser.TS);
        return this;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(final String pKey) {
        mKey = pKey;
    }

    public String getServer() {
        return mServer;
    }

    public void setServer(final String pServer) {
        mServer = pServer;
    }

    public String getTs() {
        return mTs;
    }

    public void setTs(final String pTs) {
        mTs = pTs;
    }
}
