package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.tools.ParseUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VkModelPost extends VkAttachments.VkModelAttachments {


//    'm' prefix
    public int id;
    public int to_id;
    public int from_id;
    public long date;
    public String text;
    public int reply_owner_id;
    public int reply_post_id;
    public boolean friends_only;
    public int comments_count;
    public boolean can_post_comment;
    public int likes_count;
    public boolean user_likes;
    public boolean can_like;
    public boolean can_publish;
    public int reposts_count;
    public boolean user_reposted;
    public String post_type;
    public VkAttachments attachments = new VkAttachments();
    public int signer_id;

    public List<VkModelPost> copy_history;

    public VkModelPost() {
    }

    public VkModelPost(final JSONObject pObject) throws JSONException
    {
        parse(pObject);
    }

    public VkModelPost parse(final JSONObject pObject) throws JSONException {
        id = pObject.optInt("id");
        to_id = pObject.optInt("to_id");
        from_id = pObject.optInt("from_id");
        date = pObject.optLong("date");
        text = pObject.optString("text");
        reply_owner_id = pObject.optInt("reply_owner_id");
        reply_post_id = pObject.optInt("reply_post_id");
        friends_only = ParseUtils.parseBoolean(pObject, "friends_only");
        final JSONObject comments = pObject.optJSONObject("comments");

        if(comments != null) {
            comments_count = comments.optInt("count");
            can_post_comment = ParseUtils.parseBoolean(comments, "can_post");
        }
        final JSONObject likes = pObject.optJSONObject("likes");
        if(likes != null) {
            likes_count = likes.optInt("count");
            user_likes = ParseUtils.parseBoolean(likes, "user_likes");
            can_like = ParseUtils.parseBoolean(likes, "can_like");
            can_publish = ParseUtils.parseBoolean(likes, "can_publish");
        }
        final JSONObject reposts = pObject.optJSONObject("reposts");
        if(reposts != null) {
            reposts_count = reposts.optInt("count");
            user_reposted = ParseUtils.parseBoolean(reposts, "user_reposted");
        }
        post_type = pObject.optString("post_type");
        attachments.fill(pObject.optJSONArray("attachments"));

        signer_id = pObject.optInt("signer_id");

        return this;
    }

    @Override
    public String getType() {
        return VkAttachments.TYPE_POST;
    }
}
