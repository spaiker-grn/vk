package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.serviceclasses.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class VkModelNewsFeeds {

    private List<VkModelPost> mVkModelPosts;
    private Map<Integer, VkModelUser> mUserMap;
    private String mNextFrom;

    private VkModelUser mVkModelUser;

    VkModelNewsFeeds(final JSONObject pJSONObject) throws JSONException {
        parse(pJSONObject);
    }

    public VkModelNewsFeeds parse(final JSONObject pJSONObject) throws JSONException {

        final JSONObject jsonResponse = pJSONObject.getJSONObject(Constants.Parser.RESPONSE);

        final JSONArray jsonArray = jsonResponse.getJSONArray(Constants.Parser.ITEMS);
        for (int i = 0; i < jsonArray.length(); i++){
            mVkModelPosts.add(new VkModelPost(jsonArray.optJSONObject(i)));
        }

        final JSONArray groups = jsonResponse.optJSONArray(Constants.Parser.GROUPS);
        for (int i = 0; i < groups.length(); i++) {
            mVkModelUser = new VkModelUser(groups.optJSONObject(i));
            mUserMap.put(mVkModelUser.getId(), mVkModelUser);
        }
        final JSONArray profiles = jsonResponse.optJSONArray(Constants.Parser.PROFILES);
        for (int i = 0; i < profiles.length(); i++){
            mVkModelUser = new VkModelUser(profiles.optJSONObject(i));
            mUserMap.put(mVkModelUser.getId(), mVkModelUser);

        }
        mNextFrom = pJSONObject.optString(Constants.Parser.NEXT_FROM);

        for (final VkModelPost modelPost : mVkModelPosts   ) {
            final VkModelUser vkModelUser = mUserMap.get(modelPost.getId());
            modelPost.setVkModelUser(vkModelUser);
        }

         return this;
    }

    public VkModelNewsFeeds addNew(final VkModelNewsFeeds pVkModelNewsFeeds){
        this.mVkModelPosts.addAll(pVkModelNewsFeeds.getVkModelPosts());
        this.mUserMap.putAll(pVkModelNewsFeeds.getUserMap());
        this.setNextFrom(pVkModelNewsFeeds.mNextFrom);
        return this;
    }


    public List<VkModelPost> getVkAttachmentsList() {
        return mVkModelPosts;
    }
    public String getNextFrom() {
        return mNextFrom;
    }

    public VkModelUser getVkModelUser() {
        return mVkModelUser;
    }

    public Map<Integer, VkModelUser> getUserMap() {
        return mUserMap;
    }

    public List<VkModelPost> getVkModelPosts() {
        return mVkModelPosts;
    }

    public void setVkModelPosts(final List<VkModelPost> pVkModelPosts) {
        mVkModelPosts = pVkModelPosts;
    }

    public void setUserMap(final Map<Integer, VkModelUser> pUserMap) {
        mUserMap = pUserMap;
    }

    public void setNextFrom(final String pNextFrom) {
        mNextFrom = pNextFrom;
    }

    public void setVkModelUser(final VkModelUser pVkModelUser) {
        mVkModelUser = pVkModelUser;
    }

}
