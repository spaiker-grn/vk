package com.spaikergrn.vkclient.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.SparseArray;

import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.tools.GetUsersHelper;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsersByIdLoader extends AsyncTaskLoader<SparseArray<VkModelUser>> {

    private final List<Integer> mUsersList;
    private final GetUsersHelper mGetUsersHelper = new GetUsersHelper();

    public UsersByIdLoader(final Context pContext, final Bundle pArgs) {
        super(pContext);
        mUsersList = pArgs.getIntegerArrayList(Constants.USERS_LIST_BUNDLE);
    }

    @Override
    public SparseArray<VkModelUser> loadInBackground() {

        try {
           return mGetUsersHelper.getUsersById(mUsersList);
        } catch (JSONException | IOException pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        }
        return null;
    }
}
