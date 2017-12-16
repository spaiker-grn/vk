package com.example.myapplication.fragments.dialogsfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragments.recyclersutils.ILoadMore;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;

import java.util.List;

class RecyclerAdapterDialogs extends RecyclerView.Adapter<RecyclerDialogsViewHolder> {

    private final DialogsFragment mDialogsFragment;
    private final List<VkModelDialogs> mDialogsList;
    private ILoadMore mILoadMore;
    private boolean mIsLoading;
    private final LinearLayoutManager mLinearLayoutManager;

    RecyclerAdapterDialogs(final DialogsFragment pDialogsFragment, final RecyclerView pRecyclerView, final List<VkModelDialogs> pModelDialogsList) {
        mDialogsFragment = pDialogsFragment;
        mDialogsList = pModelDialogsList;
        mLinearLayoutManager = (LinearLayoutManager) pRecyclerView.getLayoutManager();

        pRecyclerView.addOnScrollListener(mOnScrollListener);

    }

    @Override
    public RecyclerDialogsViewHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final View view = LayoutInflater.from(pParent.getContext()).inflate(R.layout.item_dialogs, pParent, false);
        return new RecyclerDialogsViewHolder(mDialogsFragment, view);
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
