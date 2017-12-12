/*
package com.example.myapplication.fragments.newsfragment;

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

import com.example.myapplication.R;
import com.example.myapplication.vkapi.vkapimodels.VkAttachments;
import com.example.myapplication.vkapi.vkapimodels.VkModelPost;

import java.util.List;

public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<VkModelPost>> {

    public static final int LOADER_ID = 1;
    public RecyclerView mRecyclerView;
    public RecyclerAdapterNewsFeed mAdapter;
    public ProgressBar mProgressBar;
    public List<VkModelPost> mVkModelPosts;

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

        mAdapter = new RecyclerAdapterNewsFeed(this, mRecyclerView, mVkModelPosts);

        final Bundle bundle = new Bundle();
        bundle.putString();
        getLoaderManager().initLoader(LOADER_ID,bundle, this).forceLoad();
        return view;
    }

    @Override
    public Loader<List<VkModelPost>> onCreateLoader(final int pId, final Bundle pArgs) {
        return null;
    }

    @Override
    public void onLoadFinished(final Loader<List<VkModelPost>> pLoader, final List<VkModelPost> pData) {

    }

    @Override
    public void onLoaderReset(final Loader<List<VkModelPost>> pLoader) {

    }

    String getNextFrom(){
        mVkModelPosts.get(mVkModelPosts.size()-1).
    }

}
*/
