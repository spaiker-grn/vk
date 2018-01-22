package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelLinkK(pJSONObject: JSONObject): VkAttachmentsK.VkModelAttachments {

    var url: String? = null
    var title: String? = null
    var description: String? = null
    var imageSrc: String? = null
    var previewPage: String? = null
    var vkModelPhoto: VkModelPhotoK? = null

    override fun getType(): String = Constants.Parser.TYPE_LINK

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelLinkK {
        url = pJSONObject.optString(Constants.Parser.URL)
        title = pJSONObject.optString(Constants.Parser.TITLE)
        description = pJSONObject.optString(Constants.Parser.DESCRIPTION)
        imageSrc = pJSONObject.optString(Constants.Parser.IMAGE_SRC)
        previewPage = pJSONObject.optString(Constants.Parser.PREVIEW_PAGE)
        if (pJSONObject.has(Constants.Parser.PHOTO)) {
            vkModelPhoto = VkModelPhotoK(pJSONObject.getJSONObject(Constants.Parser.PHOTO))
        }
        return this@VkModelLinkK
    }
}