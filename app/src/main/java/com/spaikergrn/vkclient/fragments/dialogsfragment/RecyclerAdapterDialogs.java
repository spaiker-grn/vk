package com.spaikergrn.vkclient.fragments.dialogsfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.fragments.recyclersutils.ILoadMore;
import com.spaikergrn.vkclient.vkapi.vkapimodels.VkModelDialog;

import java.util.List;

class RecyclerAdapterDialogs extends RecyclerView.Adapter<RecyclerDialogsViewHolder> {

    private final List<VkModelDialog> mDialogsList;
    private ILoadMore mILoadMore;
    private boolean mIsLoading;
    private final LinearLayoutManager mLinearLayoutManager;

    RecyclerAdapterDialogs(final RecyclerView pRecyclerView, final List<VkModelDialog> pModelDialogsList) {
        mDialogsList = pModelDialogsList;
        mLinearLayoutManager = (LinearLayoutManager) pRecyclerView.getLayoutManager();
        pRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public RecyclerDialogsViewHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final View view = LayoutInflater.from(pParent.getContext()).inflate(R.layout.item_dialogs, pParent, false);
        return new RecyclerDialogsViewHolder(pParent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(final RecyclerDialogsViewHolder pHolder, final int pPosition) {
        pHolder.bind(mDialogsList.get(pPosition));
    }

    @Override
    public int getItemCount() {
        return mDialogsList.size();
    }

    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final int totalItemCount = mLinearLayoutManager.getItemCount();
            final int lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

            final int visibleThreshold = 7;
            if (!mIsLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                if (mILoadMore != null) {
                    mIsLoading = true;
                    mILoadMore.onLoadMore();
                }
            }
        }
    };

    void setLoaded() {
        mIsLoading = false;
    }

    void setILoadMore(final ILoadMore pILoadMore) {
        this.mILoadMore = pILoadMore;
    }
}
