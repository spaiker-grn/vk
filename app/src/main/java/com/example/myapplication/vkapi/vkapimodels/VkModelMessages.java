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


    VkModelMessages(final JSONObject from) throws JSONException {
        parse(from);
    }

    public VkModelMessages parse(final JSONObject pSource) throws JSONException {
        id = pSource.optInt("id");
        user_id = pSource.optInt("user_id");
        date = pSource.optLong("date");
        read_state = ParseUtils.parseBoolean(pSource, "read_state");
        out = ParseUtils.parseBoolean(pSource, "out");
        title = pSource.optString("title");
        body = pSource.optString("body");
        if("".equals(body)) {
            if (pSource.optJSONArray("attachments") != null) {
                attachments = (pSource.optJSONArray("attachments").getJSONObject(0).optString("type"));
            }
            if (pSource.optJSONArray("fwd_message")!=null) {
                fwd_messages = (pSource.optJSONArray("fwd_messages").getJSONObject(0).optString("body"));
            }
        }

        return this;
    }

}
