package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.tools.ParseUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelWiki extends VkAttachments.VkModelAttachments {

    public int id;
    public int group_id;
    public int creator_id;
    public String title;
    public String source;
    public int editor_id;
    public long edited;
    public long created;

    public VkModelWiki() {
    }

    public VkModelWiki(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    public VkModelWiki parse(final JSONObject pObject) {
        id = pObject.optInt("id");
        group_id = pObject.optInt("group_id");
        creator_id = pObject.optInt("creator_id");
        title = pObject.optString("title");
        source = pObject.optString("source");
        editor_id = pObject.optInt("editor_id");
        edited = pObject.optLong("edited");
        created = pObject.optLong("created");

        return this;

    }

    @Override
    public String getType() {
        return VkAttachments.TYPE_WIKI_PAGE;
    }
}
