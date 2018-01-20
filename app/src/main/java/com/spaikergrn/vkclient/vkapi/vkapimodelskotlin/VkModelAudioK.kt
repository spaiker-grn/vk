package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelAudioK(pJSONObject: JSONObject) : VkModelK {

    private var mId: Int = 0
    private var mOwnerId: Int = 0
    private var mArtist: String? = null
    private var mTitle: String? = null
    private var mDuration: Int = 0
    private var mUrl: String? = null
    private var mLyricsId: Int = 0
    private var mAlbumId: Int = 0
    private var mGenre: Int = 0
    private var mAccessKey: String? = null

    override fun parse(pJSONObject: JSONObject): VkModelAudioK {
        mId = pJSONObject.optInt(Constants.Parser.ID)
        mOwnerId = pJSONObject.optInt(Constants.Parser.OWNER_ID)
        mArtist = pJSONObject.optString(Constants.Parser.ARTIST)
        mTitle = pJSONObject.optString(Constants.Parser.TITLE)
        mDuration = pJSONObject.optInt(Constants.Parser.DURATION)
        mUrl = pJSONObject.optString(Constants.Parser.URL)
        mLyricsId = pJSONObject.optInt(Constants.Parser.LYRICS_ID)
        mAlbumId = pJSONObject.optInt(Constants.Parser.ALBUM_ID)
        mGenre = pJSONObject.optInt(Constants.Parser.GENRE_ID)
        mAccessKey = pJSONObject.optString(Constants.Parser.ACCESS_KEY)
        return this@VkModelAudioK
    }

    init {
        parse(pJSONObject)
    }
}