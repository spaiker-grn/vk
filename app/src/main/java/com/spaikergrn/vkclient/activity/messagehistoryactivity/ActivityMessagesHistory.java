package com.spaikergrn.vkclient.activity.messagehistoryactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.fragments.recyclersutils.ILoadMore;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelUser;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelMessagesK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ActivityMessagesHistory extends AppCompatActivity {

    public RecyclerView mRecyclerView;
    public RecyclerAdapterMessageHistory mAdapter;
    public List<VkModelMessagesK> mVkModelMessagesList = new ArrayList<>();
    public int MESSAGES_HISTORY_SIZE;
    private ProgressBar mProgressBar;
    private int mRequestId;
    private ImageView mSendImageView;
    private EditText mMessageEditText;
    private int mChatId;
    private final Object mLock = new Object();
    private int mUserHistoryId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_history);

        initViews();

        final IntentFilter intentFilter = new IntentFilter(Constants.LONG_POLL_BROADCAST);
        registerReceiver(broadcastReceiver, intentFilter);

        initRequestId();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        final Bundle bundle = setBundle(0, Constants.COUNT_20);

        getSupportLoaderManager().initLoader(Constants.LoadersKeys.HISTORY_MESSAGE_LOADER_ID, bundle, mListLoaderCallbacks).forceLoad();

        mAdapter = new RecyclerAdapterMessageHistory(mRecyclerView, mVkModelMessagesList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setILoadMore(mILoadMore);

        mSendImageView.setOnClickListener(mSendMessageOnClickListener);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

     private Bundle setBundle(final int pValue, final int pCount) {
        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.URL_BUILDER.USER_HISTORY, mRequestId);
        bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, pValue);
        bundle.putInt(Constants.URL_BUILDER.COUNT, pCount);
        return bundle;
    }

    private void initRequestId() {
        final Intent intent = getIntent();
        mChatId = intent.getIntExtra(Constants.Parser.CHAT_ID, 0);
        mUserHistoryId =  intent.getIntExtra(Constants.Parser.USER_ID, 0);
        if (mChatId != 0) {
            mRequestId = mChatId;
        } else {
            mRequestId = mUserHistoryId;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mMessageEditText = findViewById(R.id.send_message_edit_text);
        mSendImageView = findViewById(R.id.send_image_view);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //broadcast for update MessagesHistory from LongPoll response

        @Override
        public void onReceive(final Context pContext, final Intent pIntent) {
            if (mVkModelMessagesList != null) {
                final int userId = pIntent.getIntExtra(Constants.Parser.USER_ID, 0);

                final String mtsKey = pIntent.getStringExtra(Constants.MTS_KEY);

                if (mVkModelMessagesList.get(0).getUserId() == userId) {
                    final Thread thread = new Thread(new LoadLongPollMessage(mtsKey));
                    thread.start();
                }
            }
        }
    };

    ILoadMore mILoadMore = new ILoadMore() {

        @Override
        public void onLoadMore() {
            if (mVkModelMessagesList.size() < MESSAGES_HISTORY_SIZE) {
                mRecyclerView.post(new Runnable() {

                    @Override
                    public void run() {

                        final Bundle bundle = setBundle(mVkModelMessagesList.get(mVkModelMessagesList.size() - 1).getId(), Constants.COUNT_20);
                        getSupportLoaderManager().restartLoader(Constants.LoadersKeys.HISTORY_MESSAGE_LOADER_ID, bundle, mListLoaderCallbacks).forceLoad();
                    }
                });
            }
        }
    };

    private final LoaderManager.LoaderCallbacks<List<VkModelMessagesK>> mListLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<VkModelMessagesK>>() {

        @Override
        public Loader<List<VkModelMessagesK>> onCreateLoader(final int pId, final Bundle pArgs) {
            Loader<List<VkModelMessagesK>> messagesLoader = null;
            if (pId == Constants.LoadersKeys.HISTORY_MESSAGE_LOADER_ID) {
                messagesLoader = new AsyncTaskMessageHistoryParsing(ActivityMessagesHistory.this, pArgs);
            }
            mProgressBar.setVisibility(View.VISIBLE);
            return messagesLoader;
        }

        @Override
        public void onLoadFinished(final Loader<List<VkModelMessagesK>> pLoader, final List<VkModelMessagesK> pData) {

            if (MESSAGES_HISTORY_SIZE == 0) {
                MESSAGES_HISTORY_SIZE = pData.get(0).getCountMessagesHistory();
            }

            synchronized (mLock) {
                mVkModelMessagesList.addAll(pData);
                mAdapter.notifyDataSetChanged();
            }
            mAdapter.setLoaded();
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onLoaderReset(final Loader<List<VkModelMessagesK>> pLoader) {

        }
    };

    View.OnClickListener mSendMessageOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {

            final String text;
            text = String.valueOf(mMessageEditText.getText());
            mMessageEditText.getText().clear();
            final int requestId;

            if (!text.equals(Constants.Parser.EMPTY_STRING)) {
                if (mChatId == 0) {
                    requestId = mUserHistoryId;
                } else {
                    requestId = mChatId;
                }

                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            final String response = VkApiMethods.sendMessage(requestId, text);
                            final JSONObject jsonResponse = new JSONObject(response);
                            if (!jsonResponse.has(Constants.Parser.RESPONSE)) {
                                Toast.makeText(ActivityMessagesHistory.this, Constants.ERROR_TO_SEND_MESSAGE, Toast.LENGTH_SHORT).show();
                            }
                        } catch (InterruptedException | ExecutionException | IOException | JSONException pE) {
                            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
                        }
                    }
                });
                thread.start();
            }
        }
    };

    class LoadLongPollMessage implements Runnable {

        String mTsKey;
        private VkModelMessagesK mVkModelMessages;

        LoadLongPollMessage(final String pTsKey) {
            mTsKey = pTsKey;
        }

        @Override
        public void run() {

            try {
                mVkModelMessages = getLongPollMessage(mTsKey);
            } catch (JSONException | InterruptedException | ExecutionException | IOException pE) {
                Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
            }

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    synchronized (mLock){
                        mVkModelMessagesList.add(0, mVkModelMessages);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        VkModelMessagesK getLongPollMessage(final String pTsKey) throws JSONException, InterruptedException, ExecutionException, IOException {
            final VkModelMessagesK vkModelMessages;
            final JSONObject jsonObject = new JSONObject(VkApiMethods.getLongPollHistory(pTsKey));
            vkModelMessages = new VkModelMessagesK(jsonObject.getJSONObject(Constants.Parser.RESPONSE).
                    getJSONObject(Constants.Parser.MESSAGES).getJSONArray(Constants.Parser.ITEMS).getJSONObject(0));
            vkModelMessages.setVkModelUser(new VkModelUser(jsonObject.getJSONObject(Constants.Parser.RESPONSE).
                    getJSONArray(Constants.Parser.PROFILES).getJSONObject(0)));
            return vkModelMessages;
        }
    }
}
