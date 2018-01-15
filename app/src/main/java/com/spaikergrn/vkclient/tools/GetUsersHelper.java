package com.spaikergrn.vkclient.tools;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spaikergrn.vkclient.clients.HttpUrlClient;
import com.spaikergrn.vkclient.clients.IHttpUrlClient;
import com.spaikergrn.vkclient.db.DbOperations;
import com.spaikergrn.vkclient.db.SQLHelper;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.serviceclasses.ContextHolder;
import com.spaikergrn.vkclient.vkapi.IVkApiBuilder;
import com.spaikergrn.vkclient.vkapi.VkApiBuilder;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public final class GetUsersHelper {

    private static final String SELECTION = "_id IN (%s)";
    private static final int WAITING_TIME_MILLIS = 5000;
    private final Object mLock = new Object();

    public SparseArray<VkModelUser> getUsersById(final List<Integer> pList) throws JSONException, ExecutionException, InterruptedException, IOException {

        final String request = TextUtils.join(Constants.DELIMITER, pList);
        final SQLHelper sqlHelper = new SQLHelper(ContextHolder.getContext());
        final DbOperations dbOperations = new DbOperations(sqlHelper);
        SparseArray<VkModelUser> vkModelUserArr;
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference fireBaseDatabaseReference = firebaseDatabase.getReference("VkModelUser");

        vkModelUserArr = getUsersFromDB(request, dbOperations);
        if (checkArrayIntegrity(pList, vkModelUserArr)) {
            return vkModelUserArr;
        }

        vkModelUserArr = getUsersFromFireBase(pList);

        if (checkArrayIntegrity(pList, vkModelUserArr)) {
            return vkModelUserArr;
        }

        final Map<String, String> map = new HashMap<>();
        final IVkApiBuilder vkApi = new VkApiBuilder();
        vkModelUserArr = new SparseArray<>();
        final String code = String.format(Constants.VkApiMethods.GET_USERS, request);
        map.put(Constants.Parser.CODE, code);
        Log.d(Constants.MY_TAG, vkApi.buildUrl(Constants.VkApiMethods.EXECUTE, map));
        final IHttpUrlClient httpUrlClient = new HttpUrlClient();
        final String response = httpUrlClient.getRequest(vkApi.buildUrl(Constants.VkApiMethods.EXECUTE, map));
        final JSONArray itemsArray = ParseUtils.getJSONArrayItems(response);

        assert itemsArray != null;

        for (int i = 0; i < itemsArray.length(); i++) {
            final VkModelUser modelUser = new VkModelUser(itemsArray.getJSONObject(i));
            vkModelUserArr.put(modelUser.getId(), modelUser);

            insertUserToDB(dbOperations, modelUser);

            fireBaseDatabaseReference.child(String.valueOf(modelUser.getId())).setValue(modelUser);
        }
        sqlHelper.close();
        return vkModelUserArr;
    }

    private boolean checkArrayIntegrity(final Collection<Integer> pList, final SparseArray<VkModelUser> pVkModelUserArr) {
        if (pVkModelUserArr.size() == pList.size()) {
            return true;
        } else {
            pVkModelUserArr.clear();
        }
        return false;
    }

    private SparseArray<VkModelUser> getUsersFromFireBase(final List<Integer> pList) throws InterruptedException {

        final SparseArray<VkModelUser> userSparseArray = new SparseArray<>();
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference fireBaseDatabaseReference = firebaseDatabase.getReference(Constants.VKMODELUSER_FIREBASE);

        for (int i = 0; i < pList.size(); i++) {

            fireBaseDatabaseReference.child(String.valueOf(pList.get(i))).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(final DataSnapshot pDataSnapshot) {
                    final VkModelUser vkModelUser = pDataSnapshot.getValue(VkModelUser.class);
                    if (vkModelUser != null) {
                        userSparseArray.put(vkModelUser.getId(), vkModelUser);
                        Log.d(Constants.MY_TAG, "get users from FIREBASE: " + vkModelUser.getId() + " " + vkModelUser.getFullName());
                        if( userSparseArray.size() == pList.size()){
                            synchronized (mLock)
                            {
                                mLock.notifyAll();
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(final DatabaseError pDatabaseError) {
                }
            });
        }
        synchronized (mLock) {
            mLock.wait(WAITING_TIME_MILLIS);
        }
        return userSparseArray;
    }

    private void insertUserToDB(final DbOperations pDbOperations, final VkModelUser pModelUser) {

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

    private SparseArray<VkModelUser> getUsersFromDB(final String pRequest, final DbOperations pDbOperations) {

        final SparseArray<VkModelUser> vkModelUserArrDB = new SparseArray<>();
        final Cursor cursor = pDbOperations.query(Constants.USERS_DB, String.format(SELECTION, pRequest), null, null);

        if (cursor.moveToNext()) {

            final int idColIndex = cursor.getColumnIndex(BaseColumns._ID);
            final int firstNameColIndex = cursor.getColumnIndex(Constants.Parser.FIRST_NAME);
            final int lastNameColIndex = cursor.getColumnIndex(Constants.Parser.LAST_NAME);
            final int photo50ColIndex = cursor.getColumnIndex(Constants.Parser.PHOTO_50);
            final int photo100ColIndex = cursor.getColumnIndex(Constants.Parser.PHOTO_100);
            final int deactivatedColIndex = cursor.getColumnIndex(Constants.Parser.DEACTIVATED);

            do {
                final VkModelUser vkModelUserDB = new VkModelUser();
                vkModelUserDB.setId(cursor.getInt(idColIndex));
                vkModelUserDB.setFirstName(cursor.getString(firstNameColIndex));
                vkModelUserDB.setLastName(cursor.getString(lastNameColIndex));
                vkModelUserDB.setPhoto50(cursor.getString(photo50ColIndex));
                vkModelUserDB.setPhoto100(cursor.getString(photo100ColIndex));
                vkModelUserDB.setDeactivated(cursor.getString(deactivatedColIndex));
                vkModelUserArrDB.put(vkModelUserDB.getId(), vkModelUserDB);

            } while (cursor.moveToNext());
        } else {
            Log.d(Constants.MY_TAG, "0 rows");
        }
        cursor.close();
        return vkModelUserArrDB;
    }
}
