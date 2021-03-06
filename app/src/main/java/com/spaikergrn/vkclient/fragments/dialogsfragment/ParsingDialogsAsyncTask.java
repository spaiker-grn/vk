package com.spaikergrn.vkclient.fragments.dialogsfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.SparseArray;

import com.spaikergrn.vkclient.clients.HttpUrlClient;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.tools.GetUsersHelper;
import com.spaikergrn.vkclient.tools.ParseUtils;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelDialogK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ParsingDialogsAsyncTask extends AsyncTaskLoader<List<VkModelDialogK>>  {

    private int mStartMessageId;
    private int mCount;
    private final GetUsersHelper mGetUsersHelper = new GetUsersHelper();

    ParsingDialogsAsyncTask(final Context context, final Bundle args) {
        super(context);
        if (args != null) {
           mCount = args.getInt(Constants.URL_BUILDER.COUNT);
           mStartMessageId = args.getInt(Constants.URL_BUILDER.START_MESSAGE_ID);
        }
    }

    @Override
    public List<VkModelDialogK> loadInBackground() {

        List<VkModelDialogK> vkModelDialogList = null;
        final LinkedHashSet<Integer> userId = new LinkedHashSet<>();
        final SparseArray<VkModelUser> vkModelUserMap;

        try {
            final String request = new HttpUrlClient().getRequestWithErrorCheck(VkApiMethods.getDialogs(mStartMessageId, mCount));
            final int DIALOGS_SIZE = getDialogsCount(request);
            final JSONArray itemsArray = ParseUtils.getJSONArrayItems(request);
            assert itemsArray != null;
            vkModelDialogList = new ArrayList<>();
            for (int i = 0; i < itemsArray.length(); i++) {
                final VkModelDialogK vkModelDialog = new VkModelDialogK(itemsArray.getJSONObject(i));
                vkModelDialog.setDialogsCount(DIALOGS_SIZE);
                vkModelDialogList.add(vkModelDialog);
                userId.add(vkModelDialog.getMessages().getUserId());
            }

            vkModelUserMap = mGetUsersHelper.getUsersById(new ArrayList<>(userId));
            for (int i = 0; i < vkModelDialogList.size(); i++) {
                vkModelDialogList.get(i).getMessages().setVkModelUser((vkModelUserMap.get(vkModelDialogList.get(i).getMessages().getUserId())));
            }

        } catch (final JSONException | IOException pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        }
        return vkModelDialogList;
    }

    private int getDialogsCount(final String pResponse) throws JSONException {
        final JSONObject jsonObject = new JSONObject(pResponse);
        return jsonObject.getJSONObject(Constants.Parser.RESPONSE).optInt(Constants.Parser.COUNT);
    }
}
