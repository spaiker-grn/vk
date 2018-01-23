package com.spaikergrn.vkclient.fragments.newsfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.fragments.recyclersutils.ILoadMore;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelNewsFeedsK;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelNewsPostK;

import java.util.List;

public class RecyclerAdapterNewsFeed extends RecyclerView.Adapter<RecyclerNewsFeedViewHolder> {

    private final List<VkModelNewsPostK> mVkModelNewsPosts;
    private final VkModelNewsFeedsK mVkModelNewsFeeds;
    private final NewsFragment mNewsFragment;
    private ILoadMore mILoadMore;
    private boolean mIsLoading;
    private final LinearLayoutManager mLinearLayoutManager;

    RecyclerAdapterNewsFeed(final NewsFragment pNewsFragment, final RecyclerView pRecyclerView, final VkModelNewsFeedsK pVkModelNewsFeeds) {
        mNewsFragment = pNewsFragment;
        mVkModelNewsFeeds = pVkModelNewsFeeds;
        mVkModelNewsPosts = pVkModelNewsFeeds.getVkModelNewsPosts();
        mLinearLayoutManager = (LinearLayoutManager) pRecyclerView.getLayoutManager();
        pRecyclerView.addOnScrollListener(mScrollListener);
    }

    @Override
    public RecyclerNewsFeedViewHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final View view = LayoutInflater.from(pParent.getContext()).inflate(R.layout.news_item_card, pParent, false);
        return new RecyclerNewsFeedViewHolder(mNewsFragment, mVkModelNewsFeeds, view);
    }

    @Override
    public void onBindViewHolder(final RecyclerNewsFeedViewHolder pHolder, final int pPosition) {

        pHolder.bind(mVkModelNewsPosts.get(pPosition));

    }

    private final RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final int totalItemCount = mLinearLayoutManager.getItemCount();
            final int lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

            final int visibleThreshold = 5;
            if (!mIsLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                if (mILoadMore != null) {
                    mIsLoading = true;
                    mILoadMore.onLoadMore();
                }
            }
        }

    };

    @Override
    public int getItemCount() {
        return mVkModelNewsPosts.size();
    }

    void setLoaded() {
        mIsLoading = false;
    }

    void setILoadMore(final ILoadMore pILoadMore) {
        this.mILoadMore = pILoadMore;
    }

}
