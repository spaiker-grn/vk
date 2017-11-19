package com.example.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.ModelItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Дмитрий on 04.11.2017.
 */

public class NewsFeedFragment extends Fragment {

    public static NewsFeedFragment getInstance() {
        final Bundle bundle = new Bundle();
        final NewsFeedFragment fragment = new NewsFeedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final RecyclerAdapterNews adapter = new RecyclerAdapterNews();
        recyclerView.setAdapter(adapter);
        adapter.addAll(ModelItem.getFakeItems());
        return view;
    }

    private class RecyclerAdapterNews extends RecyclerView.Adapter<RecyclerViewHolderNews> {

        private final List<ModelItem> items = new ArrayList<>();

        void addAll(final Collection<ModelItem> pFakeItems) {
            final int pos = getItemCount();
            this.items.addAll(pFakeItems);
            notifyItemRangeInserted(pos, this.items.size());
        }

        @Override
        public RecyclerViewHolderNews onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
            final View view = LayoutInflater.from(pParent.getContext()).inflate(R.layout.news_item_card, pParent, false);
            return new RecyclerViewHolderNews(view);

        }

        @Override
        public void onBindViewHolder(final RecyclerViewHolderNews pHolder, final int pPosition) {
            pHolder.bind(items.get(pPosition));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private class RecyclerViewHolderNews extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ImageView imageView;

        RecyclerViewHolderNews(final View pItemView) {
            super(pItemView);
            textView = pItemView.findViewById(R.id.card_news_description_text_view);
            imageView = pItemView.findViewById(R.id.card_news_image_view);

        }

        void bind(final ModelItem pModelItem) {

            if (pModelItem.isImg()){imageView.setImageResource(pModelItem.getImgSource());}
            else {imageView.setVisibility(View.GONE);}
            textView.setText(pModelItem.getDescription());


        }

    }

}
