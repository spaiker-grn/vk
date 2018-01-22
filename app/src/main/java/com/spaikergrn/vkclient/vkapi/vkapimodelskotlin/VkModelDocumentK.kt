package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelDocumentK(pJsonObject: JSONObject): VkAttachmentsK.VkModelAttachments {

    override fun getType(): String = Constants.Parser.TYPE_DOC

    var id: Int = 0
    var ownerId: Int = 0
    var title: String? = null
    var size: Long = 0
    var ext: String? = null
    var url: String? = null
    var photo100: String? = null
    var photo130: String? = null
    var accessKey: String? = null

    init {
        parse(pJsonObject)
    }

    override fun parse(pJsonObject: JSONObject): VkModelDocumentK {
        id = pJsonObject.optInt(Constants.Parser.ID)
        ownerId = pJsonObject.optInt(Constants.Parser.OWNER_ID)
        title = pJsonObject.optString(Constants.Parser.TITLE)
        size = pJsonObject.optLong(Constants.Parser.SIZE)
        ext = pJsonObject.optString(Constants.Parser.EXT)
        url = pJsonObject.optString(Constants.Parser.URL)
        accessKey = pJsonObject.optString(Constants.Parser.ACCESS_KEY)
        photo100 = pJsonObject.optString(Constants.Parser.PHOTO_100)
        photo130 = pJsonObject.optString(Constants.Parser.PHOTO_130)
        return this@VkModelDocumentK
    }
}