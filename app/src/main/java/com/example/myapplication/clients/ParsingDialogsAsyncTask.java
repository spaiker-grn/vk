package com.example.myapplication.clients;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.ParseUtils;
import com.example.myapplication.vkapi.IVkApiBuilder;
import com.example.myapplication.vkapi.VkApiBuilder;
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

public class ParsingDialogsAsyncTask extends AsyncTask<String, String, List<VkModelDialogs>> {

    @Override
    protected List<VkModelDialogs> doInBackground(final String... pStrings) {

        final List<VkModelDialogs> vkModelDialogsList = new ArrayList<>();
        final Collection<Integer> usersId = new ArrayList<>();
        final List<VkModelUser> vkModelUserList;

        try {

            final JSONArray itemsArray = ParseUtils.getJSONArrayItems(pStrings[0]);

            assert itemsArray != null;
            for (int i = 0; i < itemsArray.length(); i++) {
                final VkModelDialogs vkModelDialogs = new VkModelDialogs(itemsArray.getJSONObject(i));
                vkModelDialogsList.add(vkModelDialogs);
                usersId.add(vkModelDialogs.message.user_id);
            }

            vkModelUserList = getUsersById(usersId);
            for (int i = 0; i < vkModelUserList.size(); i++) {
                vkModelDialogsList.get(i).setUser(vkModelUserList.get(i));
            }

        } catch (final InterruptedException | ExecutionException | JSONException pE) {
            Log.e(Constants.ERROR, pE.getMessage());
            pE.printStackTrace();
        }

        return vkModelDialogsList;
    }

    private static List<VkModelUser> getUsersById(final Iterable<Integer> pList) throws JSONException, ExecutionException, InterruptedException {

        final Map<String, String> map = new HashMap<>();
        final IVkApiBuilder vkApi = new VkApiBuilder();
        final List<VkModelUser> vkModelUserList = new ArrayList<>();
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
            vkModelUserList.add(modelUser);
        }
        return vkModelUserList;
    }

}
