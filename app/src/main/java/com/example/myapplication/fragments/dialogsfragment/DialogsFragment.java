package com.example.myapplication.fragments.dialogsfragment;

import android.content.Intent;
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

import com.example.myapplication.R;
import com.example.myapplication.activity.messagehistoryactivity.ActivityMessagesHistory;
import com.example.myapplication.fragments.recyclersutils.ILoadMore;
import com.example.myapplication.fragments.recyclersutils.OnItemClickListener;
import com.example.myapplication.fragments.recyclersutils.RecyclerItemClickListener;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;

import java.util.ArrayList;
import java.util.List;

public class DialogsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<VkModelDialogs>> {

    public static final int LOADER_ID = 1;
    public RecyclerView mRecyclerView;
    public RecyclerAdapterDialogs mAdapter;
    public List<VkModelDialogs> mVkModelDialogsList = new ArrayList<>();
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
        mAdapter = new RecyclerAdapterDialogs(this, mRecyclerView, mVkModelDialogsList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, mOnItemClickListener));
        mAdapter.setILoadMore(mScrollLoad);

        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, 0);
        getLoaderManager().initLoader(LOADER_ID, bundle, this).forceLoad();

        return view;
    }

    @Override
    public Loader<List<VkModelDialogs>> onCreateLoader(final int id, final Bundle args) {
        Loader<List<VkModelDialogs>> listLoader = null;
        if (id == LOADER_ID) {
            listLoader = new ParsingDialogsAsyncTask(getContext(), args);
        }
        mProgressBar.setVisibility(View.VISIBLE);
        return listLoader;

    }

    @Override
    public void onLoadFinished(final Loader<List<VkModelDialogs>> loader, final List<VkModelDialogs> data) {

        if (DIALOGS_SIZE == 0) {
            DIALOGS_SIZE = data.get(0).getDialogsCount();
        }
        mVkModelDialogsList.addAll(data);
        mAdapter.notifyDataSetChanged();
        mAdapter.setLoaded();
        mProgressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(final Loader<List<VkModelDialogs>> loader) {

    }

    OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(final View pView, final int pPosition) {
            final Intent intent = new Intent(getContext(), ActivityMessagesHistory.class);
            int chatId = mVkModelDialogsList.get(pPosition).getMessages().getChatId();

            if (chatId != 0) {
                chatId = Constants.Values.INT_FOR_CHAT_ID + chatId;
                intent.putExtra(Constants.Parser.CHAT_ID, chatId);
            } else {
                intent.putExtra(Constants.URL_BUILDER.USER_HISTORY, mVkModelDialogsList.get(pPosition).getMessages().getVkModelUser());
            }
            startActivity(intent);
        }

        @Override
        public void onLongItemClick(final View pView, final int pPosition) {

        }
    };

    ILoadMore mScrollLoad = new ILoadMore() {

        @Override
        public void onLoadMore() {

            if (mVkModelDialogsList.size() < DIALOGS_SIZE) {
                mRecyclerView.post(new Runnable() {

                    @Override
                    public void run() {

                        final Bundle bundle = new Bundle();
                        bundle.putInt(Constants.URL_BUILDER.START_MESSAGE_ID, mVkModelDialogsList.get(mVkModelDialogsList.size() - 1).getMessages().getId());
                        getLoaderManager().restartLoader(LOADER_ID, bundle, DialogsFragment.this).forceLoad();

                    }
                });
            }

        }
    };
}

