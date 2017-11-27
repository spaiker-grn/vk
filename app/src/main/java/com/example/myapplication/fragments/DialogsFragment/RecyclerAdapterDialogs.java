package com.example.myapplication.fragments.DialogsFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.clients.ILoadMore;
import com.example.myapplication.vkapi.vkapimodels.VkModel;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;

import java.util.List;

class RecyclerAdapterDialogs extends RecyclerView.Adapter<RecyclerDialogsViewHolder> {

    private final DialogsFragment mDialogsFragment;
    private final List<VkModelDialogs> mDialogsList;
    private ILoadMore loadMore;
    private boolean isLoading;
    //int visibleThreshold = 5;
    private int lastVisibleItem;
    private int totalItemCount;


    RecyclerAdapterDialogs(final DialogsFragment pDialogsFragment, final RecyclerView pRecyclerView, final List<VkModelDialogs> pModelDialogsList) {
        mDialogsFragment = pDialogsFragment;
        mDialogsList = pModelDialogsList;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) pRecyclerView.getLayoutManager();

        pRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (!isLoading) {
                    if (totalItemCount - 1 == lastVisibleItem /*totalItemCount <= (lastVisibleItem + visibleThreshold)*/) {
                        isLoading = true;
                        if (loadMore != null) {
                            loadMore.onLoadMore();

                        }

                    }
                }

            }
        });

    }

    @Override
    public RecyclerDialogsViewHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final View view = LayoutInflater.from(pParent.getContext()).inflate(R.layout.adapter_dialogs, pParent, false);
        return new RecyclerDialogsViewHolder(mDialogsFragment, view);
    }

    @Override
    public void onBindViewHolder(final RecyclerDialogsViewHolder pHolder, final int pPosition) {
        final VkModelDialogs vkModelDialogs = mDialogsList.get(pPosition);

//        getUnread();
//        if (!vkModelDialogs.isUnread) {
//            pHolder.bindReaded();
//        } else {
//            pHolder.bindUnReaded();
//        }

        pHolder.bind(mDialogsList.get(pPosition));
    }

    @Override
    public int getItemCount() {

        return mDialogsList.size();
    }

    void setLoaded() {
        isLoading = false;
    }

    void setLoadMore(final ILoadMore pLoadMore) {
        this.loadMore = pLoadMore;
    }
}
