package com.spaikergrn.vk_client.fragments.dialogsfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spaikergrn.vk_client.R;
import com.spaikergrn.vk_client.activity.messagehistoryactivity.ActivityMessagesHistory;
import com.spaikergrn.vk_client.fragments.IAdapterRefresh;
import com.spaikergrn.vk_client.fragments.recyclersutils.ILoadMore;
import com.spaikergrn.vk_client.fragments.recyclersutils.OnItemClickListener;
import com.spaikergrn.vk_client.fragments.recyclersutils.RecyclerItemClickListener;
import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.vkapi.vkapimodels.VkModelDialog;

import java.util.ArrayList;
import java.util.List;

public class DialogsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<VkModelDialog>>, IAdapterRefresh {

    public static final int LOADER_ID = 1;
    public static final int VALUE_40 = 40;
    public RecyclerView mRecyclerView;
    public RecyclerAdapterDialogs mAdapter;
    public List<VkModelDialog> mVkModelDialogList = new ArrayList<>();
    public int DIALOGS_SIZE;
    public ProgressBar mProgressBar;

    public DialogsFragment() {

    }

    public static DialogsFragment newInstance() {
        final Bundle bundle = new Bundle();
        final DialogsFragment fragment = new DialogsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater pInflater, final ViewGroup pContainer,
                             final Bundle savedInstanceState) {

        final View view = pInflater.inflate(R.layout.fragment_recycler_view, pContainer, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final DividerItemDecoration decoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapterDialogs(this, mRecyclerView, mVkModelDialogList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, mOnItemClickListener));
        mAdapter.setILoadMore(mScrollLoad);

        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, 0);
        bundle.putInt(Constants.URL_BUILDER.COUNT, VALUE_40);
        getLoaderManager().initLoader(LOADER_ID, bundle, this).forceLoad();

        final IntentFilter intentFilter = new IntentFilter(Constants.LONG_POLL_BROADCAST);
        getContext().registerReceiver(broadcastReceiver, intentFilter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public Loader<List<VkModelDialog>> onCreateLoader(final int id, final Bundle args) {
        Loader<List<VkModelDialog>> listLoader = null;
        if (id == LOADER_ID) {
            listLoader = new ParsingDialogsAsyncTask(getContext(), args);
        }
        mProgressBar.setVisibility(View.VISIBLE);
        return listLoader;

    }

    @Override
    public void onLoadFinished(final Loader<List<VkModelDialog>> loader, final List<VkModelDialog> data) {

        if (data != null) {
            if (DIALOGS_SIZE == 0) {
                DIALOGS_SIZE = data.get(0).getDialogsCount();
            }
            mVkModelDialogList.addAll(data);
            mAdapter.notifyDataSetChanged();
            mAdapter.setLoaded();
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(final Loader<List<VkModelDialog>> loader) {

    }

    OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(final View pView, final int pPosition) {
            final Intent intent = new Intent(getContext(), ActivityMessagesHistory.class);
            int chatId = mVkModelDialogList.get(pPosition).getMessages().getChatId();

            if (chatId != 0) {
                chatId = Constants.Values.INT_FOR_CHAT_ID + chatId;
                intent.putExtra(Constants.Parser.CHAT_ID, chatId);
            } else {
                intent.putExtra(Constants.Parser.USER_ID, mVkModelDialogList.get(pPosition).getMessages().getUserId());
            }
            startActivity(intent);
        }

        @Override
        public void onLongItemClick(final View pView, final int pPosition) {
        }
    };

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //broadcast for update Dialogs from LongPoll response

        @Override
        public void onReceive(final Context pContext, final Intent pIntent) {

            final Thread thread = new Thread(new LoadLongPollMessage());
            thread.start();
        }
    };

    ILoadMore mScrollLoad = new ILoadMore() {

        @Override
        public void onLoadMore() {

            if (mVkModelDialogList.size() < DIALOGS_SIZE) {
                mRecyclerView.post(new Runnable() {

                    @Override
                    public void run() {

                        final Bundle bundle = new Bundle();
                        bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, mVkModelDialogList.get(mVkModelDialogList.size() - 1).getMessages().getId());
                        bundle.putInt(Constants.URL_BUILDER.COUNT, VALUE_40);
                        getLoaderManager().restartLoader(LOADER_ID, bundle, DialogsFragment.this).forceLoad();
                    }
                });
            }
        }
    };

    @Override
    public void refresh() {
        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, 0);
        bundle.putInt(Constants.URL_BUILDER.COUNT, VALUE_40);
        getLoaderManager().restartLoader(LOADER_ID, bundle, this).forceLoad();
        Toast.makeText(getContext(),"Refresh Dialogs",Toast.LENGTH_SHORT).show();
    }

    class LoadLongPollMessage implements Runnable {

        VkModelDialog mVkModelDialog;
        boolean mIsChat;

        LoadLongPollMessage() {

        }

        @Override
        public void run() {

            final Bundle bundle = new Bundle();
            bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, 0);
            bundle.putInt(Constants.URL_BUILDER.COUNT, 1);
            final ParsingDialogsAsyncTask parsingDialogsAsyncTask = new ParsingDialogsAsyncTask(getContext(), bundle);
            final List<VkModelDialog> vkModelDialogs = parsingDialogsAsyncTask.loadInBackground();
            mVkModelDialog = vkModelDialogs.get(0);
            mIsChat = vkModelDialogs.get(0).getMessages().getChatId() != 0;

            if (mIsChat) {
                for (int i = 0; i < mVkModelDialogList.size(); i++) {
                    if (mVkModelDialogList.get(i).getMessages().getChatId() == mVkModelDialog.getMessages().getChatId()) {
                        mVkModelDialogList.remove(i);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < mVkModelDialogList.size(); i++) {
                    if (mVkModelDialogList.get(i).getMessages().getUserId() == mVkModelDialog.getMessages().getUserId()) {
                        mVkModelDialogList.remove(i);
                        break;
                    }
                }
            }

            mVkModelDialogList.add(0, vkModelDialogs.get(0));
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}