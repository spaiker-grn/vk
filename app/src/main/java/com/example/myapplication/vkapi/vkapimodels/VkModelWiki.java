package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.serviceclasses.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelWiki extends VkAttachments.VkModelAttachments {

    private int mId;
    private int mGroupId;
    private int mCreatorId;
    private String mTitle;
    private String mSource;
    private int mEditorId;
    private long mEdited;
    private long mCreated;

    public VkModelWiki() {
    }

    public VkModelWiki(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    public VkModelWiki parse(final JSONObject pObject) {
        mId = pObject.optInt(Constants.Parser.ID);
        mGroupId = pObject.optInt(Constants.Parser.GROUP_ID);
        mCreatorId = pObject.optInt(Constants.Parser.CREATOR_ID);
        mTitle = pObject.optString(Constants.Parser.TITLE);
        mSource = pObject.optString(Constants.Parser.SOURCE);
        mEditorId = pObject.optInt(Constants.Parser.EDITOR_ID);
        mEdited = pObject.optLong(Constants.Parser.EDITED);
        mCreated = pObject.optLong(Constants.Parser.CREATED);

        return this;

    }

    @Override
    public String getType() {
        return Constants.Parser.TYPE_WIKI_PAGE;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int pId) {
        mId = pId;
    }

    public int getGroupId() {
        return mGroupId;
    }

    public void setGroupId(final int pGroupId) {
        mGroupId = pGroupId;
    }

    public int getCreatorId() {
        return mCreatorId;
    }

    public void setCreatorId(final int pCreatorId) {
        mCreatorId = pCreatorId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String pTitle) {
        mTitle = pTitle;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(final String pSource) {
        mSource = pSource;
    }

    public int getEditorId() {
        return mEditorId;
    }

    public void setEditorId(final int pEditorId) {
        mEditorId = pEditorId;
    }

    public long getEdited() {
        return mEdited;
    }

    public void setEdited(final long pEdited) {
        mEdited = pEdited;
    }

    public long getCreated() {
        return mCreated;
    }

    public void setCreated(final long pCreated) {
        mCreated = pCreated;
    }
}
