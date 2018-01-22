package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelChatK(pJSONObject: JSONObject): VkModelK {

    var id: Int = 0
    var type: String? = null
    var title: String? = null
    var adminId: Int = 0
    var users: Array<Int?>? = null

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelChatK {
        id = pJSONObject.optInt(Constants.Parser.ID)
        type = pJSONObject.optString(Constants.Parser.TYPE)
        title = pJSONObject.optString(Constants.Parser.TITLE)
        adminId = pJSONObject.optInt(Constants.Parser.ADMIN_ID)
        val usersArr = pJSONObject.optJSONArray(Constants.Parser.USERS)

        if (usersArr != null) {
            users = arrayOfNulls(usersArr.length())
            for (i in users!!.indices) {
                users!![i] = usersArr.optInt(i)
            }
        }
        return this@VkModelChatK
    }
}