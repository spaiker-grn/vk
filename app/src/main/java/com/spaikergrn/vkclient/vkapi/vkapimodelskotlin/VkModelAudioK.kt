package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelAudioK(pJSONObject: JSONObject) : VkAttachmentsK.VkModelAttachments{

    override fun getType(): String {
        return Constants.Parser.TYPE_AUDIO
    }

    var id: Int = 0
    var ownerId: Int = 0
    var artist: String? = null
    var title: String? = null
    var duration: Int = 0
    var url: String? = null
    var lyricsId: Int = 0
    var albumId: Int = 0
    var genre: Int = 0
    var accessKey: String? = null

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelAudioK {
        id = pJSONObject.optInt(Constants.Parser.ID)
        ownerId = pJSONObject.optInt(Constants.Parser.OWNER_ID)
        artist = pJSONObject.optString(Constants.Parser.ARTIST)
        title = pJSONObject.optString(Constants.Parser.TITLE)
        duration = pJSONObject.optInt(Constants.Parser.DURATION)
        url = pJSONObject.optString(Constants.Parser.URL)
        lyricsId = pJSONObject.optInt(Constants.Parser.LYRICS_ID)
        albumId = pJSONObject.optInt(Constants.Parser.ALBUM_ID)
        genre = pJSONObject.optInt(Constants.Parser.GENRE_ID)
        accessKey = pJSONObject.optString(Constants.Parser.ACCESS_KEY)
        return this@VkModelAudioK
    }
}