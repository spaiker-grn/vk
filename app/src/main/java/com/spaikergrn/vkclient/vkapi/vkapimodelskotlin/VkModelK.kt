package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import org.json.JSONException
import org.json.JSONObject

interface VkModelK {
    @Throws(JSONException::class)
     fun parse(pJSONObject: JSONObject): VkModelK
}