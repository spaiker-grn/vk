package com.spaikergrn.vkclient.tools;

import android.content.Intent;
import android.util.Log;

import com.spaikergrn.vkclient.activity.LoginActivity;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.serviceclasses.ContextHolder;

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

    public static String checkError(final String pResponse) throws JSONException {
        final JSONObject jsonObject = new JSONObject(pResponse);
        if (jsonObject.has(Constants.Parser.ERROR)) {
            if (jsonObject.getJSONObject(Constants.Parser.ERROR).optInt(Constants.Parser.ERROR_CODE) == Constants.ERROR_CODE_AUTH) {

                ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                return Constants.RELOAD;
            }
            return Constants.ERROR;
        }
        return pResponse;
    }
}