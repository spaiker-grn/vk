package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.ParseUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VkModelPost extends VkAttachments.VkModelAttachments {

    private int mId;
    private int mToId;
    private int mFromId;
    private long mDate;
    private String mText;
    private int mReplyOwnerId;
    private int mReplyPostId;
    private boolean mFriendsOnly;
    private int mCommentsCount;
    private boolean mCanPostComment;
    private int mLikesCount;
    private boolean mUserLikes;
    private boolean mCanLike;
    private boolean mCanPublish;
    private int mRepostsCount;
    private boolean mUserReposted;
    private String mPostType;
    private VkAttachments mAttachments = new VkAttachments();
    private int mSignerId;
    private List<VkModelPost> mCopyHistory;

    VkModelPost() {
    }

    public VkModelPost(final JSONObject pObject) throws JSONException
    {
        parse(pObject);
    }

    public VkModelPost parse(final JSONObject pObject) throws JSONException {
        mId = pObject.optInt(Constants.Parser.ID);
        mToId = pObject.optInt(Constants.Parser.TO_ID);
        mFromId = pObject.optInt(Constants.Parser.FROM_ID);
        mDate = pObject.optLong(Constants.Parser.DATE);
        mText = pObject.optString(Constants.Parser.TEXT);
        mReplyOwnerId = pObject.optInt(Constants.Parser.REPLY_OWNER_ID);
        mReplyPostId = pObject.optInt(Constants.Parser.REPLY_POST_ID);
        mFriendsOnly = ParseUtils.parseBoolean(pObject, Constants.Parser.FRIENDS_ONLY);
        final JSONObject comments = pObject.optJSONObject(Constants.Parser.COMMENTS);

        if(comments != null) {
            mCommentsCount = comments.optInt(Constants.Parser.COUNT);
            mCanPostComment = ParseUtils.parseBoolean(comments, Constants.Parser.CAN_POST);
        }
        final JSONObject likes = pObject.optJSONObject(Constants.Parser.LIKES);
        if(likes != null) {
            mLikesCount = likes.optInt(Constants.Parser.COUNT);
            mUserLikes = ParseUtils.parseBoolean(likes, Constants.Parser.USER_LIKES);
            mCanLike = ParseUtils.parseBoolean(likes, Constants.Parser.CAN_LIKE);
            mCanPublish = ParseUtils.parseBoolean(likes, Constants.Parser.CAN_PUBLISH);
        }
        final JSONObject reposts = pObject.optJSONObject(Constants.Parser.REPOSTS);
        if(reposts != null) {
            mRepostsCount = reposts.optInt(Constants.Parser.COUNT);
            mUserReposted = ParseUtils.parseBoolean(reposts, Constants.Parser.USER_REPOSTED);
        }
        mPostType = pObject.optString(Constants.Parser.POST_TYPE);
        mAttachments.fill(pObject.optJSONArray(Constants.Parser.ATTACHMENTS));

        mSignerId = pObject.optInt(Constants.Parser.SIGNER_ID);

        return this;
    }

    @Override
    public String getType() {
        return Constants.Parser.TYPE_POST;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public int getToId() {
        return mToId;
    }

    public void setToId(final int pToId) {
        mToId = pToId;
    }

    public int getFromId() {
        return mFromId;
    }

    public void setFromId(final int pFromId) {
        mFromId = pFromId;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(final long pDate) {
        mDate = pDate;
    }

    public String getText() {
        return mText;
    }

    public void setText(final String pText) {
        mText = pText;
    }

    public int getReplyOwnerId() {
        return mReplyOwnerId;
    }

    public void setReplyOwnerId(final int pReplyOwnerId) {
        mReplyOwnerId = pReplyOwnerId;
    }

    public int getReplyPostId() {
        return mReplyPostId;
    }

    public void setReplyPostId(final int pReplyPostId) {
        mReplyPostId = pReplyPostId;
    }

    public boolean isFriendsOnly() {
        return mFriendsOnly;
    }

    public void setFriendsOnly(final boolean pFriendsOnly) {
        mFriendsOnly = pFriendsOnly;
    }

    public int getCommentsCount() {
        return mCommentsCount;
    }

    public void setCommentsCount(final int pCommentsCount) {
        mCommentsCount = pCommentsCount;
    }

    public boolean isCanPostComment() {
        return mCanPostComment;
    }

    public void setCanPostComment(final boolean pCanPostComment) {
        mCanPostComment = pCanPostComment;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(final int pLikesCount) {
        mLikesCount = pLikesCount;
    }

    public boolean isUserLikes() {
        return mUserLikes;
    }

    public void setUserLikes(final boolean pUserLikes) {
        mUserLikes = pUserLikes;
    }

    public boolean isCanLike() {
        return mCanLike;
    }

    public void setCanLike(final boolean pCanLike) {
        mCanLike = pCanLike;
    }

    public boolean isCanPublish() {
        return mCanPublish;
    }

    public void setCanPublish(final boolean pCanPublish) {
        mCanPublish = pCanPublish;
    }

    public int getRepostsCount() {
        return mRepostsCount;
    }

    public void setRepostsCount(final int pRepostsCount) {
        mRepostsCount = pRepostsCount;
    }

    public boolean isUserReposted() {
        return mUserReposted;
    }

    public void setUserReposted(final boolean pUserReposted) {
        mUserReposted = pUserReposted;
    }

    public String getPostType() {
        return mPostType;
    }

    public void setPostType(final String pPostType) {
        mPostType = pPostType;
    }

    public VkAttachments getAttachments() {
        return mAttachments;
    }

    public void setAttachments(final VkAttachments pAttachments) {
        mAttachments = pAttachments;
    }

    public int getSignerId() {
        return mSignerId;
    }

    public void setSignerId(final int pSignerId) {
        mSignerId = pSignerId;
    }

    public List<VkModelPost> getCopyHistory() {
        return mCopyHistory;
    }

    public void setCopyHistory(final List<VkModelPost> pCopyHistory) {
        mCopyHistory = pCopyHistory;
    }
}
