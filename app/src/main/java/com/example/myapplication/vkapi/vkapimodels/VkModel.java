package com.example.myapplication.vkapi.vkapimodels;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class  VkModel {

    public abstract VkModel parse(JSONObject pJSONObject) throws JSONException;


}
