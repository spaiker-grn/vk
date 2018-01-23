package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelDialogK(pJSONObject: JSONObject) : VkModelK {

    var dialogsCount: Int = 0
    var unread: Int = 0
    var messages: VkModelMessagesK? = null

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelK {
        unread = pJSONObject.optInt(Constants.Parser.UNREAD)
        messages = VkModelMessagesK(pJSONObject.optJSONObject(Constants.Parser.MESSAGE))
        return this@VkModelDialogK
    }
}