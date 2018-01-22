package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.tools.ParseUtils
import org.json.JSONObject
import java.util.ArrayList

class VkModelPostK(pJsonObject: JSONObject): VkAttachmentsK.VkModelAttachments {

    var id: Int = 0
    var toId: Int = 0
    var fromId: Int = 0
    var date: Long = 0
    var postType: String? = null
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
    var attachments: VkAttachmentsK? = null
    var signerId: Int = 0
    var copyHistory: MutableList<VkModelPostK>? = null

    override fun getType(): String = Constants.Parser.TYPE_POST

    init {
        parse(pJsonObject)
    }

    override fun parse(pJsonObject: JSONObject): VkModelPostK {
        id = pJsonObject.optInt(Constants.Parser.ID)
        toId = pJsonObject.optInt(Constants.Parser.TO_ID)
        fromId = pJsonObject.optInt(Constants.Parser.FROM_ID)
        date = pJsonObject.optLong(Constants.Parser.DATE)
        text = pJsonObject.optString(Constants.Parser.TEXT)
        replyOwnerId = pJsonObject.optInt(Constants.Parser.REPLY_OWNER_ID)
        replyPostId = pJsonObject.optInt(Constants.Parser.REPLY_POST_ID)
        friendsOnly = ParseUtils.parseBoolean(pJsonObject, Constants.Parser.FRIENDS_ONLY)

        val comments = pJsonObject.optJSONObject(Constants.Parser.COMMENTS)
        commentsCount = comments.optInt(Constants.Parser.COUNT)
        commentsCount = ParseUtils.parseInt(comments, Constants.Parser.COUNT)
        canPostComment = ParseUtils.parseBoolean(comments, Constants.Parser.CAN_POST)

        val likes = pJsonObject.optJSONObject(Constants.Parser.LIKES)
        likesCount = ParseUtils.parseInt(likes, Constants.Parser.COUNT)
        userLikes = ParseUtils.parseBoolean(likes, Constants.Parser.USER_LIKES)
        canLike = ParseUtils.parseBoolean(likes, Constants.Parser.CAN_LIKE)
        canPublish = ParseUtils.parseBoolean(likes, Constants.Parser.CAN_PUBLISH)

        val reposts = pJsonObject.optJSONObject(Constants.Parser.REPOSTS)
        repostsCount = ParseUtils.parseInt(reposts, Constants.Parser.COUNT)
        userReposted = ParseUtils.parseBoolean(reposts, Constants.Parser.USER_REPOSTED)

        postType = pJsonObject.optString(Constants.Parser.POST_TYPE)

        if (pJsonObject.has(Constants.Parser.ATTACHMENTS)) {
            attachments = VkAttachmentsK(pJsonObject.optJSONArray(Constants.Parser.ATTACHMENTS))
        }

        signerId = pJsonObject.optInt(Constants.Parser.SIGNER_ID)

        val copyHistoryArr = pJsonObject.optJSONArray(Constants.Parser.COPY_HISTORY)
        if (copyHistoryArr != null) {
            copyHistory = ArrayList()
            for (i in 0 until copyHistoryArr.length()) {
                copyHistory!!.add(VkModelPostK(copyHistoryArr.optJSONObject(i)))
            }
        }
        return this@VkModelPostK
    }
}