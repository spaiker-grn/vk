package com.example.myapplication.activity.messagehistoryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myapplication.R;
import com.example.myapplication.fragments.recyclersutils.ILoadMore;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.vkapimodels.VkModelMessages;
import com.example.myapplication.vkapi.vkapimodels.VkModelUser;

import java.util.ArrayList;
import java.util.List;

public class ActivityMessagesHistory extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<VkModelMessages>> {

    public static final int LOADER_ID = 1;
    public RecyclerView mRecyclerView;
    public RecyclerAdapterMessageHistory mAdapter;
    public List<VkModelMessages> mVkModelMessagesList = new ArrayList<>();
    public int MESSAGES_HISTORY_SIZE;
    private ProgressBar mProgressBar;
    private int mRequestId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recycler_view);
        final Intent intent = getIntent();

        final int chatId = intent.getIntExtra(Constants.Parser.CHAT_ID, 0);

        final VkModelUser vkModelUser = (VkModelUser) intent.getSerializableExtra(Constants.URL_BUILDER.USER_HISTORY);

        if (chatId != 0) {
            mRequestId = chatId;
        } else {
            mRequestId = vkModelUser.getId();
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.URL_BUILDER.USER_HISTORY, mRequestId);
        bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, 0);

        getSupportLoaderManager().initLoader(LOADER_ID, bundle, this).forceLoad();

        mAdapter = new RecyclerAdapterMessageHistory(mRecyclerView, mVkModelMessagesList, vkModelUser);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setILoadMore(new ILoadMore() {

            @Override
            public void onLoadMore() {

                if (mVkModelMessagesList.size() < MESSAGES_HISTORY_SIZE) {
                    mRecyclerView.post(new Runnable() {

                        @Override
                        public void run() {

                            final Bundle bundle = new Bundle();
                            bundle.putInt(Constants.URL_BUILDER.USER_HISTORY, mRequestId);
                            bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, mVkModelMessagesList.get(mVkModelMessagesList.size() - 1).getId());
                            getSupportLoaderManager().restartLoader(LOADER_ID, bundle, ActivityMessagesHistory.this).forceLoad();

                        }
                    });
                }

            }
        });

    }

    @Override
    public Loader<List<VkModelMessages>> onCreateLoader(final int pId, final Bundle pArgs) {
        Loader<List<VkModelMessages>> messagesLoader = null;
        if (pId == LOADER_ID) {
            messagesLoader = new AsyncTaskMessageHistoryParsing(this, pArgs);
        }
        mProgressBar.setVisibility(View.VISIBLE);
        return messagesLoader;
    }

    @Override
    public void onLoadFinished(final Loader<List<VkModelMessages>> pLoader, final List<VkModelMessages> pData) {

        if (MESSAGES_HISTORY_SIZE == 0) {
            MESSAGES_HISTORY_SIZE = pData.get(0).getCountMessagesHistory();
        }
        mVkModelMessagesList.addAll(pData);
        mAdapter.notifyDataSetChanged();
        mAdapter.setLoaded();
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(final Loader<List<VkModelMessages>> pLoader) {

    }
}
