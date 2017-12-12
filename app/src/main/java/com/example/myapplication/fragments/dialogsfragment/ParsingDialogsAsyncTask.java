package com.example.myapplication.fragments.dialogsfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.SparseArray;

import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.ParseUtils;
import com.example.myapplication.vkapi.VkApiMethods;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;
import com.example.myapplication.vkapi.vkapimodels.VkModelUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ParsingDialogsAsyncTask extends AsyncTaskLoader<List<VkModelDialogs>> {

    private int mStartMessageId;

    ParsingDialogsAsyncTask(final Context context, final Bundle args) {
        super(context);
        if (args != null) {

           mStartMessageId = args.getInt(Constants.URL_BUILDER.START_MESSAGE_ID);
        }
    }

    @Override
    public List<VkModelDialogs> loadInBackground() {

        final List<VkModelDialogs> vkModelDialogsList = new ArrayList<>();
        final Collection<Integer> usersId = new ArrayList<>();
        final SparseArray<VkModelUser> vkModelUserMap;

        try {

            final String request = VkApiMethods.getDialogs(mStartMessageId);
            final int DIALOGS_SIZE = getCount(request);

            final JSONArray itemsArray = ParseUtils.getJSONArrayItems(request);

            assert itemsArray != null;
            for (int i = 0; i < itemsArray.length(); i++) {
                final VkModelDialogs vkModelDialogs = new VkModelDialogs(itemsArray.getJSONObject(i));
                vkModelDialogs.setDialogsCount(DIALOGS_SIZE);
                vkModelDialogsList.add(vkModelDialogs);
                usersId.add(vkModelDialogs.getMessages().getUserId());
            }

            vkModelUserMap = ParseUtils.getUsersById(usersId);
            for (int i = 0; i < vkModelDialogsList.size(); i++) {
                vkModelDialogsList.get(i).getMessages().setVkModelUser((vkModelUserMap.get(vkModelDialogsList.get(i).getMessages().getUserId())));
            }

        } catch (final InterruptedException | ExecutionException | JSONException | IOException pE) {
            Log.e(Constants.ERROR, pE.getMessage());
        }

        return vkModelDialogsList;
    }

    private int getCount(final String pResponse) throws JSONException {
        final JSONObject jsonObject = new JSONObject(pResponse);
        return jsonObject.getJSONObject(Constants.Parser.RESPONSE).optInt(Constants.Parser.COUNT);
    }

}