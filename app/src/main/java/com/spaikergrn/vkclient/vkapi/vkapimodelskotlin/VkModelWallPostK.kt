package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.tools.ParseUtils
import com.spaikergrn.vkclient.vkapi.vkapimodels.*
import org.json.JSONObject
import java.util.ArrayList

class VkModelWallPostK(pJSONObject: JSONObject) : VkAttachmentsK.VkModelAttachments {

    var id: Int = 0
    var toId: Int = 0
    var fromId: Int = 0
    var date: Long = 0
    var text: String? = null
    var replyOwnerId: Int = 0
    var replyPostId: Int = 0
    var friendsOnly: Boolean = false
    var commentsCount: Int = 0
    var canPostComment: Boolean = false
    var likesCount: Int = 0
    var userLikes: Boolean = false
    var canLike: Boolean = false
    var canPublish: Boolean = false
    var repostsCount: Int = 0
    var userReposted: Boolean = false
    var postType: String? = null
    var viewsCount: Int = 0
    var attachments: VkAttachmentsI? = null
    var signerId: Int = 0
    var copyHistory: MutableList<VkModelWallPostK> = ArrayList()
    var vkModelGroup: VkModelGroupK? = null
    var vkModelUser: VkModelUser? = null

    override fun getType(): String {
       return Constants.Parser.TYPE_POST
    }

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModel {
        id = pJSONObject.optInt(Constants.Parser.ID)
        toId = pJSONObject.optInt(Constants.Parser.TO_ID)
        fromId = pJSONObject.optInt(Constants.Parser.FROM_ID)
        date = pJSONObject.optLong(Constants.Parser.DATE)
        text = pJSONObject.optString(Constants.Parser.TEXT)
        replyOwnerId = pJSONObject.optInt(Constants.Parser.REPLY_OWNER_ID)
        replyPostId = pJSONObject.optInt(Constants.Parser.REPLY_POST_ID)
        friendsOnly = ParseUtils.parseBoolean(pJSONObject, Constants.Parser.FRIENDS_ONLY)

        val comments = pJSONObject.optJSONObject(Constants.Parser.COMMENTS)
        ParseUtils.parseInt(comments, Constants.Parser.COUNT)
        canPostComment = ParseUtils.parseBoolean(comments, Constants.Parser.CAN_POST)

        val likes = pJSONObject.optJSONObject(Constants.Parser.LIKES)
        likesCount = ParseUtils.parseInt(likes, Constants.Parser.COUNT)
        userLikes = ParseUtils.parseBoolean(likes, Constants.Parser.USER_LIKES)
        canLike = ParseUtils.parseBoolean(likes, Constants.Parser.CAN_LIKE)
        canPublish = ParseUtils.parseBoolean(likes, Constants.Parser.CAN_PUBLISH)

        val reposts = pJSONObject.optJSONObject(Constants.Parser.REPOSTS)
        repostsCount = ParseUtils.parseInt(reposts, Constants.Parser.COUNT)
        userReposted = ParseUtils.parseBoolean(reposts, Constants.Parser.USER_REPOSTED)

        postType = pJSONObject.optString(Constants.Parser.POST_TYPE)

        if (pJSONObject.has(Constants.Parser.ATTACHMENTS)) {
            attachments = VkAttachmentsK(pJSONObject.optJSONArray(Constants.Parser.ATTACHMENTS))
        }
        signerId = pJSONObject.optInt(Constants.Parser.SIGNER_ID)

        val copyHistoryArr = pJSONObject.optJSONArray(Constants.Parser.COPY_HISTORY)
        if (copyHistoryArr != null) {
            for (i in 0 until copyHistoryArr.length()) {
                copyHistory.add(VkModelWallPostK(copyHistoryArr.optJSONObject(i)))
            }
        }
        return this@VkModelWallPostK
    }
}