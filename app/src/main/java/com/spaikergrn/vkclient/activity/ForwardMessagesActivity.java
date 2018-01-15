package com.spaikergrn.vk_client.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ProgressBar;

import com.spaikergrn.vk_client.R;
import com.spaikergrn.vk_client.activity.messagehistoryactivity.RecyclerAdapterMessageHistory;
import com.spaikergrn.vk_client.loaders.LoaderUsersById;
import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelMessages;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelUser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ForwardMessagesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<SparseArray<VkModelUser>> {

    public RecyclerView mRecyclerView;
    public RecyclerAdapterMessageHistory mAdapter;
    private List<VkModelMessages> mMessagesList;

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_messages_history);
        final VkModelMessages vkModelMessage = (VkModelMessages) getIntent().getSerializableExtra(Constants.FORWARD_MESSAGES_INTENT);
        mMessagesList = vkModelMessage.getFwdMessages();
        mRecyclerView = findViewById(R.id.recycler_view);
        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapterMessageHistory(mRecyclerView, mMessagesList);
        mRecyclerView.setAdapter(mAdapter);

        final ArrayList<Integer> usersList;
        usersList = getUsersList(mMessagesList);

        final Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(Constants.USERS_LIST_BUNDLE, usersList);
        getSupportLoaderManager().initLoader(Constants.USERS_LOADER_BY_ID, bundle,this).forceLoad();

    }

    @Override
    public Loader<SparseArray<VkModelUser>> onCreateLoader(final int pId, final Bundle pArgs) {
        Loader<SparseArray<VkModelUser>> usersLoader = null;
        if (pId == Constants.USERS_LOADER_BY_ID) {
            usersLoader = new LoaderUsersById(this, pArgs);
        }
        return usersLoader;
    }

    @Override
    public void onLoadFinished(final Loader<SparseArray<VkModelUser>> pLoader, final SparseArray<VkModelUser> pData) {
        if (pData != null){
            for (int i = 0; i < mMessagesList.size(); i++){
                mMessagesList.get(i).setVkModelUser(pData.get(mMessagesList.get(i).getUserId()));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(final Loader<SparseArray<VkModelUser>> pLoader) {

    }

    public ArrayList<Integer> getUsersList(final List<VkModelMessages> pMessagesList){
        final LinkedHashSet<Integer> usersHashSet = new LinkedHashSet<>();
        for (int i = 0; i < pMessagesList.size(); i++){
            usersHashSet.add(pMessagesList.get(i).getUserId());
        }
        return new ArrayList<>(usersHashSet);
    }
}
