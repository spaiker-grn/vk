package com.spaikergrn.vkclient.vkapi.vkapimodels;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import org.json.JSONObject;

public class VkModelAudio implements VkAttachments.VkModelAttachments {

    private int mId;
    private int mOwnerId;
    private String mArtist;
    private String mTitle;
    private int mDuration;
    private String mUrl;
    private int mLyricsId;
    private int mAlbumId;
    private int mGenre;
    private String mAccessKey;

    VkModelAudio(final JSONObject pObject) {
        parse(pObject);
    }

    public VkModelAudio parse(final JSONObject pObject) {
        mId = pObject.optInt(Constants.Parser.ID);
        mOwnerId = pObject.optInt(Constants.Parser.OWNER_ID);
        mArtist = pObject.optString(Constants.Parser.ARTIST);
        mTitle = pObject.optString(Constants.Parser.TITLE);
        mDuration = pObject.optInt(Constants.Parser.DURATION);
        mUrl = pObject.optString(Constants.Parser.URL);
        mLyricsId = pObject.optInt(Constants.Parser.LYRICS_ID);
        mAlbumId = pObject.optInt(Constants.Parser.ALBUM_ID);
        mGenre = pObject.optInt(Constants.Parser.GENRE_ID);
        mAccessKey = pObject.optString(Constants.Parser.ACCESS_KEY);
        return this;
    }


    @Override
    public String getType() {
        return Constants.Parser.TYPE_AUDIO;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public int getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(final int pOwnerId) {
        mOwnerId = pOwnerId;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(final String pArtist) {
        mArtist = pArtist;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String pTitle) {
        mTitle = pTitle;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(final int pDuration) {
        mDuration = pDuration;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(final String pUrl) {
        mUrl = pUrl;
    }

    public int getLyricsId() {
        return mLyricsId;
    }

    public void setLyricsId(final int pLyricsId) {
        mLyricsId = pLyricsId;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(final int pAlbumId) {
        mAlbumId = pAlbumId;
    }

    public int getGenre() {
        return mGenre;
    }

    public void setGenre(final int pGenre) {
        mGenre = pGenre;
    }

    public String getAccessKey() {
        return mAccessKey;
    }

    public void setAccessKey(final String pAccessKey) {
        mAccessKey = pAccessKey;
    }
}
