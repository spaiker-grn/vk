package com.example.myapplication.vkapi.vkapimodels;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelDocument extends VkAttachments.VkModelAttachments {

    public int id;
    public int owner_id;
    public String title;
    public long size;
    public String ext;
    public String url;
    public String photo_100;
    public String photo_130;
    public String access_key;

    public VkModelDocument() {
    }

    public VkModelDocument(final JSONObject pObject) throws JSONException
    {
        parse(pObject);
    }

    public VkModelDocument parse(final JSONObject pObject) {
        id = pObject.optInt("id");
        owner_id = pObject.optInt("owner_id");
        title = pObject.optString("title");
        size = pObject.optLong("size");
        ext = pObject.optString("ext");
        url = pObject.optString("url");
        access_key = pObject.optString("access_key");

        photo_100 = pObject.optString("photo_100");

        photo_130 = pObject.optString("photo_130");

        return this;
    }

    @Override
    public String getType() {
        return VkAttachments.TYPE_DOC;
    }
}
