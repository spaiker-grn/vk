/*
package com.example.myapplication.fragments.newsfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragments.recyclersutils.ILoadMore;
import com.example.myapplication.vkapi.vkapimodels.VkModelPost;

import java.util.List;

public class RecyclerAdapterNewsFeed extends RecyclerView.Adapter<RecyclerNewsFeedViewHolder> {

    private final List<VkModelPost> mVkModelPosts;
    private final NewsFragment mNewsFragment;
    private ILoadMore mILoadMore;
    private boolean mIsLoading;
    private final int mVisibleThreshold = 10;
    private int mLastVisibleItem;
    private int mTotalItemCount;


    RecyclerAdapterNewsFeed(final NewsFragment pNewsFragment, final RecyclerView pRecyclerView, final List<VkModelPost> pVkModelPosts ){
        mNewsFragment = pNewsFragment;
        mVkModelPosts = pVkModelPosts;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) pRecyclerView.getLayoutManager();

        pRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalItemCount = linearLayoutManager.getItemCount();
                mLastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {

                    if (mILoadMore != null) {
                        mIsLoading = true;
                        mILoadMore.onLoadMore();

                    }

                }

            }
        });

    }

    @Override
    public RecyclerNewsFeedViewHolder onCreateViewHolder(final ViewGroup pParent, final int pViewtype) {
        final View view = LayoutInflater.from(pParent.getContext()).inflate(R.layout.news_item_card, pParent, false);
        return new RecyclerNewsFeedViewHolder(mNewsFragment, view);
    }

    @Override
    public void onBindViewHolder(final RecyclerNewsFeedViewHolder pHolder, final int pPosition) {

    }

    @Override
    public int getItemCount() {
       return mVkModelPosts.size();
    }

    void setLoaded() {
        mIsLoading = false;
    }

    void setILoadMore(final ILoadMore pILoadMore) {
        this.mILoadMore = pILoadMore;
    }
}
*/
