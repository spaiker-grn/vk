package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import android.util.Log
import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.vkapi.vkapimodels.*
import org.json.JSONArray
import org.json.JSONObject

class VkAttachmentsK(jsonArray: JSONArray):VkAttachmentsI {

    private var mAttachmentsList: MutableList<VkModelAttachmentsI>? = null

    init{
        fill(jsonArray)
    }

    override fun getAttachmentsList(): MutableList<VkModelAttachmentsI>? {
        return mAttachmentsList
    }

    private fun fill(pArray: JSONArray) {

        mAttachmentsList = mutableListOf()
        for (i in 0 until pArray.length()) {


            val vkModelAttachments: VkModelAttachments?
            try {
                vkModelAttachments = parseObject(pArray.getJSONObject(i))
                if (vkModelAttachments != null) {
                    mAttachmentsList!!.add(vkModelAttachments)
                }
            } catch (pE: Exception) {
                Log.e(Constants.ERROR, pE.message, pE.cause)
            }
        }
    }

    @Throws(Exception::class)
    private fun parseObject(pAttachment: JSONObject): VkModelAttachments? {
        val type = pAttachment.optString(Constants.Parser.TYPE)

        return when (type) {
            Constants.Parser.TYPE_PHOTO -> VkModelPhotoK(pAttachment.getJSONObject(Constants.Parser.TYPE_PHOTO))
            Constants.Parser.TYPE_VIDEO -> VkModelVideoK(pAttachment.getJSONObject(Constants.Parser.TYPE_VIDEO))
            Constants.Parser.TYPE_AUDIO -> VkModelAudioK(pAttachment.getJSONObject(Constants.Parser.TYPE_AUDIO))
            Constants.Parser.TYPE_DOC -> VkModelDocumentK(pAttachment.getJSONObject(Constants.Parser.TYPE_DOC))
            Constants.Parser.TYPE_POST -> VkModelPostK(pAttachment.getJSONObject(Constants.Parser.TYPE_POST))
            Constants.Parser.TYPE_POSTED_PHOTO -> VkModelPhotoK(pAttachment.getJSONObject(Constants.Parser.TYPE_POSTED_PHOTO))
            Constants.Parser.TYPE_LINK -> VkModelLinkK(pAttachment.getJSONObject(Constants.Parser.TYPE_LINK))
            Constants.Parser.TYPE_WIKI_PAGE -> VkModelWikiK(pAttachment.getJSONObject(Constants.Parser.TYPE_WIKI_PAGE))
            Constants.Parser.TYPE_STICKER -> VkModelStickerK(pAttachment.getJSONObject(Constants.Parser.TYPE_STICKER))
            Constants.Parser.TYPE_GIFT -> VkModelGiftK(pAttachment.getJSONObject(Constants.Parser.TYPE_GIFT))
            else -> null
        }
    }

    interface VkModelAttachments : VkModelAttachmentsI
}