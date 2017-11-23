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

    public int dialogs_count;
    public int unread;
    public VkModelMessages message;
    public VkModelUser user;

    public void setUser(final VkModelUser pUser) {
        user = pUser;
    }



    public VkModelDialogs(final JSONObject pObject) throws JSONException {
        parse(pObject);

    }

    public VkModelDialogs parse(final JSONObject pObject) throws JSONException {

        unread = pObject.optInt("unread");
        message = new VkModelMessages(pObject.optJSONObject("message"));
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
