package com.example.myapplication.vkapi.vkapimodels;


import com.example.myapplication.tools.ParseUtils;
import com.example.myapplication.vkapi.VkApiMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VkModelDialogs extends VkModel {

    public int unread;
    public VkModelMessages message;
    public VkModelUser user;

    public void setUser(final VkModelUser pUser) {
        user = pUser;
    }



    public VkModelDialogs(final JSONObject from) throws JSONException {
        parse(from);

    }

    public VkModelDialogs parse(final JSONObject pFrom) throws JSONException {

        unread = pFrom.optInt("unread");
        message = new VkModelMessages(pFrom.optJSONObject("message"));
        return this;
    }

    public static List<VkModelDialogs> initFromJsomArray(final JSONArray pJSONArray) throws JSONException {
        final List<VkModelDialogs> vkModelDialogsList = new ArrayList<>();
        for (int i = 0; i < pJSONArray.length(); i++){
            vkModelDialogsList.add(new VkModelDialogs(pJSONArray.getJSONObject(i)));
        }
        return vkModelDialogsList;
    }

}
