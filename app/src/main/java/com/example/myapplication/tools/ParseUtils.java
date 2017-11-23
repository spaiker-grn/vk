package com.example.myapplication.tools;

import android.util.Log;

import com.example.myapplication.serviceclasses.Constants;

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

        if (jsonObject.has("response")) {

            final JSONObject jsonResponse = jsonObject.getJSONObject("response");
            return jsonResponse.getJSONArray("items");

        } else {
            if (jsonObject.has("error")) {
                final JSONObject jsonError = jsonObject.getJSONObject("error");
                Log.d(Constants.MY_TAG, jsonError.optString("error_msg"));
            } else {
                Log.d(Constants.MY_TAG, "Unknown json response ");
            }

        }
        return null;
    }



}
