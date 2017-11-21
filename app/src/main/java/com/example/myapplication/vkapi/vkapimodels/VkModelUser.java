package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.vkapi.VkApiMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class VkModelUser extends VkModel {

    public int id;
    public String first_name;
    public String last_name;
    public String photo_50;
    public String photo_100;

    public VkModelUser(final JSONObject from) throws JSONException {
        parse(from);
    }

    public VkModelUser(final int pId) throws ExecutionException, InterruptedException {
         getUserById(pId);
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

    public VkModelUser getUserById(final int pId) throws ExecutionException, InterruptedException {

        try {

            final JSONObject jsonObject = new JSONObject(VkApiMethods.getUserById(pId));

            return parse(jsonObject.getJSONArray("response").getJSONObject(0));

        } catch (final JSONException pE) {
            pE.getMessage();

        }
        return null;
    }
}
