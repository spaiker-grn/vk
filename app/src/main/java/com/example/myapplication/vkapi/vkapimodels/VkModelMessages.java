package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.tools.ParseUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VkModelMessages extends VkModel {

    public int id;
    public int user_id;
    public long date;
    public boolean read_state;
    public boolean out;
    public String title;
    public String body;
    public String attachments;
    public String fwd_messages;
    //public VkModelAttachments attachments;
    //public List<VkModelMessages> fwd_messages;


    VkModelMessages(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    public VkModelMessages parse(final JSONObject pObject) throws JSONException {
        id = pObject.optInt("id");
        user_id = pObject.optInt("user_id");
        date = pObject.optLong("date");
        read_state = ParseUtils.parseBoolean(pObject, "read_state");
        out = ParseUtils.parseBoolean(pObject, "out");
        title = pObject.optString("title");
        body = pObject.optString("body");
        if("".equals(body)) {
            if (pObject.optJSONArray("attachments") != null) {
                attachments = (pObject.optJSONArray("attachments").getJSONObject(0).optString("type"));
            }
            if (pObject.optJSONArray("fwd_message")!=null) {
                fwd_messages = (pObject.optJSONArray("fwd_messages").getJSONObject(0).optString("body"));
            }
        }

        return this;
    }

}
