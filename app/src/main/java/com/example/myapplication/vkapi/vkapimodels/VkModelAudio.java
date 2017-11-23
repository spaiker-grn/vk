package com.example.myapplication.vkapi.vkapimodels;

import org.json.JSONObject;

public class VkModelAudio extends VkAttachments.VkModelAttachments {



    public int id;
    public int owner_id;
    public String artist;
    public String title;
    public int duration;
    public String url;
    public int lyrics_id;
    public int album_id;
    public int genre;
    public String access_key;

    public VkModelAudio() {
    }

    public VkModelAudio(final JSONObject pObject) {
        parse(pObject);
    }


    public VkModelAudio parse(final JSONObject pObject) {
        id = pObject.optInt("id");
        owner_id = pObject.optInt("owner_id");
        artist = pObject.optString("artist");
        title = pObject.optString("title");
        duration = pObject.optInt("duration");
        url = pObject.optString("url");
        lyrics_id = pObject.optInt("lyrics_id");
        album_id = pObject.optInt("album_id");
        genre = pObject.optInt("genre_id");
        access_key = pObject.optString("access_key");
        return this;
    }

    @Override
    public String getType() {
        return VkAttachments.TYPE_AUDIO;
    }
}
