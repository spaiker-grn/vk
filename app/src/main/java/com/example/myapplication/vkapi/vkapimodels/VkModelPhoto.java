package com.example.myapplication.vkapi.vkapimodels;


import org.json.JSONObject;
import static com.example.myapplication.tools.ParseUtils.parseBoolean;
import static com.example.myapplication.tools.ParseUtils.parseInt;

public class VkModelPhoto extends VkAttachments.VkModelAttachments {

    public int id;
    public int album_id;
    public int owner_id;
    public int width;
    public int height;
    public String text;
    public long date;
    public String photo_75;
    public String photo_130;
    public String photo_604;
    public String photo_807;
    public String photo_1280;
    public String photo_2560;
    public boolean user_likes;
    public boolean can_comment;
    public int likes;
    public int comments;
    public int tags;
    public String access_key;

    public VkModelPhoto() {
    }

    public VkModelPhoto(final JSONObject pJSONObject){
        parse(pJSONObject);
    }

    public VkModelPhoto parse(final JSONObject pObject) {
        album_id = pObject.optInt("album_id");
        date = pObject.optLong("date");
        height = pObject.optInt("height");
        width = pObject.optInt("width");
        owner_id = pObject.optInt("owner_id");
        id = pObject.optInt("id");
        text = pObject.optString("text");
        access_key = pObject.optString("access_key");

        photo_75 = pObject.optString("photo_75");
        photo_130 = pObject.optString("photo_130");
        photo_604 = pObject.optString("photo_604");
        photo_807 = pObject.optString("photo_807");
        photo_1280 = pObject.optString("photo_1280");
        photo_2560 = pObject.optString("photo_2560");

        final JSONObject jsonLikes = pObject.optJSONObject("likes");
        likes = parseInt(jsonLikes, "count");
        user_likes = parseBoolean(jsonLikes, "user_likes");
        comments = parseInt(pObject.optJSONObject("comments"), "count");
        tags = parseInt(pObject.optJSONObject("tags"), "count");
        can_comment = parseBoolean(pObject, "can_comment");

        return this;
    }

    @Override
    public String getType() {
        return VkAttachments.TYPE_PHOTO;
    }
}
