package com.example.myapplication.clients;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.example.myapplication.fragments.DialogsFragment.DialogsFragment;
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

    private int DIALOGS_SIZE;
    private String mRequest;
    private int mOffset;
    private int mCount;


    public ParsingDialogsAsyncTask(final Context context, final Bundle args) {
        super(context);
        if (args != null){
            //mRequest = args.getString(DialogsFragment.REQUEST);
            mOffset = args.getInt(DialogsFragment.OFFSET_KEY);
            mCount = args.getInt(DialogsFragment.COUNT_KEY);
        }
        Log.d("My_tag", "Create Loader");
    }

    @Override
    public List<VkModelDialogs> loadInBackground() {

        Log.d("My_tag", "Loader");

        final List<VkModelDialogs> vkModelDialogsList = new ArrayList<>();
        final Collection<Integer> usersId = new ArrayList<>();
        final SparseArray<VkModelUser> vkModelUserMap;


        try {


            mRequest = VkApiMethods.getDialogs(mOffset, mCount);
            DIALOGS_SIZE = getCount(mRequest);

            final JSONArray itemsArray = ParseUtils.getJSONArrayItems(mRequest);

            assert itemsArray != null;
            for (int i = 0; i < itemsArray.length(); i++) {
                final VkModelDialogs vkModelDialogs = new VkModelDialogs(itemsArray.getJSONObject(i));
                vkModelDialogs.dialogs_count = DIALOGS_SIZE;
                vkModelDialogsList.add(vkModelDialogs);
                usersId.add(vkModelDialogs.message.user_id);
            }


            vkModelUserMap = getUsersById(usersId);
            for (int i = 0; i < vkModelDialogsList.size(); i++) {
                vkModelDialogsList.get(i).setUser(vkModelUserMap.get(vkModelDialogsList.get(i).message.user_id));
            }

        } catch (final InterruptedException | ExecutionException | JSONException pE) {
            Log.e(Constants.ERROR, pE.getMessage());
            pE.printStackTrace();
        }

        return vkModelDialogsList;
    }

        int getCount(final String pResponse) throws JSONException {
        final JSONObject jsonObject = new JSONObject(pResponse);
        return jsonObject.getJSONObject("response").optInt("count");
    }

    private static SparseArray<VkModelUser> getUsersById(final Iterable<Integer> pList) throws JSONException, ExecutionException, InterruptedException {

        final Map<String, String> map = new HashMap<>();
        final IVkApiBuilder vkApi = new VkApiBuilder();
        final SparseArray<VkModelUser> vkModelUserMap = new SparseArray<>();
        final String request = TextUtils.join(",", pList);
        final String code = (VkApiBuilder.GET_USERS_START + request) + VkApiBuilder.GET_USERS_END;
        map.put("code", code);
        Log.d(Constants.MY_TAG, code  /*vkApi.buildUrl(VkApiBuilder.EXECUTE, map)*/);
        Log.d(Constants.MY_TAG, vkApi.buildUrl(VkApiBuilder.EXECUTE, map));
        final IHttpUrlClient httpUrlClient = new HttpUrlClient();
        final String response = httpUrlClient.getRequest(vkApi.buildUrl(VkApiBuilder.EXECUTE, map));
        final JSONArray itemsArray = ParseUtils.getJSONArrayItems(response);
        assert itemsArray != null;
        for (int i = 0; i < itemsArray.length(); i++) {
            final VkModelUser modelUser = new VkModelUser(itemsArray.getJSONObject(i));
            vkModelUserMap.put(modelUser.id,modelUser);

        }
        return vkModelUserMap;
    }

}
