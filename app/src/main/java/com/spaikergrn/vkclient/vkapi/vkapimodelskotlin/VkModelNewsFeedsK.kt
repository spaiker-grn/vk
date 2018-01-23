package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import android.util.SparseArray
import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser
import org.json.JSONObject
import java.io.Serializable
import java.util.ArrayList

class VkModelNewsFeedsK(): VkModelK, Serializable {

    var vkModelNewsPosts: MutableList<VkModelNewsPostK> = ArrayList()
    var userMap = SparseArray<VkModelUser>()
    var groupMap = SparseArray<VkModelGroupK>()
    var nextFrom: String? = null

    constructor(pJSONObject: JSONObject):this(){
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelK {
        val jsonResponse = pJSONObject.getJSONObject(Constants.Parser.RESPONSE)

        val jsonArray = jsonResponse.getJSONArray(Constants.Parser.ITEMS)
        for (i in 0 until jsonArray.length()) {
            vkModelNewsPosts.add(VkModelNewsPostK(jsonArray.optJSONObject(i)))
        }

        val groups = jsonResponse.optJSONArray(Constants.Parser.GROUPS)
        (0 until groups.length())
                .map { VkModelGroupK(groups.optJSONObject(it)) }
                .forEach { groupMap.put(it.id, it) }

        val profiles = jsonResponse.optJSONArray(Constants.Parser.PROFILES)
        (0 until profiles.length())
                .map { VkModelUser(profiles.optJSONObject(it)) }
                .forEach { userMap.put(it.id, it) }

        nextFrom = jsonResponse.optString(Constants.Parser.NEXT_FROM)

        for (modelPost in vkModelNewsPosts) {
            if (modelPost.sourceID < 0) {
                modelPost.vkModelGroup = groupMap.get(Math.abs(modelPost.sourceID))
            } else {
                modelPost.vkModelUser = userMap.get(modelPost.sourceID)
            }
        }
        return this@VkModelNewsFeedsK
    }

    fun addNew(pVkModelNewsFeeds: VkModelNewsFeedsK?) {

        if (pVkModelNewsFeeds != null) {
            for (i in 0 until pVkModelNewsFeeds.userMap.size()) {
                this.userMap.put(pVkModelNewsFeeds.userMap.keyAt(i), pVkModelNewsFeeds.userMap.valueAt(i))
            }
            for (i in 0 until pVkModelNewsFeeds.groupMap.size()) {
                this.groupMap.put(pVkModelNewsFeeds.groupMap.keyAt(i), pVkModelNewsFeeds.groupMap.valueAt(i))
            }
            this.vkModelNewsPosts.addAll(pVkModelNewsFeeds.vkModelNewsPosts)
            this.nextFrom = pVkModelNewsFeeds.nextFrom
        }
    }
}