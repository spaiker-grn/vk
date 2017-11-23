package com.example.myapplication.vkapi.vkapimodels;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.myapplication.tools.ParseUtils.parseBoolean;

public class VkModelVideo extends VkAttachments.VkModelAttachments {


    public int id;
    public int owner_id;
    public String title;
    public String description;
    public int duration;
    public long date;
    public int views;
    public String photo_130;
    public String photo_320;
    public String photo_640;
    public String access_key;
    public int comments;
    public boolean can_comment;
    public boolean can_repost;
    public boolean user_likes;
    public int likes;

    public VkModelVideo() {
    }

    public VkModelVideo(final JSONObject pObject) throws JSONException
    {
        parse(pObject);
    }


    public VkModelVideo parse(final JSONObject pObject) {
        id = pObject.optInt("id");
        owner_id = pObject.optInt("owner_id");
        title = pObject.optString("title");
        description = pObject.optString("description");
        duration = pObject.optInt("duration");
        date = pObject.optLong("date");
        views = pObject.optInt("views");
        comments = pObject.optInt("comments");
        access_key = pObject.optString("access_key");

        final JSONObject likes = pObject.optJSONObject("likes");
        if(likes != null) {
            this.likes = likes.optInt("count");
            user_likes = parseBoolean(likes, "user_likes");
        }
        can_comment = parseBoolean(pObject, "can_comment");
        can_repost = parseBoolean(pObject, "can_repost");
        photo_130 = pObject.optString("photo_130");
        photo_320 = pObject.optString("photo_320");
        photo_640 = pObject.optString("photo_640");

        return this;
    }

    @Override
    public String getType() {
        return VkAttachments.TYPE_VIDEO;
    }
}
