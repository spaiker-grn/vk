package com.example.myapplication.clients;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.example.myapplication.fragments.dialogsfragment.DialogsFragment;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.ParseUtils;
import com.example.myapplication.vkapi.IVkApiBuilder;
import com.example.myapplication.vkapi.VkApiBuilder;
import com.example.myapplication.vkapi.VkApiMethods;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;
import com.example.myapplication.vkapi.vkapimodels.VkModelUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ParsingDialogsAsyncTask extends AsyncTaskLoader<List<VkModelDialogs>> {

    private int mOffset;
    private int mCount;

    public ParsingDialogsAsyncTask(final Context context, final Bundle args) {
        super(context);
        if (args != null) {
            mOffset = args.getInt(DialogsFragment.OFFSET_KEY);
            mCount = args.getInt(DialogsFragment.COUNT_KEY);
        }
    }

    @Override
    public List<VkModelDialogs> loadInBackground() {

        final List<VkModelDialogs> vkModelDialogsList = new ArrayList<>();
        final Collection<Integer> usersId = new ArrayList<>();
        final SparseArray<VkModelUser> vkModelUserMap;

        try {

            final String request = VkApiMethods.getDialogs(mOffset, mCount);
            final int DIALOGS_SIZE = getCount(request);

            final JSONArray itemsArray = ParseUtils.getJSONArrayItems(request);

            assert itemsArray != null;
            for (int i = 0; i < itemsArray.length(); i++) {
                final VkModelDialogs vkModelDialogs = new VkModelDialogs(itemsArray.getJSONObject(i));
                vkModelDialogs.setDialogsCount(DIALOGS_SIZE);
                vkModelDialogsList.add(vkModelDialogs);
                usersId.add(vkModelDialogs.getMessages().getUserId());
            }

            vkModelUserMap = getUsersById(usersId);
            for (int i = 0; i < vkModelDialogsList.size(); i++) {
                vkModelDialogsList.get(i).setUser(vkModelUserMap.get(vkModelDialogsList.get(i).getMessages().getUserId()));
            }

        } catch (final InterruptedException | ExecutionException | JSONException pE) {
            Log.e(Constants.ERROR, pE.getMessage());
        }

        return vkModelDialogsList;
    }

    private int getCount(final String pResponse) throws JSONException {
        final JSONObject jsonObject = new JSONObject(pResponse);
        return jsonObject.getJSONObject(Constants.Parser.RESPONSE).optInt(Constants.Parser.COUNT);
    }

    private static SparseArray<VkModelUser> getUsersById(final Iterable<Integer> pList) throws JSONException, ExecutionException, InterruptedException {

        final Map<String, String> map = new HashMap<>();
        final IVkApiBuilder vkApi = new VkApiBuilder();
        final SparseArray<VkModelUser> vkModelUserMap = new SparseArray<>();
        final String request = TextUtils.join(",", pList);
        final String code = (Constants.VkApiMethods.GET_USERS_START + request) + Constants.VkApiMethods.GET_USERS_END;
        map.put(Constants.Parser.CODE, code);
        Log.d(Constants.MY_TAG, vkApi.buildUrl(Constants.VkApiMethods.EXECUTE, map));
        final IHttpUrlClient httpUrlClient = new HttpUrlClient();
        final String response = httpUrlClient.getRequest(vkApi.buildUrl(Constants.VkApiMethods.EXECUTE, map));
        final JSONArray itemsArray = ParseUtils.getJSONArrayItems(response);
        assert itemsArray != null;
        for (int i = 0; i < itemsArray.length(); i++) {
            final VkModelUser modelUser = new VkModelUser(itemsArray.getJSONObject(i));
            vkModelUserMap.put(modelUser.getId(), modelUser);

        }
        return vkModelUserMap;
    }

}
