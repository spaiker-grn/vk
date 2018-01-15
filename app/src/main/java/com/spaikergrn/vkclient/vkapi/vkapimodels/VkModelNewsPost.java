package com.spaikergrn.vkclient.vkapi.vkapimodels;

import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.tools.ParseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VkModelNewsPost implements ILikeAble, VkModel {

    private int mSourceID;
    private long mDate;
    private int mPostID;
    private String mPostType;
    private String mText;
    private VkAttachments mVkAttachments;
    private int mCommentsCount;
    private boolean mCanPostComment;
    private int mLikesCount;
    private boolean mUserLikes;
    private boolean mCanLike;
    private boolean mCanPublish;
    private int mRepostsCount;
    private boolean mUserReposted;
    private int mViewsCount;
    private VkModelGroup mVkModelGroup;
    private VkModelUser mVkModelUser;
    private List<VkModelCopyHistoryPost> mCopyHistory;

    VkModelNewsPost(final JSONObject pJSONObject) throws JSONException {
        parse(pJSONObject);
    }

    @Override
    public VkModel parse(final JSONObject pJSONObject) throws JSONException {
        mSourceID = pJSONObject.getInt(Constants.Parser.SOURCE_ID);
        mDate = pJSONObject.getLong(Constants.Parser.DATE);
        mPostID = pJSONObject.getInt(Constants.Parser.POST_ID);
        mPostType = pJSONObject.getString(Constants.Parser.POST_TYPE);
        mText = pJSONObject.getString(Constants.Parser.TEXT);
        if (pJSONObject.has(Constants.Parser.ATTACHMENTS)) {
            mVkAttachments = new VkAttachments(pJSONObject.getJSONArray(Constants.Parser.ATTACHMENTS));
        }

        final JSONObject comments = pJSONObject.optJSONObject(Constants.Parser.COMMENTS);
        mCommentsCount = ParseUtils.parseInt(comments, Constants.Parser.COUNT);
        mCanPostComment = ParseUtils.parseBoolean(comments, Constants.Parser.CAN_POST);

        final JSONObject likes = pJSONObject.optJSONObject(Constants.Parser.LIKES);
        mLikesCount = likes.optInt(Constants.Parser.COUNT);
        mLikesCount = ParseUtils.parseInt(likes, Constants.Parser.COUNT);
        mUserLikes = ParseUtils.parseBoolean(likes, Constants.Parser.USER_LIKES);
        mCanLike = ParseUtils.parseBoolean(likes, Constants.Parser.CAN_LIKE);
        mCanPublish = ParseUtils.parseBoolean(likes, Constants.Parser.CAN_PUBLISH);

        final JSONObject reposts = pJSONObject.optJSONObject(Constants.Parser.REPOSTS);
        mRepostsCount = ParseUtils.parseInt(reposts, Constants.Parser.COUNT);
        mUserReposted = ParseUtils.parseBoolean(reposts, Constants.Parser.USER_REPOSTED);

        final JSONObject views = pJSONObject.optJSONObject(Constants.Parser.VIEWS);
        mViewsCount = ParseUtils.parseInt(views, Constants.Parser.COUNT);

        final JSONArray copyHistory = pJSONObject.optJSONArray(Constants.Parser.COPY_HISTORY);
        if (copyHistory != null) {
            mCopyHistory = new ArrayList<>();
            for (int i = 0; i < copyHistory.length(); i++) {
                mCopyHistory.add(new VkModelCopyHistoryPost(copyHistory.optJSONObject(i)));
            }
        }
        return this;
    }

    public int getSourceID() {
        return mSourceID;
    }

    public void setSourceID(final int pSourceID) {
        mSourceID = pSourceID;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(final long pDate) {
        mDate = pDate;
    }

    public int getPostID() {
        return mPostID;
    }

    public void setPostID(final int pPostID) {
        mPostID = pPostID;
    }

    public String getPostType() {
        return mPostType;
    }

    public void setPostType(final String pPostType) {
        mPostType = pPostType;
    }

    public String getText() {
        return mText;
    }

    public void setText(final String pText) {
        mText = pText;
    }

    public VkAttachments getVkAttachments() {
        return mVkAttachments;
    }

    public void setVkAttachments(final VkAttachments pVkAttachments) {
        mVkAttachments = pVkAttachments;
    }

    public VkModelGroup getVkModelGroup() {
        return mVkModelGroup;
    }

    public void setVkModelGroup(final VkModelGroup pVkModelGroup) {
        mVkModelGroup = pVkModelGroup;
    }

    public VkModelUser getVkModelUser() {
        return mVkModelUser;
    }

    public void setVkModelUser(final VkModelUser pVkModelUser) {
        mVkModelUser = pVkModelUser;
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

    public int getViewsCount() {
        return mViewsCount;
    }

    public void setViewsCount(final int pViewsCount) {
        mViewsCount = pViewsCount;
    }

    public List<VkModelCopyHistoryPost> getCopyHistory() {
        return mCopyHistory;
    }

    public void setCopyHistory(final List<VkModelCopyHistoryPost> pCopyHistory) {
        mCopyHistory = pCopyHistory;
    }

    @Override
    public void setUserLike(final boolean pUserLike) {
        mUserLikes = pUserLike;
    }

    @Override
    public boolean getUserLike() {
        return mUserLikes;
    }
}
