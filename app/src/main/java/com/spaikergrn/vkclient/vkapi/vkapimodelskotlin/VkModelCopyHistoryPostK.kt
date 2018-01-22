package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkAttachmentsI
import org.json.JSONObject

class VkModelCopyHistoryPostK (pJSONObject: JSONObject)  : VkModelK {

    var id: Int = 0
    var ownerId: Int = 0
    var fromId: Int = 0
    var date: Long = 0
    var text: String? = null
    var type: String? = null
    var vkAttachmentsI: VkAttachmentsI? = null

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelCopyHistoryPostK {
        id = pJSONObject.optInt(Constants.Parser.ID)
        ownerId = pJSONObject.optInt(Constants.Parser.OWNER_ID)
        fromId = pJSONObject.optInt(Constants.Parser.FROM_ID)
        date = pJSONObject.optLong(Constants.Parser.DATE)
        type = pJSONObject.optString(Constants.Parser.POST_TYPE)
        text = pJSONObject.optString(Constants.Parser.TEXT)
        if (pJSONObject.has(Constants.Parser.ATTACHMENTS)) {
            vkAttachmentsI = VkAttachmentsK(pJSONObject.getJSONArray(Constants.Parser.ATTACHMENTS))
        }
        return this
    }
}
