package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelStickerK(pJSONObject: JSONObject) : VkAttachmentsK.VkModelAttachments {

    var id: Int = 0
    var productId: Int = 0
    var photo64: String? = null
    var photo128: String? = null
    var photo256: String? = null
    var photo352: String? = null
    var photo512: String? = null
    var width: Int = 0
    var height: Int = 0

    override fun getType(): String = Constants.Parser.TYPE_STICKER

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelStickerK {
        id = pJSONObject.optInt(Constants.Parser.ID)
        productId = pJSONObject.optInt(Constants.Parser.PRODUCT_ID)
        photo64 = pJSONObject.optString(Constants.Parser.PHOTO_64)
        photo128 = pJSONObject.optString(Constants.Parser.PHOTO_128)
        photo256 = pJSONObject.optString(Constants.Parser.PHOTO_256)
        photo352 = pJSONObject.optString(Constants.Parser.PHOTO_352)
        photo512 = pJSONObject.optString(Constants.Parser.PHOTO_512)
        width = pJSONObject.optInt(Constants.Parser.WIDTH)
        height = pJSONObject.optInt(Constants.Parser.HEIGHT)
        return this@VkModelStickerK
    }

}