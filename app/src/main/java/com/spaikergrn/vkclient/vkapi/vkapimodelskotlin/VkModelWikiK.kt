package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelWikiK(pJsonObject: JSONObject) : VkAttachmentsK.VkModelAttachments {

    var id: Int = 0
    var groupId: Int = 0
    var creatorId: Int = 0
    var title: String? = null
    var source: String? = null
    var editorId: Int = 0
    var edited: Long = 0
    var created: Long = 0

    override fun getType(): String = Constants.Parser.TYPE_WIKI_PAGE

    init {
        parse(pJsonObject)
    }

    override fun parse(pJsonObject: JSONObject): VkModelWikiK {
        id = pJsonObject.optInt(Constants.Parser.ID)
        groupId = pJsonObject.optInt(Constants.Parser.GROUP_ID)
        creatorId = pJsonObject.optInt(Constants.Parser.CREATOR_ID)
        title = pJsonObject.optString(Constants.Parser.TITLE)
        source = pJsonObject.optString(Constants.Parser.SOURCE)
        editorId = pJsonObject.optInt(Constants.Parser.EDITOR_ID)
        edited = pJsonObject.optLong(Constants.Parser.EDITED)
        created = pJsonObject.optLong(Constants.Parser.CREATED)
        return this@VkModelWikiK
    }
}