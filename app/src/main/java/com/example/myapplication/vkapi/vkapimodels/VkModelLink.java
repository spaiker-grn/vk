package com.example.myapplication.vkapi.vkapimodels;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelLink extends VkAttachments.VkModelAttachments {

    public String url;
    public String title;
    public String description;
    public String image_src;
    public String preview_page;

    public VkModelLink() {
    }

    public VkModelLink(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    public VkModelLink parse(final JSONObject pObject) {
        url = pObject.optString("url");
        title = pObject.optString("title");
        description = pObject.optString("description");
        image_src = pObject.optString("image_src");
        preview_page = pObject.optString("preview_page");
        return this;
    }

    @Override
    public String getType() {
        return VkAttachments.TYPE_LINK;
    }
}
