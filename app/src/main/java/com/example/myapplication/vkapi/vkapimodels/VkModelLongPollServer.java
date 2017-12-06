package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.serviceclasses.Constants;

import org.json.JSONObject;

public class VkModelLongPollServer extends VkModel {

    private String mKey;
    private String mServer;
    private String mTs;

    public VkModelLongPollServer(final JSONObject pObject) {
        parse(pObject);

    }

    public VkModelLongPollServer parse(final JSONObject pObject) {
        mKey = pObject.optString(Constants.Parser.KEY);
        mServer = pObject.optString(Constants.Parser.SERVER);
        mTs = pObject.optString(Constants.Parser.TS);
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
