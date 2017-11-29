package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.serviceclasses.Constants;

import org.json.JSONObject;

import static com.example.myapplication.tools.ParseUtils.parseBoolean;
import static com.example.myapplication.tools.ParseUtils.parseInt;

public class VkModelPhoto extends VkAttachments.VkModelAttachments {

    private int mId;
    private int mAlbumId;
    private int mOwnerId;
    private int mWidth;
    private int mHeight;
    private String mText;
    private long mDate;
    private String mPhoto75;
    private String mPhoto130;
    private String mPhoto604;
    private String mPhoto807;
    private String mPhoto1280;
    private String mPhoto2560;
    private boolean mUserLikes;
    private boolean mCanComment;
    private int mLikes;
    private int mComments;
    private int mTags;
    private String mAccessKey;

    public VkModelPhoto() {
    }

    public VkModelPhoto(final JSONObject pJSONObject) {
        parse(pJSONObject);
    }

    public VkModelPhoto parse(final JSONObject pObject) {
        mAlbumId = pObject.optInt(Constants.Parser.ALBUM_ID);
        mDate = pObject.optLong(Constants.Parser.DATE);
        mHeight = pObject.optInt(Constants.Parser.HEIGHT);
        mWidth = pObject.optInt(Constants.Parser.WIDTH);
        mOwnerId = pObject.optInt(Constants.Parser.OWNER_ID);
        mId = pObject.optInt(Constants.Parser.ID);
        mText = pObject.optString(Constants.Parser.TEXT);
        mAccessKey = pObject.optString(Constants.Parser.ACCESS_KEY);

        mPhoto75 = pObject.optString(Constants.Parser.PHOTO_75);
        mPhoto130 = pObject.optString(Constants.Parser.PHOTO_130);
        mPhoto604 = pObject.optString(Constants.Parser.PHOTO_604);
        mPhoto807 = pObject.optString(Constants.Parser.PHOTO_807);
        mPhoto1280 = pObject.optString(Constants.Parser.PHOTO_1280);
        mPhoto2560 = pObject.optString(Constants.Parser.PHOTO_2560);

        final JSONObject jsonLikes = pObject.optJSONObject(Constants.Parser.LIKES);
        mLikes = parseInt(jsonLikes, Constants.Parser.COUNT);
        mUserLikes = parseBoolean(jsonLikes, Constants.Parser.USER_LIKES);
        mComments = parseInt(pObject.optJSONObject(Constants.Parser.COMMENTS), Constants.Parser.COUNT);
        mTags = parseInt(pObject.optJSONObject(Constants.Parser.TAGS), Constants.Parser.COUNT);
        mCanComment = parseBoolean(pObject, Constants.Parser.CAN_COMMENT);

        return this;
    }

    @Override
    public String getType() {
        return Constants.Parser.TYPE_PHOTO;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(final int pAlbumId) {
        mAlbumId = pAlbumId;
    }

    public int getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(final int pOwnerId) {
        mOwnerId = pOwnerId;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(final int pWidth) {
        mWidth = pWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(final int pHeight) {
        mHeight = pHeight;
    }

    public String getText() {
        return mText;
    }

    public void setText(final String pText) {
        mText = pText;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(final long pDate) {
        mDate = pDate;
    }

    public String getPhoto75() {
        return mPhoto75;
    }

    public void setPhoto75(final String pPhoto75) {
        mPhoto75 = pPhoto75;
    }

    public String getPhoto130() {
        return mPhoto130;
    }

    public void setPhoto130(final String pPhoto130) {
        mPhoto130 = pPhoto130;
    }

    public String getPhoto604() {
        return mPhoto604;
    }

    public void setPhoto604(final String pPhoto604) {
        mPhoto604 = pPhoto604;
    }

    public String getPhoto807() {
        return mPhoto807;
    }

    public void setPhoto807(final String pPhoto807) {
        mPhoto807 = pPhoto807;
    }

    public String getPhoto1280() {
        return mPhoto1280;
    }

    public void setPhoto1280(final String pPhoto1280) {
        mPhoto1280 = pPhoto1280;
    }

    public String getPhoto2560() {
        return mPhoto2560;
    }

    public void setPhoto2560(final String pPhoto2560) {
        mPhoto2560 = pPhoto2560;
    }

    public boolean isUserLikes() {
        return mUserLikes;
    }

    public void setUserLikes(final boolean pUserLikes) {
        mUserLikes = pUserLikes;
    }

    public boolean isCanComment() {
        return mCanComment;
    }

    public void setCanComment(final boolean pCanComment) {
        mCanComment = pCanComment;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(final int pLikes) {
        mLikes = pLikes;
    }

    public int getComments() {
        return mComments;
    }

    public void setComments(final int pComments) {
        mComments = pComments;
    }

    public int getTags() {
        return mTags;
    }

    public void setTags(final int pTags) {
        mTags = pTags;
    }

    public String getAccessKey() {
        return mAccessKey;
    }

    public void setAccessKey(final String pAccessKey) {
        mAccessKey = pAccessKey;
    }
}
