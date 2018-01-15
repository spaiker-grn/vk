package com.spaikergrn.vkclient.tools;

import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ParseUtils {

    public static boolean parseBoolean(final JSONObject pFrom, final String pName) {
        return pFrom != null && pFrom.optInt(pName, 0) == 1;
    }

    public static int parseInt(final JSONObject pFrom, final String pName) {
        if (pFrom == null) {
            return 0;
        }
        return pFrom.optInt(pName, 0);
    }

    public static JSONArray getJSONArrayItems(final String pResponse) throws JSONException {

        final JSONObject jsonObject = new JSONObject(pResponse);

        if (jsonObject.has(Constants.Parser.RESPONSE)) {

            final JSONObject jsonResponse = jsonObject.getJSONObject(Constants.Parser.RESPONSE);
            return jsonResponse.getJSONArray(Constants.Parser.ITEMS);

        } else {
            if (jsonObject.has(Constants.Parser.ERROR)) {
                final JSONObject jsonError = jsonObject.getJSONObject(Constants.Parser.ERROR);
                Log.d(Constants.MY_TAG, jsonError.optString(Constants.Parser.ERROR_MSG));
            } else {
                Log.d(Constants.MY_TAG, Constants.UNKNOWN_JSON_RESPONSE);
            }

        }
        return null;
    }
}