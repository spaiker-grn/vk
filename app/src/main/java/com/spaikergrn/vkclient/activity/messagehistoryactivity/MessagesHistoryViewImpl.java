package com.spaikergrn.vkclient.activity.messagehistoryactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.activity.messagehistoryactivity.messageshistorycontract.MessagesHistoryView;
import com.spaikergrn.vkclient.activity.messagehistoryactivity.messageshistorycontract.MessagesHistoryViewPresenter;
import com.spaikergrn.vkclient.fragments.recyclersutils.ILoadMore;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelMessagesK;

import java.util.ArrayList;
import java.util.List;

public class MessagesHistoryViewImpl extends AppCompatActivity implements MessagesHistoryView {

    public RecyclerView mRecyclerView;
    public RecyclerAdapterMessageHistory mAdapter;
    public List<VkModelMessagesK> mVkModelMessagesList = new ArrayList<>();
    public int MESSAGES_HISTORY_SIZE;
    private ProgressBar mProgressBar;
    private int mRequestId;
    private EditText mMessageEditText;
    private final Object mLock = new Object();
    private MessagesHistoryViewPresenter mPresenter;

    private final View.OnClickListener mSendMessageClickListener = new View.OnClickListener() {

        @Override
        public void onClick(final View pView) {
            final String text = String.valueOf(mMessageEditText.getText());

            if (!text.isEmpty()) {
                mMessageEditText.getText().clear();
                mPresenter.sendMessage(mRequestId, text);
            }
        }
    };

    public static void start(final Context pContext, final int pRequestId) {
        final Intent intent = new Intent(pContext, MessagesHistoryViewImpl.class);
        intent.putExtra(Constants.Parser.CHAT_ID, pRequestId);
        pContext.startActivity(intent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_history);

        initViews();
        registerLongPollReceiver();

        mRequestId = getIntent().getIntExtra(Constants.Parser.CHAT_ID, 0);

        mPresenter = new MessagesHistoryViewPresenterImpl(this);
        mPresenter.getMessages(mRequestId, 0, Constants.COUNT_20);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    public void onMessageHistoryLoaded(final List<VkModelMessagesK> pVkModelMessages) {
        if (MESSAGES_HISTORY_SIZE == 0) {
            MESSAGES_HISTORY_SIZE = pVkModelMessages.get(0).getCountMessagesHistory();
        }
        synchronized (mLock) {
            mVkModelMessagesList.addAll(pVkModelMessages);
            mAdapter.notifyDataSetChanged();
        }
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorSendMessageToast() {
        Toast.makeText(this, Constants.ERROR_TO_SEND_MESSAGE, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(final Throwable pThrowable) {
        Log.e(getClass().getSimpleName(), Constants.ERROR_TO_SEND_MESSAGE, pThrowable);
    }

    @Override
    public void onLongPollMessageLoaded(final VkModelMessagesK pVkModelMessagesK) {
        synchronized (mLock) {
            mVkModelMessagesList.add(0, pVkModelMessagesK);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void registerLongPollReceiver() {
        final IntentFilter intentFilter = new IntentFilter(Constants.LONG_POLL_BROADCAST);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapterMessageHistory(mRecyclerView, mVkModelMessagesList);
        mAdapter.setILoadMore(mILoadMore);
        mRecyclerView.setAdapter(mAdapter);

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);

        mMessageEditText = findViewById(R.id.send_message_edit_text);

        findViewById(R.id.send_image_view).setOnClickListener(mSendMessageClickListener);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //broadcast for update MessagesHistory from LongPoll response

        @Override
        public void onReceive(final Context pContext, final Intent pIntent) {
            if (mVkModelMessagesList != null) {
                final int userId = pIntent.getIntExtra(Constants.Parser.USER_ID, 0);

                final String mtsKey = pIntent.getStringExtra(Constants.MTS_KEY);

                if (mVkModelMessagesList.get(0).getUserId() == userId) {
                    mPresenter.getLongPollMessage(mtsKey);
                }
            }
        }
    };

    ILoadMore mILoadMore = new ILoadMore() {

        @Override
        public void onLoadMore() {
            if (mVkModelMessagesList.size() < MESSAGES_HISTORY_SIZE) {

                mProgressBar.setVisibility(View.VISIBLE);
                mPresenter.getMessages(mRequestId, mVkModelMessagesList.get(mVkModelMessagesList.size() - 1).getId(), Constants.COUNT_20);

            }
        }
    };

}
