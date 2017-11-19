package com.example.myapplication.vkapi.vkapimodels;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelUser extends VkModel {

    public int id;
    public String first_name;
    public String last_name;
    public String photo_50;
    public String photo_100;

    public VkModelUser(final JSONObject from) throws JSONException {
        parse(from);
    }

    public VkModelUser parse(final JSONObject pFrom) throws JSONException {

        id = pFrom.optInt("id");
        first_name = pFrom.optString("first_name");
        last_name = pFrom.optString("last_name");
        photo_50 = pFrom.optString("photo_50");
        photo_100 = pFrom.optString("photo_100");

        return this;
    }

    public String getFullName() {
        return first_name + " " + last_name;
    }

}
