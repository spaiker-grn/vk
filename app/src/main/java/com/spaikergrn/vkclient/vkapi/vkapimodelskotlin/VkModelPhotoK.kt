package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import android.text.TextUtils
import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.tools.ParseUtils.parseBoolean
import com.spaikergrn.vkclient.tools.ParseUtils.parseInt
import org.json.JSONObject

 class VkModelPhotoK(jsonObject: JSONObject) : VkAttachmentsK.VkModelAttachments, ILikeAbleK {

    override fun getType(): String = Constants.Parser.TYPE_PHOTO

    init {
        parse(jsonObject)
    }

    var id: Int = 0
    var albumId: Int = 0
    var ownerId: Int = 0
    var width: Int = 0
    var height: Int = 0
    var text: String? = null
    var date: Long = 0
    var photo75: String? = null
    var photo130: String? = null
    var photo604: String? = null
    var photo807: String? = null
    var photo1280: String? = null
    var photo2560: String? = null
    var userLikes: Boolean = false
    var canComment: Boolean = false
    var likes: Int = 0
    var comments: Int = 0
    var tags: Int = 0
    var accessKey: String? = null

    override fun parse(pJSONObject: JSONObject) : VkModelPhotoK  {

        albumId = pJSONObject.optInt(Constants.Parser.ALBUM_ID)
        date = pJSONObject.optLong(Constants.Parser.DATE)
        height = pJSONObject.optInt(Constants.Parser.HEIGHT)
        width = pJSONObject.optInt(Constants.Parser.WIDTH)
        ownerId = pJSONObject.optInt(Constants.Parser.OWNER_ID)
        id = pJSONObject.optInt(Constants.Parser.ID)
        text = pJSONObject.optString(Constants.Parser.TEXT)
        accessKey = pJSONObject.optString(Constants.Parser.ACCESS_KEY)

        photo75 = pJSONObject.optString(Constants.Parser.PHOTO_75)
        photo130 = pJSONObject.optString(Constants.Parser.PHOTO_130)
        photo604 = pJSONObject.optString(Constants.Parser.PHOTO_604)
        photo807 = pJSONObject.optString(Constants.Parser.PHOTO_807)
        photo1280 = pJSONObject.optString(Constants.Parser.PHOTO_1280)
        photo2560 = pJSONObject.optString(Constants.Parser.PHOTO_2560)

        val jsonLikes = pJSONObject.optJSONObject(Constants.Parser.LIKES)
        likes = parseInt(jsonLikes, Constants.Parser.COUNT)
        userLikes = parseBoolean(jsonLikes, Constants.Parser.USER_LIKES)
        comments = parseInt(pJSONObject.optJSONObject(Constants.Parser.COMMENTS), Constants.Parser.COUNT)
        tags = parseInt(pJSONObject.optJSONObject(Constants.Parser.TAGS), Constants.Parser.COUNT)
        canComment = parseBoolean(pJSONObject, Constants.Parser.CAN_COMMENT)
        return this@VkModelPhotoK
    }

    fun getPhotoBySize(pSize: String): String? {

        val size = if (pSize == Constants.Parser.EMPTY_STRING) Constants.Parser.PHOTO_604 else pSize

        when (size) {
            Constants.Parser.PHOTO_1280 -> return if (!TextUtils.isEmpty(photo1280)) {
                photo1280
            } else {
                getPhotoBySize(Constants.Parser.PHOTO_807)
            }
            Constants.Parser.PHOTO_807 -> return if (!TextUtils.isEmpty(photo807)) {
                photo807
            } else {
                getPhotoBySize(Constants.Parser.PHOTO_604)
            }
            Constants.Parser.PHOTO_604 -> return if (!TextUtils.isEmpty(photo604)) {
                photo604
            } else {
                getPhotoBySize(Constants.Parser.PHOTO_130)
            }
            Constants.Parser.PHOTO_130 -> if (!TextUtils.isEmpty(photo130)) {
                return photo130
            }
        }
        return null
    }

    fun getSmallPhotoForNews(): String? {

        return when {
            (!TextUtils.isEmpty(photo604)) -> photo604
            (!TextUtils.isEmpty(photo130)) -> photo130
            else -> null
        }
    }

     override fun setUserLike(pUserLike: Boolean) {
         userLikes = pUserLike
     }

     override fun getUserLike(): Boolean {
         return userLikes
     }
}