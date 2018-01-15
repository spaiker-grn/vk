package com.spaikergrn.vk_client.vkapi.vkapimodels;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class  VkModel {

    public abstract VkModel parse(JSONObject pJSONObject) throws JSONException;

}
