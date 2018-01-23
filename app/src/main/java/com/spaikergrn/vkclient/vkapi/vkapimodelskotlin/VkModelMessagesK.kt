package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.tools.ParseUtils
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkAttachmentsI
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser
import org.json.JSONObject
import java.io.Serializable
import java.util.ArrayList

class VkModelMessagesK(pJSONObject: JSONObject) : VkModelK, Serializable {

    var id: Int = 0
    var userId: Int = 0
    var fromId: Int = 0
    var date: Long = 0
    var readState: Boolean = false
    var out: Boolean = false
    var title: String? = null
    var body: String? = null
    var attachments: VkAttachmentsI? = null
    var fwdMessages: MutableList<VkModelMessagesK>? = null
    var photo50: String? = null
    var countMessagesHistory: Int = 0
    var chatId: Int = 0
    var vkModelUser: VkModelUser? = null

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelMessagesK {
        id = pJSONObject.optInt(Constants.Parser.ID)
        fromId = pJSONObject.optInt(Constants.Parser.FROM_ID)
        userId = pJSONObject.optInt(Constants.Parser.USER_ID)
        date = pJSONObject.optLong(Constants.Parser.DATE)
        readState = ParseUtils.parseBoolean(pJSONObject, Constants.Parser.READ_STATE)
        out = ParseUtils.parseBoolean(pJSONObject, Constants.Parser.OUT)
        title = pJSONObject.optString(Constants.Parser.TITLE)
        body = pJSONObject.optString(Constants.Parser.BODY)

        if (pJSONObject.has(Constants.Parser.ATTACHMENTS)) {
            attachments = VkAttachmentsK(pJSONObject.getJSONArray(Constants.Parser.ATTACHMENTS))
        }
        if (pJSONObject.has(Constants.Parser.FWD_MESSAGE)) {
            fwdMessages = ArrayList()
            val jsonArray = pJSONObject.getJSONArray(Constants.Parser.FWD_MESSAGE)
            for (i in 0 until jsonArray.length()) {
                fwdMessages!!.add(VkModelMessagesK(jsonArray.getJSONObject(i)))
            }
        }

        if (pJSONObject.has(Constants.Parser.PHOTO_50)) {
            photo50 = pJSONObject.optString(Constants.Parser.PHOTO_50)
        }
        if (pJSONObject.has(Constants.Parser.CHAT_ID)) {
            chatId = pJSONObject.optInt(Constants.Parser.CHAT_ID)
        }
        return this@VkModelMessagesK
    }
}