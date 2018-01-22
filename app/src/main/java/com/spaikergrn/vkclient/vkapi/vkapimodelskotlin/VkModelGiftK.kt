package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelGiftK(pJSONObject: JSONObject) : VkAttachmentsK.VkModelAttachments {

    var id: Int = 0
    var thumb48: String? = null
    var thumb96: String? = null
    var thumb256: String? = null

    override fun getType(): String = Constants.Parser.TYPE_GIFT

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelGiftK {

        id = pJSONObject.optInt(Constants.Parser.ID)
        thumb48 = pJSONObject.optString(Constants.Parser.THUMB_48)
        thumb96 = pJSONObject.optString(Constants.Parser.THUMB_96)
        thumb256 = pJSONObject.optString(Constants.Parser.THUMB_256)
        return this@VkModelGiftK

    }
}