package com.spaikergrn.vkclient.vkapi.vkapimodelskotlin

import com.spaikergrn.vkclient.serviceclasses.Constants
import org.json.JSONObject

class VkModelProfileK(pJSONObject: JSONObject) : VkModelK {

    var mFirstName: String? = null
    var mLastName: String? = null
    var mScreenname: String? = null
    var mSex: Int = 0
    var mRelation: Int = 0
    var mBDate: String? = null
    var mBDateVisibility: Int = 0
    var mHomeTown: String? = null
    var mCountryId: Int = 0
    var mCountryTitle: String? = null
    var mCityId: Int = 0
    var mCityTitle: String? = null
    var mStatus: String? = null
    var mPhone: String? = null

    init {
        parse(pJSONObject)
    }

    override fun parse(pJSONObject: JSONObject): VkModelProfileK {
        mFirstName = pJSONObject.optString(Constants.Parser.FIRST_NAME)
        mLastName = pJSONObject.optString(Constants.Parser.LAST_NAME)
        mScreenname = pJSONObject.optString(Constants.Parser.SCREEN_NAME)
        mSex = pJSONObject.optInt(Constants.Parser.SEX)
        mRelation = pJSONObject.optInt(Constants.Parser.RELATION)
        mBDate = pJSONObject.optString(Constants.Parser.BDATE)
        mBDateVisibility = pJSONObject.optInt(Constants.Parser.BDATE_VISIBILITY)
        mHomeTown = pJSONObject.optString(Constants.Parser.HOME_TOWN)
        val country = pJSONObject.optJSONObject(Constants.Parser.COUNTRY)
        if (country != null) {
            mCountryId = country.optInt(Constants.Parser.ID)
            mCountryTitle = country.optString(Constants.Parser.TITLE)
        }
        val city = pJSONObject.optJSONObject(Constants.Parser.CITY)
        if (city != null) {
            mCityId = city.optInt(Constants.Parser.ID)
            mCityTitle = city.optString(Constants.Parser.TITLE)
        }
        mStatus = pJSONObject.optString(Constants.Parser.STATUS)
        mPhone = pJSONObject.optString(Constants.Parser.PHONE)
        return this@VkModelProfileK
    }
}