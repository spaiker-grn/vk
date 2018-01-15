package com.spaikergrn.vk_client.vkapi.vkapimodels;

import android.util.SparseArray;

import com.spaikergrn.vk_client.serviceclasses.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VkModelNewsFeeds implements Serializable {

    private List<VkModelNewsPost> mVkModelNewsPosts = new ArrayList<>();
    private SparseArray<VkModelUser> mUserMap = new SparseArray<>();
    private SparseArray<VkModelGroup> mGroupMap = new SparseArray<>();
    private String mNextFrom;
    private VkModelUser mVkModelUser;
    private VkModelGroup mVkModelGroup;

    public VkModelNewsFeeds(final JSONObject pJSONObject) throws JSONException {
        parse(pJSONObject);
    }

    public VkModelNewsFeeds() {
    }

    public VkModelNewsFeeds parse(final JSONObject pJSONObject) throws JSONException {

        final JSONObject jsonResponse = pJSONObject.getJSONObject(Constants.Parser.RESPONSE);

        final JSONArray jsonArray = jsonResponse.getJSONArray(Constants.Parser.ITEMS);
        for (int i = 0; i < jsonArray.length(); i++){
            mVkModelNewsPosts.add(new VkModelNewsPost(jsonArray.optJSONObject(i)));
        }

        final JSONArray groups = jsonResponse.optJSONArray(Constants.Parser.GROUPS);
        for (int i = 0; i < groups.length(); i++) {
            mVkModelGroup = new VkModelGroup(groups.optJSONObject(i));
            mGroupMap.put(mVkModelGroup.getId(), mVkModelGroup);
        }

        final JSONArray profiles = jsonResponse.optJSONArray(Constants.Parser.PROFILES);
        for (int i = 0; i < profiles.length(); i++){
            mVkModelUser = new VkModelUser(profiles.optJSONObject(i));
            mUserMap.put(mVkModelUser.getId(), mVkModelUser);
        }

        mNextFrom = jsonResponse.optString(Constants.Parser.NEXT_FROM);

        for (final VkModelNewsPost modelPost : mVkModelNewsPosts) {
            if (modelPost.getSourceID() < 0){
                modelPost.setVkModelGroup(mGroupMap.get(Math.abs(modelPost.getSourceID())));
            } else {
                modelPost.setVkModelUser(mUserMap.get(modelPost.getSourceID()));
            }

        }
         return this;
    }

    public VkModelNewsFeeds addNew(final VkModelNewsFeeds pVkModelNewsFeeds){

        if (pVkModelNewsFeeds != null){
            for(int i = 0; i < pVkModelNewsFeeds.getUserMap().size(); i++){
                this.getUserMap().put(pVkModelNewsFeeds.getUserMap().keyAt(i),pVkModelNewsFeeds.getUserMap().valueAt(i));
            }
            for(int i = 0; i < pVkModelNewsFeeds.getGroupMap().size(); i++){
                this.getGroupMap().put(pVkModelNewsFeeds.getGroupMap().keyAt(i), pVkModelNewsFeeds.getGroupMap().valueAt(i));
            }
            this.mVkModelNewsPosts.addAll(pVkModelNewsFeeds.getVkModelNewsPosts());
            this.setNextFrom(pVkModelNewsFeeds.mNextFrom);
            return this;
        }
        return null;
    }

    public List<VkModelNewsPost> getVkModelNewsPosts() {
        return mVkModelNewsPosts;
    }

    public void setVkModelNewsPosts(final List<VkModelNewsPost> pVkModelNewsPosts) {
        mVkModelNewsPosts = pVkModelNewsPosts;
    }

    public SparseArray<VkModelUser> getUserMap() {
        return mUserMap;
    }

    public void setUserMap(final SparseArray<VkModelUser> pUserMap) {
        mUserMap = pUserMap;
    }

    public SparseArray<VkModelGroup> getGroupMap() {
        return mGroupMap;
    }

    public void setGroupMap(final SparseArray<VkModelGroup> pGroupMap) {
        mGroupMap = pGroupMap;
    }

    public String getNextFrom() {
        return mNextFrom;
    }

    public void setNextFrom(final String pNextFrom) {
        mNextFrom = pNextFrom;
    }

    public VkModelUser getVkModelUser() {
        return mVkModelUser;
    }

    public void setVkModelUser(final VkModelUser pVkModelUser) {
        mVkModelUser = pVkModelUser;
    }

    public VkModelGroup getVkModelGroup() {
        return mVkModelGroup;
    }

    public void setVkModelGroup(final VkModelGroup pVkModelGroup) {
        mVkModelGroup = pVkModelGroup;
    }
}