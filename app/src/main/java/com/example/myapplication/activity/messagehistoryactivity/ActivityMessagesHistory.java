package com.example.myapplication.activity.messagehistoryactivity;

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

import com.example.myapplication.R;
import com.example.myapplication.fragments.recyclersutils.ILoadMore;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.VkApiMethods;
import com.example.myapplication.vkapi.vkapimodels.VkModelMessages;
import com.example.myapplication.vkapi.vkapimodels.VkModelUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ActivityMessagesHistory extends AppCompatActivity {

    public static final int HISTORY_MESSAGE_LOADER = 0;
    public RecyclerView mRecyclerView;
    public RecyclerAdapterMessageHistory mAdapter;
    public List<VkModelMessages> mVkModelMessagesList = new ArrayList<>();
    public int MESSAGES_HISTORY_SIZE;
    private ProgressBar mProgressBar;
    private int mRequestId;
    private ImageView mSendImageView;
    private ImageView mAddAttachmentImageView;
    private EditText mMessageEditText;
    private int mChatId;
    private VkModelUser mVkModelUser;
    private final Object mLock = new Object();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_history);

        initViews();

        final IntentFilter intentFilter = new IntentFilter(Constants.LONG_POLL_BROADCAST);
        registerReceiver(broadcastReceiver, intentFilter);

        final Intent intent = getIntent();
        mChatId = intent.getIntExtra(Constants.Parser.CHAT_ID, 0);
        mVkModelUser = (VkModelUser) intent.getSerializableExtra(Constants.URL_BUILDER.USER_HISTORY);
        if (mChatId != 0) {
            mRequestId = mChatId;
        } else {
            mRequestId = mVkModelUser.getId();
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.URL_BUILDER.USER_HISTORY, mRequestId);
        bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, 0);
        bundle.putInt(Constants.URL_BUILDER.COUNT, Constants.COUNT_20);

        getSupportLoaderManager().initLoader(HISTORY_MESSAGE_LOADER, bundle, mListLoaderCallbacks).forceLoad();

        mAdapter = new RecyclerAdapterMessageHistory(mRecyclerView, mVkModelMessagesList, mVkModelUser);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setILoadMore(mILoadMore);

        mSendImageView.setOnClickListener(mOnClickListener);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
        mAddAttachmentImageView = findViewById(R.id.add_attachment_image_view);
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

                        final Bundle bundle = new Bundle();
                        bundle.putInt(Constants.URL_BUILDER.USER_HISTORY, mRequestId);
                        bundle.putInt(Constants.URL_BUILDER.COUNT, Constants.COUNT_20);
                        bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, mVkModelMessagesList.get(mVkModelMessagesList.size() - 1).getId());
                        getSupportLoaderManager().restartLoader(HISTORY_MESSAGE_LOADER, bundle, mListLoaderCallbacks).forceLoad();

                    }
                });
            }
        }
    };

    private final LoaderManager.LoaderCallbacks<List<VkModelMessages>> mListLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<VkModelMessages>>() {

        @Override
        public Loader<List<VkModelMessages>> onCreateLoader(final int pId, final Bundle pArgs) {
            Loader<List<VkModelMessages>> messagesLoader = null;
            if (pId == HISTORY_MESSAGE_LOADER) {
                messagesLoader = new AsyncTaskMessageHistoryParsing(ActivityMessagesHistory.this, pArgs);
            }
            mProgressBar.setVisibility(View.VISIBLE);
            return messagesLoader;
        }

        @Override
        public void onLoadFinished(final Loader<List<VkModelMessages>> pLoader, final List<VkModelMessages> pData) {

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
        public void onLoaderReset(final Loader<List<VkModelMessages>> pLoader) {

        }

    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {

            final String text;
            text = String.valueOf(mMessageEditText.getText());
            mMessageEditText.getText().clear();
            final int requestId;

            if (text != null) {
                if (mChatId == 0) {
                    requestId = mVkModelUser.getId();
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
                            Log.e(Constants.MY_TAG, Constants.ERROR_TO_SEND_MESSAGE, pE);
                        }

                    }
                });
                thread.start();

            }

        }
    };

    class LoadLongPollMessage implements Runnable {

        String tsKey;

        LoadLongPollMessage(final String pTsKey) {
            tsKey = pTsKey;
        }

        @Override
        public void run() {

            VkModelMessages vkModelMessages = null;
            try {
                final JSONObject jsonObject = new JSONObject(VkApiMethods.getLongPollHistory(tsKey));
                vkModelMessages = new VkModelMessages(jsonObject.getJSONObject(Constants.Parser.RESPONSE).
                        getJSONObject(Constants.Parser.MESSAGES).getJSONArray(Constants.Parser.ITEMS).getJSONObject(0));
                vkModelMessages.setVkModelUser(new VkModelUser(jsonObject.getJSONObject(Constants.Parser.RESPONSE).
                        getJSONArray("profiles").getJSONObject(0)));
            } catch (JSONException | InterruptedException | ExecutionException | IOException pE) {
                Log.e(Constants.ERROR, "LoadLongPollMessageRun: ", pE);
            }

            final VkModelMessages finalVkModelMessages = vkModelMessages;
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mVkModelMessagesList.add(0, finalVkModelMessages);
                    mAdapter.notifyDataSetChanged();

                }
            });
        }

    }

}
