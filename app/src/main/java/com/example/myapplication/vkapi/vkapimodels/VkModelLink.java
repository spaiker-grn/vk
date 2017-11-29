package com.example.myapplication.vkapi.vkapimodels;

import com.example.myapplication.serviceclasses.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class VkModelLink extends VkAttachments.VkModelAttachments {

    private String mUrl;
    private String mTitle;
    private String mDescription;
    private String mImageSrc;
    private String mPreviewPage;

    public VkModelLink() {

    }

    public VkModelLink(final JSONObject pObject) throws JSONException {
        parse(pObject);
    }

    public VkModelLink parse(final JSONObject pObject) {
        mUrl = pObject.optString(Constants.Parser.URL);
        mTitle = pObject.optString(Constants.Parser.TITLE);
        mDescription = pObject.optString(Constants.Parser.DESCRIPTION);
        mImageSrc = pObject.optString(Constants.Parser.IMAGE_SRC);
        mPreviewPage = pObject.optString(Constants.Parser.PREVIEW_PAGE);
        return this;
    }

    @Override
    public String getType() {
        return Constants.Parser.TYPE_LINK;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(final String pUrl) {
        mUrl = pUrl;
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

    public void setDescription(final String pDescription) {
        mDescription = pDescription;
    }

    public String getImageSrc() {
        return mImageSrc;
    }

    public void setImageSrc(final String pImageSrc) {
        mImageSrc = pImageSrc;
    }

    public String getPreviewPage() {
        return mPreviewPage;
    }

    public void setPreviewPage(final String pPreviewPage) {
        mPreviewPage = pPreviewPage;
    }
}
