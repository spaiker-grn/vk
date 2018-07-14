package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.tools.ParseUtils
import com.spaikergrn.vkclient.tools.ParseUtils.parseBoolean
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkImageAttachment
import org.json.JSONObject

class VkModelVideoK(jsonObject: JSONObject) : VkAttachmentsK.VkModelAttachments, VkImageAttachment {

    override fun getImageWidth(): Int {
        return width
    }

    override fun getImageHeight(): Int {
        return height
    }

    var id: Int = 0
    var ownerId: Int = 0
    var title: String? = null
    var description: String? = null
    var duration: Int = 0
    var date: Long = 0
    var views: Int = 0
    var photo130: String? = null
    var photo320: String? = null
    var photo800: String? = null
    var width: Int = 0
    var height: Int = 0
    var firstFrame130: String? = null
    var firstFrame320: String? = null
    var firstFrame800: String? = null
    var accessKey: String? = null
    var comments: Int = 0
    var canComment: Boolean = false
    var canRepost: Boolean = false
    var userLikes: Boolean = false
    var likes: Int = 0

    override fun getType(): String = Constants.Parser.TYPE_VIDEO

    init {
        parse(jsonObject)
    }

    override fun parse(pJsonObject: JSONObject): VkModelVideoK {
        id = pJsonObject.optInt(Constants.Parser.ID)
        ownerId = pJsonObject.optInt(Constants.Parser.OWNER_ID)
        title = pJsonObject.optString(Constants.Parser.TITLE)
        description = pJsonObject.optString(Constants.Parser.DESCRIPTION)
        duration = pJsonObject.optInt(Constants.Parser.DURATION)
        date = pJsonObject.optLong(Constants.Parser.DATE)
        views = pJsonObject.optInt(Constants.Parser.VIEWS)
        comments = pJsonObject.optInt(Constants.Parser.COMMENTS)
        accessKey = pJsonObject.optString(Constants.Parser.ACCESS_KEY)
        height = pJsonObject.optInt(Constants.Parser.HEIGHT)
        width = pJsonObject.optInt(Constants.Parser.WIDTH)

        val likesObj = pJsonObject.optJSONObject(Constants.Parser.LIKES)
        likes = ParseUtils.parseInt(likesObj, Constants.Parser.COUNT)
        userLikes = parseBoolean(likesObj, Constants.Parser.USER_LIKES)

        canComment = parseBoolean(pJsonObject, Constants.Parser.CAN_COMMENT)
        canRepost = parseBoolean(pJsonObject, Constants.Parser.CAN_REPOST)
        photo130 = pJsonObject.optString(Constants.Parser.PHOTO_130)
        photo320 = pJsonObject.optString(Constants.Parser.PHOTO_320)
        photo800 = pJsonObject.optString(Constants.Parser.PHOTO_800)
        firstFrame130 = pJsonObject.optString(Constants.Parser.FIRST_FRAME_130)
        firstFrame320 = pJsonObject.optString(Constants.Parser.FIRST_FRAME_320)
        firstFrame800 = pJsonObject.optString(Constants.Parser.FIRST_FRAME_800)
        return this@VkModelVideoK
    }

    private fun getFirstFrameForNews(): String? {

        if (firstFrame800 != Constants.Parser.EMPTY_STRING) {
            return firstFrame800
        } else if (firstFrame320 != Constants.Parser.EMPTY_STRING) {
            return firstFrame320
        } else if (firstFrame130 != Constants.Parser.EMPTY_STRING) {
            return firstFrame130
        }
        return null
    }

    override
    fun getSmallPhotoForNews(): String? {

        if (photo320 != Constants.Parser.EMPTY_STRING) {
            return photo320
        } else if (photo130 != Constants.Parser.EMPTY_STRING) {
            return photo130
        }
        return getFirstFrameForNews()
    }

    override
    fun getMainPhotoForNews(size: String): String? {

        if (photo800 != Constants.Parser.EMPTY_STRING) {
            return photo800
        } else if (photo320 != Constants.Parser.EMPTY_STRING) {
            return photo320
        } else if (photo130 != Constants.Parser.EMPTY_STRING) {
            return photo130
        }
        return getFirstFrameForNews()
    }


}