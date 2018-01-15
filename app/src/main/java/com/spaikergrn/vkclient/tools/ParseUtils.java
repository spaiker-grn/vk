package com.spaikergrn.vk_client.tools;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.spaikergrn.vk_client.clients.HttpUrlClient;
import com.spaikergrn.vk_client.clients.IHttpUrlClient;
import com.spaikergrn.vk_client.db.DbOperations;
import com.spaikergrn.vk_client.db.SQLHelper;
import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.serviceclasses.ContextHolder;
import com.spaikergrn.vk_client.vkapi.IVkApiBuilder;
import com.spaikergrn.vk_client.vkapi.VkApiBuilder;
import com.spaikergrn.vk_client.vkapi.VkApiMethods;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelMessages;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public final class ParseUtils {

    private static final String SELECTION = "_id IN (%s)";

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

    public static SparseArray<VkModelUser> getUsersById(final Collection<Integer> pList) throws JSONException, ExecutionException, InterruptedException, IOException {

        final String request = TextUtils.join(Constants.DELIMITER, pList);
        final SQLHelper sqlHelper = new SQLHelper(ContextHolder.getContext());
        final DbOperations dbOperations = new DbOperations(sqlHelper);
        SparseArray<VkModelUser> vkModelUserArr;

        vkModelUserArr = getUsersFromDB(request, dbOperations);
        if (vkModelUserArr.size() == pList.size()){
            sqlHelper.close();
            return vkModelUserArr;
        } else {
            vkModelUserArr.clear();
        }

        final Map<String, String> map = new HashMap<>();
        final IVkApiBuilder vkApi = new VkApiBuilder();
        vkModelUserArr = new SparseArray<>();
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

            insertUser(dbOperations, modelUser);
        }
        sqlHelper.close();
        return vkModelUserArr;
    }

    private static void insertUser(final DbOperations pDbOperations, final VkModelUser pModelUser) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(BaseColumns._ID, pModelUser.getId());
        contentValues.put(Constants.Parser.FIRST_NAME, pModelUser.getFirstName());
        contentValues.put(Constants.Parser.LAST_NAME, pModelUser.getLastName());
        contentValues.put(Constants.Parser.PHOTO_50, pModelUser.getPhoto50());
        contentValues.put(Constants.Parser.PHOTO_100, pModelUser.getPhoto100());
        contentValues.put(Constants.Parser.DEACTIVATED, pModelUser.getDeactivated());
        final int user = pDbOperations.insert(Constants.USERS_DB, contentValues);
        Log.d(Constants.MY_TAG, "inserted user: " + user);
    }

    private static SparseArray<VkModelUser> getUsersFromDB(final String pRequest, final DbOperations pDbOperations) {

        final SparseArray<VkModelUser> vkModelUserArrDB = new SparseArray<>();
        final Cursor cursor = pDbOperations.query(Constants.USERS_DB, String.format(SELECTION, pRequest), null, null);

        if (cursor.moveToNext()) {

            final int idColIndex = cursor.getColumnIndex(BaseColumns._ID);
            final int firstNameColIndex = cursor.getColumnIndex(Constants.Parser.FIRST_NAME);
            final int lastNameColIndex = cursor.getColumnIndex(Constants.Parser.LAST_NAME);
            final int photo50ColIndex = cursor.getColumnIndex(Constants.Parser.PHOTO_50);
            final int photo100ColIndex = cursor.getColumnIndex(Constants.Parser.PHOTO_100);
            final int deacvivatedColIndex = cursor.getColumnIndex(Constants.Parser.DEACTIVATED);

            do {
                final VkModelUser vkModelUserDB = new VkModelUser();
                vkModelUserDB.setId(cursor.getInt(idColIndex));
                vkModelUserDB.setFirstName(cursor.getString(firstNameColIndex));
                vkModelUserDB.setLastName(cursor.getString(lastNameColIndex));
                vkModelUserDB.setPhoto50(cursor.getString(photo50ColIndex));
                vkModelUserDB.setPhoto100(cursor.getString(photo100ColIndex));
                vkModelUserDB.setDeactivated(cursor.getString(deacvivatedColIndex));
                vkModelUserArrDB.put(vkModelUserDB.getId(), vkModelUserDB);
/*                Log.d(Constants.MY_TAG, "getUsersById from DB: " + vkModelUserDB.getId() + " " + vkModelUserDB.getFirstName()
                        + " " + vkModelUserDB.getLastName() + " " + vkModelUserDB.getPhoto50() + " " + vkModelUserDB.getPhoto100()
                        + " " + vkModelUserDB.getDeactivated());*/
            } while (cursor.moveToNext());
        } else {
            Log.d(Constants.MY_TAG, "0 rows");
        }
        cursor.close();
        return vkModelUserArrDB;
    }

    public static VkModelMessages getLongPollMessage(final String pTsKey) throws JSONException, InterruptedException, ExecutionException, IOException {
        final VkModelMessages vkModelMessages;
        final JSONObject jsonObject = new JSONObject(VkApiMethods.getLongPollHistory(pTsKey));
        vkModelMessages = new VkModelMessages(jsonObject.getJSONObject(Constants.Parser.RESPONSE).
                getJSONObject(Constants.Parser.MESSAGES).getJSONArray(Constants.Parser.ITEMS).getJSONObject(0));
        vkModelMessages.setVkModelUser(new VkModelUser(jsonObject.getJSONObject(Constants.Parser.RESPONSE).
                getJSONArray(Constants.Parser.PROFILES).getJSONObject(0)));
        return vkModelMessages;
    }

    public static int getDialogsCount(final String pResponse) throws JSONException {
        final JSONObject jsonObject = new JSONObject(pResponse);
        return jsonObject.getJSONObject(Constants.Parser.RESPONSE).optInt(Constants.Parser.COUNT);
    }
}
