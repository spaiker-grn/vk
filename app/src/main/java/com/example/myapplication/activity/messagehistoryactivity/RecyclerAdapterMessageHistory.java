package com.example.myapplication.activity.messagehistoryactivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragments.recyclersutils.ILoadMore;
import com.example.myapplication.vkapi.vkapimodels.VkModelMessages;
import com.example.myapplication.vkapi.vkapimodels.VkModelUser;

import java.util.List;

public class RecyclerAdapterMessageHistory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int OUT = 0;
    private final int IN = 1;
    private final List<VkModelMessages> mMessagesList;
    private final VkModelUser mVkModelUser;
    private ILoadMore mILoadMore;
    private boolean mIsLoading;
    private final LinearLayoutManager mLinearLayoutManager;

    RecyclerAdapterMessageHistory(final RecyclerView pRecyclerView, final List<VkModelMessages> pMessagesList, final VkModelUser pVkModelUser) {
        mMessagesList = pMessagesList;
        mVkModelUser = pVkModelUser;

        mLinearLayoutManager = (LinearLayoutManager) pRecyclerView.getLayoutManager();
        mLinearLayoutManager.setReverseLayout(true);
        pRecyclerView.addOnScrollListener(mOnScrollListener);

    }

    @Override
    public int getItemViewType(final int position) {

        if (mMessagesList.get(position).isOut()) {
            return OUT;
        } else {
            return IN;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {

        if (pViewType == IN) {
            final View view = LayoutInflater.from(pParent.getContext()).inflate(R.layout.item_history_messages_in, pParent, false);
            return new RecyclerViewHolderHistoryIn(pParent.getContext(), view);
        } else {
            final View view = LayoutInflater.from(pParent.getContext()).inflate(R.layout.item_history_messages_out, pParent, false);
            return new RecyclerViewHolderHistoryOut(pParent.getContext(), view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder pHolder, final int pPosition) {

        switch (pHolder.getItemViewType()) {
            case IN:
                final RecyclerViewHolderHistoryIn viewHolderHistoryIn = (RecyclerViewHolderHistoryIn) pHolder;
                viewHolderHistoryIn.bind(mMessagesList.get(pPosition), mVkModelUser);
                break;
            case OUT:
                final RecyclerViewHolderHistoryOut viewHolderHistoryOut = (RecyclerViewHolderHistoryOut) pHolder;
                viewHolderHistoryOut.bind(mMessagesList.get(pPosition));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }


    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

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

    void setLoaded() {
        mIsLoading = false;
    }

    void setILoadMore(final ILoadMore pLoadMore) {
        this.mILoadMore = pLoadMore;
    }

}
