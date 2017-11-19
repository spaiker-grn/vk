package com.example.myapplication.vkapi.vkapimodels;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

/*    public VkModelUser getUserById(final int pId){

        try {
            final JSONObject jsonObject = new JSONObject(VkApiMethods.getUser(pId,"photo_50, photo_100"));
            final JSONObject userObject  = jsonObject.getJSONArrayItems("response").getJSONObject(0);
            return new VkModelUser(userObject);

        } catch (final JSONException pE) {
            pE.getMessage();

        }
        return null;
    }*/

}
