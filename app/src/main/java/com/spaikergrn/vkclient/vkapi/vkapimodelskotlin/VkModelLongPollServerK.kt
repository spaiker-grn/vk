package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelLongPollServerK(pJSONObject: JSONObject) : VkModelK {

    var key: String? = null
    var server: String? = null
    var ts: String? = null

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelLongPollServerK {
        val jsonObject = pJSONObject.optJSONObject(Constants.Parser.RESPONSE)
        key = jsonObject.optString(Constants.Parser.KEY)
        server = jsonObject.optString(Constants.Parser.SERVER)
        ts = jsonObject.optString(Constants.Parser.TS)
        return this@VkModelLongPollServerK
    }
}
