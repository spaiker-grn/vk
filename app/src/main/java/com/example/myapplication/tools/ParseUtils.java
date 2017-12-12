package com.example.myapplication.tools;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.example.myapplication.clients.HttpUrlClient;
import com.example.myapplication.clients.IHttpUrlClient;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.IVkApiBuilder;
import com.example.myapplication.vkapi.VkApiBuilder;
import com.example.myapplication.vkapi.vkapimodels.VkModelUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    public static SparseArray<VkModelUser> getUsersById(final Iterable<Integer> pList) throws JSONException, ExecutionException, InterruptedException, IOException {

        final Map<String, String> map = new HashMap<>();
        final IVkApiBuilder vkApi = new VkApiBuilder();
        final SparseArray<VkModelUser> vkModelUserArr = new SparseArray<>();
        final String request = TextUtils.join(Constants.DELIMITER, pList);
        final String code = String.format(Constants.VkApiMethods.GET_USERS, request);
        map.put(Constants.Parser.CODE, code);
        Log.d(Constants.MY_TAG, vkApi.buildUrl(Constants.VkApiMethods.EXECUTE, map));
        final IHttpUrlClient httpUrlClient = new HttpUrlClient();
        final String response = httpUrlClient.getRequest(vkApi.buildUrl(Constants.VkApiMethods.EXECUTE, map));
        final JSONArray itemsArray = getJSONArrayItems(response);
        assert itemsArray != null;
        for (int i = 0; i < itemsArray.length(); i++) {
            final VkModelUser modelUser = new VkModelUser(itemsArray.getJSONObject(i));
            vkModelUserArr.put(modelUser.getId(), modelUser);

        }
        return vkModelUserArr;
    }
}
