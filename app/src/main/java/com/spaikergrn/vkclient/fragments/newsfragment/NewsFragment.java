package com.spaikergrn.vkclient.fragments.newsfragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.fragments.IFragmentAdapterRefresh;
import com.spaikergrn.vkclient.fragments.recyclersutils.ILoadMore;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelNewsFeeds;

public class NewsFragment extends Fragment implements IFragmentAdapterRefresh {

    public RecyclerView mRecyclerView;
    public RecyclerAdapterNewsFeed mAdapter;
    public ProgressBar mProgressBar;
    public VkModelNewsFeeds mVkModelNewsFeeds = new VkModelNewsFeeds();

    public static NewsFragment newInstance() {
        final Bundle bundle = new Bundle();
        final NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater pInflater, final ViewGroup pContainer,
                             final Bundle pSavedInstanceState) {
        final View view = pInflater.inflate(R.layout.fragment_news, pContainer, false);
        mRecyclerView = view.findViewById(R.id.news_recycler_view);
        mProgressBar = view.findViewById(R.id.news_progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerAdapterNewsFeed(this, mRecyclerView, mVkModelNewsFeeds);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setILoadMore(mScrollLoad);

        final Bundle bundle = new Bundle();
        bundle.putString(Constants.Parser.NEXT_FROM, null);
        getLoaderManager().initLoader(Constants.LoadersKeys.NEWS_LOADER_ID, bundle, mLoaderCallbacks).forceLoad();
        return view;
    }

    LoaderManager.LoaderCallbacks<VkModelNewsFeeds> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<VkModelNewsFeeds>() {

        @Override
        public Loader<VkModelNewsFeeds> onCreateLoader(final int pId, final Bundle pArgs) {
            Loader<VkModelNewsFeeds> listLoader = null;
            if (pId == Constants.LoadersKeys.NEWS_LOADER_ID) {
                listLoader = new ParsingNewsAsyncTask(getContext(), pArgs);
            }
            mProgressBar.setVisibility(View.VISIBLE);
            return listLoader;
        }

        @Override
        public void onLoadFinished(final Loader<VkModelNewsFeeds> pLoader, final VkModelNewsFeeds pData) {

            mVkModelNewsFeeds.addNew(pData);
            mAdapter.notifyDataSetChanged();
            mAdapter.setLoaded();
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onLoaderReset(final Loader<VkModelNewsFeeds> pLoader) {

        }
    };

    ILoadMore mScrollLoad = new ILoadMore() {

        @Override
        public void onLoadMore() {

            mRecyclerView.post(new Runnable() {

                @Override
                public void run() {
                    final Bundle bundle = new Bundle();
                    bundle.putString(Constants.Parser.NEXT_FROM, mVkModelNewsFeeds.getNextFrom());
                    getLoaderManager().restartLoader(Constants.LoadersKeys.NEWS_LOADER_ID, bundle, mLoaderCallbacks).forceLoad();
                }
            });
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().stopService(new Intent(getContext(), MediaPlayer.class));
    }

    @Override
    public void refresh() {

        mVkModelNewsFeeds = new VkModelNewsFeeds();
        mAdapter = new RecyclerAdapterNewsFeed(this, mRecyclerView, mVkModelNewsFeeds);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setILoadMore(mScrollLoad);

        final Bundle bundle = new Bundle();
        bundle.putString(Constants.Parser.NEXT_FROM, null);
        getLoaderManager().restartLoader(Constants.LoadersKeys.NEWS_LOADER_ID, bundle, mLoaderCallbacks).forceLoad();

        Toast.makeText(getContext(), Constants.REFRESH_NEWS, Toast.LENGTH_SHORT).show();
    }
}
