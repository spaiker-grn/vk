package com.example.myapplication.vkapi.vkapimodels;

import android.util.Log;

import com.example.myapplication.serviceclasses.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VkAttachments {

    private final List<VkModelAttachments> mAttachmentsList = new ArrayList<>();

    public VkAttachments(final JSONArray pFrom) {
        fill(pFrom);
    }

    public VkAttachments() {
    }

    public void fill(final JSONArray pArray) {

        if (pArray != null) {
            for (int i = 0; i < pArray.length(); i++) {

                final VkModelAttachments vkModelAttachments;
                try {
                    vkModelAttachments = parseObject(pArray.getJSONObject(i));
                    if (vkModelAttachments != null) {
                        mAttachmentsList.add(vkModelAttachments);
                    }

                } catch (final Exception pE) {

                    Log.d(Constants.ERROR, pE.getMessage());
                }

            }

        }

    }

    private VkModelAttachments parseObject(final JSONObject pAttachment) throws Exception {
        final String type = pAttachment.optString(Constants.Parser.TYPE);

        if (Constants.Parser.TYPE_PHOTO.equals(type)) {
            return new VkModelPhoto().parse(pAttachment.getJSONObject(Constants.Parser.TYPE_PHOTO));
        } else if (Constants.Parser.TYPE_VIDEO.equals(type)) {
            return new VkModelVideo().parse(pAttachment.getJSONObject(Constants.Parser.TYPE_VIDEO));
        } else if (Constants.Parser.TYPE_AUDIO.equals(type)) {
            return new VkModelAudio().parse(pAttachment.getJSONObject(Constants.Parser.TYPE_AUDIO));
        } else if (Constants.Parser.TYPE_DOC.equals(type)) {
            return new VkModelDocument().parse(pAttachment.getJSONObject(Constants.Parser.TYPE_DOC));
        } else if (Constants.Parser.TYPE_POST.equals(type)) {
            return new VkModelPost().parse(pAttachment.getJSONObject(Constants.Parser.TYPE_POST));
        } else if (Constants.Parser.TYPE_POSTED_PHOTO.equals(type)) {
            return new VkModelPhoto().parse(pAttachment.getJSONObject(Constants.Parser.TYPE_POSTED_PHOTO));
        } else if (Constants.Parser.TYPE_LINK.equals(type)) {
            return new VkModelLink().parse(pAttachment.getJSONObject(Constants.Parser.TYPE_LINK));
        } else if (Constants.Parser.TYPE_WIKI_PAGE.equals(type)) {
            return new VkModelWiki().parse(pAttachment.getJSONObject(Constants.Parser.TYPE_WIKI_PAGE));
        }
        return null;
    }

    public abstract static class VkModelAttachments extends VkModel {

        public abstract String getType();

    }

}
