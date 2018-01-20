package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import android.text.TextUtils
import com.spaikergrn.vkclient.serviceclasses.Constants
import com.spaikergrn.vkclient.tools.ParseUtils.parseBoolean
import com.spaikergrn.vkclient.tools.ParseUtils.parseInt
import org.json.JSONObject

class VkModelPhotoK : VkModelK {

    private var mId: Int = 0
    private var mAlbumId: Int = 0
    private var mOwnerId: Int = 0
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mText: String? = null
    private var mDate: Long = 0
    private var mPhoto75: String? = null
    private var mPhoto130: String? = null
    private var mPhoto604: String? = null
    private var mPhoto807: String? = null
    private var mPhoto1280: String? = null
    private var mPhoto2560: String? = null
    private var mUserLikes: Boolean = false
    private var mCanComment: Boolean = false
    private var mLikes: Int = 0
    private var mComments: Int = 0
    private var mTags: Int = 0
    private var mAccessKey: String? = null

    override fun parse(pJSONObject: JSONObject): VkModelK {

        mAlbumId = pJSONObject.optInt(Constants.Parser.ALBUM_ID)
        mDate = pJSONObject.optLong(Constants.Parser.DATE)
        mHeight = pJSONObject.optInt(Constants.Parser.HEIGHT)
        mWidth = pJSONObject.optInt(Constants.Parser.WIDTH)
        mOwnerId = pJSONObject.optInt(Constants.Parser.OWNER_ID)
        mId = pJSONObject.optInt(Constants.Parser.ID)
        mText = pJSONObject.optString(Constants.Parser.TEXT)
        mAccessKey = pJSONObject.optString(Constants.Parser.ACCESS_KEY)

        mPhoto75 = pJSONObject.optString(Constants.Parser.PHOTO_75)
        mPhoto130 = pJSONObject.optString(Constants.Parser.PHOTO_130)
        mPhoto604 = pJSONObject.optString(Constants.Parser.PHOTO_604)
        mPhoto807 = pJSONObject.optString(Constants.Parser.PHOTO_807)
        mPhoto1280 = pJSONObject.optString(Constants.Parser.PHOTO_1280)
        mPhoto2560 = pJSONObject.optString(Constants.Parser.PHOTO_2560)

        val jsonLikes = pJSONObject.optJSONObject(Constants.Parser.LIKES)
        mLikes = parseInt(jsonLikes, Constants.Parser.COUNT)
        mUserLikes = parseBoolean(jsonLikes, Constants.Parser.USER_LIKES)
        mComments = parseInt(pJSONObject.optJSONObject(Constants.Parser.COMMENTS), Constants.Parser.COUNT)
        mTags = parseInt(pJSONObject.optJSONObject(Constants.Parser.TAGS), Constants.Parser.COUNT)
        mCanComment = parseBoolean(pJSONObject, Constants.Parser.CAN_COMMENT)
        return this@VkModelPhotoK
    }

    fun getPhotoBySize(pSize: String): String? {

        val size = if (pSize == Constants.Parser.EMPTY_STRING) Constants.Parser.PHOTO_604 else pSize

        when (size) {
            Constants.Parser.PHOTO_1280 -> return if (!TextUtils.isEmpty(mPhoto1280)) {
                mPhoto1280
            } else {
                getPhotoBySize(Constants.Parser.PHOTO_807)
            }
            Constants.Parser.PHOTO_807 -> return if (!TextUtils.isEmpty(mPhoto807)) {
                mPhoto807
            } else {
                getPhotoBySize(Constants.Parser.PHOTO_604)
            }
            Constants.Parser.PHOTO_604 -> return if (!TextUtils.isEmpty(mPhoto604)) {
                mPhoto604
            } else {
                getPhotoBySize(Constants.Parser.PHOTO_130)
            }
            Constants.Parser.PHOTO_130 -> if (!TextUtils.isEmpty(mPhoto130)) {
                return mPhoto130
            }
        }
        return null
    }

    fun getSmallPhotoForNews(): String? {

        return when {
            (mPhoto604 != Constants.Parser.EMPTY_STRING) -> mPhoto604
            (mPhoto130 != Constants.Parser.EMPTY_STRING) -> mPhoto130
            else -> null
        }
    }
}