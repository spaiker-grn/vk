package com.example.myapplication.fragments.DialogsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.clients.ILoadMore;
import com.example.myapplication.clients.ParsingDialogsAsyncTask;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.VkApiMethods;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DialogsFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public RecyclerAdapterDialogs mAdapter;
    public List<VkModelDialogs> mVkModelDialogsList = new ArrayList<>();
    public int DIALOGS_SIZE;
    private static final int DIALOGS_COUNT = 40;

    public DialogsFragment() {

    }

    public static DialogsFragment getInstance() {
        final Bundle bundle = new Bundle();
        final DialogsFragment fragment = new DialogsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater pInflater, final ViewGroup pContainer,
                             final Bundle savedInstanceState) {

        final String response;

        final View view = pInflater.inflate(R.layout.fragment_recycler_view, pContainer, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final DividerItemDecoration decoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(layoutManager);

        final ParsingDialogsAsyncTask mParsingDialogsAsyncTask = new ParsingDialogsAsyncTask();

        try {
            response = VkApiMethods.getDialogs(0, DIALOGS_COUNT);
            DIALOGS_SIZE = setCount(response);
            mParsingDialogsAsyncTask.execute(response);
            mVkModelDialogsList = mParsingDialogsAsyncTask.get();

        } catch (final InterruptedException | JSONException | ExecutionException pE) {
            Log.e(Constants.ERROR, pE.getMessage());
            pE.printStackTrace();
        }

        mAdapter = new RecyclerAdapterDialogs(this, mRecyclerView, mVkModelDialogsList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setLoadMore(new ILoadMore() {

            @Override
            public void onLoadMore() {

                if (mVkModelDialogsList.size() < DIALOGS_SIZE) {
                    mRecyclerView.post(new Runnable() {

                        @Override
                        public void run() {
                            final ParsingDialogsAsyncTask ParsingDialogsAsyncTask = new ParsingDialogsAsyncTask();


                            try {
                                if (mVkModelDialogsList.size() / DIALOGS_COUNT < DIALOGS_SIZE / DIALOGS_COUNT) {
                                    ParsingDialogsAsyncTask.execute(VkApiMethods.getDialogs(mVkModelDialogsList.size(), DIALOGS_COUNT));

                                } else {ParsingDialogsAsyncTask.execute(VkApiMethods.getDialogs(mVkModelDialogsList.size(),DIALOGS_SIZE%DIALOGS_COUNT));}

                                mVkModelDialogsList.addAll(ParsingDialogsAsyncTask.get());
                            } catch (final InterruptedException | ExecutionException pE) {
                                Log.e(Constants.ERROR, pE.getMessage());
                                pE.printStackTrace();
                            }

                            mAdapter.notifyDataSetChanged();
                            mAdapter.setLoaded();

                        }
                    });
                }
            }
        });
        return view;
    }

    int setCount(final String pResponse) throws JSONException {
        final JSONObject jsonObject = new JSONObject(pResponse);
        return jsonObject.getJSONObject("response").optInt("count");
    }

}

