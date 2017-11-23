package com.example.myapplication.vkapi.vkapimodels;

import android.util.Log;

import com.example.myapplication.serviceclasses.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VkAttachments {

    public static final String TYPE_PHOTO = "photo";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_DOC = "doc";
    public static final String TYPE_POST = "wall";
    public static final String TYPE_POSTED_PHOTO = "posted_photo";
    public static final String TYPE_LINK = "link";
    public static final String TYPE_WIKI_PAGE = "page";

    public List<VkModelAttachments> mAttachmentsList = new ArrayList<>();

    public VkAttachments(final JSONArray pFrom)  {
        fill(pFrom);
    }

    public VkAttachments() {
    }

    public void fill(final JSONArray pArray)  {

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
                    pE.printStackTrace();
                }


            }

        }

    }

    public VkModelAttachments parseObject(final JSONObject pAttachment) throws Exception {
        final String type = pAttachment.optString("type");

        if (TYPE_PHOTO.equals(type)) {
            return new VkModelPhoto().parse(pAttachment.getJSONObject(TYPE_PHOTO));
        } else if (TYPE_VIDEO.equals(type)) {
            return new VkModelVideo().parse(pAttachment.getJSONObject(TYPE_VIDEO));
        } else if (TYPE_AUDIO.equals(type)) {
            return new VkModelAudio().parse(pAttachment.getJSONObject(TYPE_AUDIO));
        } else if (TYPE_DOC.equals(type)) {
            return new VkModelDocument().parse(pAttachment.getJSONObject(TYPE_DOC));
        } else if (TYPE_POST.equals(type)) {
            return new VkModelPost().parse(pAttachment.getJSONObject(TYPE_POST));
        } else if (TYPE_POSTED_PHOTO.equals(type)) {
            return new VkModelPhoto().parse(pAttachment.getJSONObject(TYPE_POSTED_PHOTO));
        } else if (TYPE_LINK.equals(type)) {
            return new VkModelLink().parse(pAttachment.getJSONObject(TYPE_LINK));
        } else if (TYPE_WIKI_PAGE.equals(type)) {
            return new VkModelWiki().parse(pAttachment.getJSONObject(TYPE_WIKI_PAGE));
        }
        return null;
    }

    public abstract static class VkModelAttachments extends VkModel {

        public abstract String getType();

    }

}
