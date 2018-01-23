package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.tools.ParseUtils
import org.json.JSONObject

class VkModelGroupK(pJSONObject: JSONObject):VkModelK {

    var id: Int = 0
    var name: String? = null
    var screenName: String? = null
    var photo50: String? = null
    var photo100: String? = null
    var isMember: Boolean = false
    var isAdmin: Boolean = false
    var deactivated: String? = null

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelGroupK {
        id = pJSONObject.optInt(Constants.Parser.ID)
        name = pJSONObject.optString(Constants.Parser.NAME)
        screenName = pJSONObject.getString(Constants.Parser.SCREEN_NAME)
        photo50 = pJSONObject.optString(Constants.Parser.PHOTO_50)
        photo100 = pJSONObject.optString(Constants.Parser.PHOTO_100)
        isMember = ParseUtils.parseBoolean(pJSONObject, Constants.Parser.IS_MEMBER)
        isAdmin = ParseUtils.parseBoolean(pJSONObject, Constants.Parser.IS_ADMIN)
        if (pJSONObject.has(Constants.Parser.DEACTIVATED)) {
            deactivated = pJSONObject.optString(Constants.Parser.DEACTIVATED)
        }
        return this@VkModelGroupK
    }
}