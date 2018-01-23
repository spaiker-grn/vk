package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.tools.ParseUtils
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser
import org.json.JSONObject
import java.util.ArrayList

class VkModelNewsPostK(pJSONObject: JSONObject) : VkModelK, ILikeAbleK {

    var sourceID: Int = 0
    var date: Long = 0
    var postID: Int = 0
    var postType: String? = null
    var text: String? = null
    var vkAttachments: VkAttachmentsK? = null
    var commentsCount: Int = 0
    var canPostComment: Boolean = false
    var likesCount: Int = 0
    var userLikes: Boolean = false
    var canLike: Boolean = false
    var canPublish: Boolean = false
    var repostsCount: Int = 0
    var userReposted: Boolean = false
    var viewsCount: Int = 0
    var vkModelGroup: VkModelGroupK? = null
    var vkModelUser: VkModelUser? = null
    var copyHistory: MutableList<VkModelCopyHistoryPostK>? = null

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelNewsPostK {
        sourceID = pJSONObject.getInt(Constants.Parser.SOURCE_ID)
        date = pJSONObject.getLong(Constants.Parser.DATE)
        postID = pJSONObject.getInt(Constants.Parser.POST_ID)
        postType = pJSONObject.getString(Constants.Parser.POST_TYPE)
        text = pJSONObject.getString(Constants.Parser.TEXT)
        if (pJSONObject.has(Constants.Parser.ATTACHMENTS)) {
            vkAttachments = VkAttachmentsK(pJSONObject.getJSONArray(Constants.Parser.ATTACHMENTS))
        }

        val comments = pJSONObject.optJSONObject(Constants.Parser.COMMENTS)
        commentsCount = ParseUtils.parseInt(comments, Constants.Parser.COUNT)
        canPostComment = ParseUtils.parseBoolean(comments, Constants.Parser.CAN_POST)

        val likes = pJSONObject.optJSONObject(Constants.Parser.LIKES)
        likesCount = likes.optInt(Constants.Parser.COUNT)
        likesCount = ParseUtils.parseInt(likes, Constants.Parser.COUNT)
        userLikes = ParseUtils.parseBoolean(likes, Constants.Parser.USER_LIKES)
        canLike = ParseUtils.parseBoolean(likes, Constants.Parser.CAN_LIKE)
        canPublish = ParseUtils.parseBoolean(likes, Constants.Parser.CAN_PUBLISH)

        val reposts = pJSONObject.optJSONObject(Constants.Parser.REPOSTS)
        repostsCount = ParseUtils.parseInt(reposts, Constants.Parser.COUNT)
        userReposted = ParseUtils.parseBoolean(reposts, Constants.Parser.USER_REPOSTED)

        val views = pJSONObject.optJSONObject(Constants.Parser.VIEWS)
        viewsCount = ParseUtils.parseInt(views, Constants.Parser.COUNT)

        val copyHistoryArr = pJSONObject.optJSONArray(Constants.Parser.COPY_HISTORY)
        if (copyHistoryArr != null) {
            copyHistory = ArrayList()
            for (i in 0 until copyHistoryArr.length()) {
                copyHistory!!.add(VkModelCopyHistoryPostK(copyHistoryArr.optJSONObject(i)))
            }
        }
        return this@VkModelNewsPostK
    }

    override fun setUserLike(pUserLike: Boolean) {
        userLikes = pUserLike
    }

    override fun getUserLike(): Boolean {
        return userLikes
    }
}