package com.spaikergrn.vkclient.vkapi.vkapimodels;

import org.json.JSONException;
import org.json.JSONObject;

public interface VkModel {

    VkModel parse(JSONObject pJSONObject) throws JSONException;

}
