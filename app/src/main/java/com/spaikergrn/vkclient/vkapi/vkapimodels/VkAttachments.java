package com.spaikergrn.vkclient.vkapi.vkapimodels;

import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VkAttachments implements VkAttachmentsI {

    private List<VkModelAttachmentsI> mAttachmentsList;

    @Override
    public List<VkModelAttachmentsI> getAttachmentsList() {
        return mAttachmentsList;
    }

    public VkAttachments(final JSONArray pFrom) {
        fill(pFrom);
    }

    public VkAttachments() {
    }

    private void fill(final JSONArray pArray) {

        if (pArray != null) {
            mAttachmentsList = new ArrayList<>();
            for (int i = 0; i < pArray.length(); i++) {

                final VkModelAttachmentsI vkModelAttachmentsI;
                try {
                    vkModelAttachmentsI = parseObject(pArray.getJSONObject(i));
                    if (vkModelAttachmentsI != null) {
                        mAttachmentsList.add(vkModelAttachmentsI);
                    }
                } catch (final Exception pE) {

                    Log.e(Constants.ERROR, pE.getMessage());
                }
            }
        }
    }

    private VkModelAttachmentsI parseObject(final JSONObject pAttachment) throws Exception {
        final String type = pAttachment.optString(Constants.Parser.TYPE);

        if (Constants.Parser.TYPE_PHOTO.equals(type)) {
            return new VkModelPhoto(pAttachment.getJSONObject(Constants.Parser.TYPE_PHOTO));
        } else if (Constants.Parser.TYPE_VIDEO.equals(type)) {
            return new VkModelVideo(pAttachment.getJSONObject(Constants.Parser.TYPE_VIDEO));
        } else if (Constants.Parser.TYPE_AUDIO.equals(type)) {
            return new VkModelAudio(pAttachment.getJSONObject(Constants.Parser.TYPE_AUDIO));
        } else if (Constants.Parser.TYPE_DOC.equals(type)) {
            return new VkModelDocument(pAttachment.getJSONObject(Constants.Parser.TYPE_DOC));
        } else if (Constants.Parser.TYPE_POST.equals(type)) {
            return new VkModelPost(pAttachment.getJSONObject(Constants.Parser.TYPE_POST));
        } else if (Constants.Parser.TYPE_POSTED_PHOTO.equals(type)) {
            return new VkModelPhoto(pAttachment.getJSONObject(Constants.Parser.TYPE_POSTED_PHOTO));
        } else if (Constants.Parser.TYPE_LINK.equals(type)) {
            return new VkModelLink(pAttachment.getJSONObject(Constants.Parser.TYPE_LINK));
        } else if (Constants.Parser.TYPE_WIKI_PAGE.equals(type)) {
            return new VkModelWiki(pAttachment.getJSONObject(Constants.Parser.TYPE_WIKI_PAGE));
        }else if (Constants.Parser.TYPE_STICKER.equals(type)) {
            return new VkModelSticker(pAttachment.getJSONObject(Constants.Parser.TYPE_STICKER));
        }else if (Constants.Parser.TYPE_GIFT.equals(type)) {
            return new VkModelGift(pAttachment.getJSONObject(Constants.Parser.TYPE_GIFT));
        }

        return null;
    }

    public interface VkModelAttachments extends VkModelAttachmentsI {

    }
}
