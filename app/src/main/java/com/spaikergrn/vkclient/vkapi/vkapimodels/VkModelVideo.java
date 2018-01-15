package com.spaikergrn.vk_client.vkapi.vkapimodels;

import com.spaikergrn.vk_client.serviceclasses.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import static com.spaikergrn.vk_client.tools.ParseUtils.parseBoolean;

public class VkModelVideo extends VkAttachments.VkModelAttachments {

    private int mId;
    private int mOwnerId;
    private String mTitle;
    private String mDescription;
    private int mDuration;
    private long mDate;
    private int mViews;
    private String mPhoto130;
    private String mPhoto320;
    private String mPhoto800;
    private int mWidth;
    private int mHeight;
    private String mFirstFrame130;
    private String mFirstFrame320;
    private String mFirstFrame800;
    private String mAccessKey;
    private int mComments;
    private boolean mCanComment;
    private boolean mCanRepost;
    private boolean mUserLikes;
    private int mLikes;

    public VkModelVideo() {
    }

    public VkModelVideo(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    public VkModelVideo parse(final JSONObject pObject) {
        mId = pObject.optInt(Constants.Parser.ID);
        mOwnerId = pObject.optInt(Constants.Parser.OWNER_ID);
        mTitle = pObject.optString(Constants.Parser.TITLE);
        mDescription = pObject.optString(Constants.Parser.DESCRIPTION);
        mDuration = pObject.optInt(Constants.Parser.DURATION);
        mDate = pObject.optLong(Constants.Parser.DATE);
        mViews = pObject.optInt(Constants.Parser.VIEWS);
        mComments = pObject.optInt(Constants.Parser.COMMENTS);
        mAccessKey = pObject.optString(Constants.Parser.ACCESS_KEY);
        mHeight = pObject.optInt(Constants.Parser.HEIGHT);
        mWidth = pObject.optInt(Constants.Parser.WIDTH);

        final JSONObject likes = pObject.optJSONObject(Constants.Parser.LIKES);
        if (likes != null) {
            this.mLikes = likes.optInt(Constants.Parser.COUNT);
            mUserLikes = parseBoolean(likes, Constants.Parser.USER_LIKES);
        }
        mCanComment = parseBoolean(pObject, Constants.Parser.CAN_COMMENT);
        mCanRepost = parseBoolean(pObject, Constants.Parser.CAN_REPOST);
        mPhoto130 = pObject.optString(Constants.Parser.PHOTO_130);
        mPhoto320 = pObject.optString(Constants.Parser.PHOTO_320);
        mPhoto800 = pObject.optString(Constants.Parser.PHOTO_800);
        mFirstFrame130 = pObject.optString(Constants.Parser.FIRST_FRAME_130);
        mFirstFrame320 = pObject.optString(Constants.Parser.FIRST_FRAME_320);
        mFirstFrame800 = pObject.optString(Constants.Parser.FIRST_FRAME_800);
        return this;
    }

    public String getFirstFrameForNews() {

        if (!mFirstFrame800.equals(Constants.Parser.EMPTY_STRING)) {
            return mFirstFrame800;
        } else if (!mFirstFrame320.equals(Constants.Parser.EMPTY_STRING)) {
            return mFirstFrame320;
        } else if (!mFirstFrame130.equals(Constants.Parser.EMPTY_STRING)) {
            return mFirstFrame130;
        }

        return null;
    }

    public String getPhotoForNews() {

        if (!mPhoto800.equals(Constants.Parser.EMPTY_STRING)) {
            return mPhoto800;
        } else if (!mPhoto320.equals(Constants.Parser.EMPTY_STRING)) {
            return mPhoto320;
        } else if (!mPhoto130.equals(Constants.Parser.EMPTY_STRING)) {
            return mPhoto130;
        }

        return getFirstFrameForNews();
    }

    public String getSmallPhotoForNews() {

        if (!mPhoto320.equals(Constants.Parser.EMPTY_STRING)) {
            return mPhoto320;
        } else if (!mPhoto130.equals(Constants.Parser.EMPTY_STRING)) {
            return mPhoto130;
        }

        return getFirstFrameForNews();
    }

    @Override
    public String getType() {
        return Constants.Parser.TYPE_VIDEO;
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String pTitle) {
        mTitle = pTitle;
    }

    public String getDescription() {
        return mDescription;
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

    public void setDescription(final String pDescription) {
        mDescription = pDescription;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(final int pDuration) {
        mDuration = pDuration;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(final long pDate) {
        mDate = pDate;
    }

    public int getViews() {
        return mViews;
    }

    public void setViews(final int pViews) {
        mViews = pViews;
    }

    public String getPhoto130() {
        return mPhoto130;
    }

    public void setPhoto130(final String pPhoto130) {
        mPhoto130 = pPhoto130;
    }

    public String getPhoto320() {
        return mPhoto320;
    }

    public void setPhoto320(final String pPhoto320) {
        mPhoto320 = pPhoto320;
    }

    public String getPhoto800() {
        return mPhoto800;
    }

    public String getAccessKey() {
        return mAccessKey;
    }

    public void setAccessKey(final String pAccessKey) {
        mAccessKey = pAccessKey;
    }

    public int getComments() {
        return mComments;
    }

    public void setComments(final int pComments) {
        mComments = pComments;
    }

    public boolean isCanComment() {
        return mCanComment;
    }

    public void setCanComment(final boolean pCanComment) {
        mCanComment = pCanComment;
    }

    public boolean isCanRepost() {
        return mCanRepost;
    }

    public void setCanRepost(final boolean pCanRepost) {
        mCanRepost = pCanRepost;
    }

    public boolean isUserLikes() {
        return mUserLikes;
    }

    public void setUserLikes(final boolean pUserLikes) {
        mUserLikes = pUserLikes;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(final int pLikes) {
        mLikes = pLikes;
    }

    public void setPhoto800(final String pPhoto800) {
        mPhoto800 = pPhoto800;
    }

    public String getFirstFrame130() {
        return mFirstFrame130;
    }

    public void setFirstFrame130(final String pFirstFrame130) {
        mFirstFrame130 = pFirstFrame130;
    }

    public String getFirstFrame320() {
        return mFirstFrame320;
    }

    public void setFirstFrame320(final String pFirstFrame320) {
        mFirstFrame320 = pFirstFrame320;
    }

    public String getFirstFrame800() {
        return mFirstFrame800;
    }

    public void setFirstFrame800(final String pFirstFrame800) {
        mFirstFrame800 = pFirstFrame800;
    }
}
