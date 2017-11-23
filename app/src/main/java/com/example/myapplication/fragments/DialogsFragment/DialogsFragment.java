package com.example.myapplication.fragments.DialogsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.myapplication.R;
import com.example.myapplication.activity.MessagesHistory;
import com.example.myapplication.clients.ILoadMore;
import com.example.myapplication.clients.ParsingDialogsAsyncTask;
import com.example.myapplication.fragments.RecyclerItemClickListener;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.VkApiMethods;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DialogsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<VkModelDialogs>> {

    public static final String REQUEST = "request";
    public static final int LOADER_ID = 1;
    public RecyclerView mRecyclerView;
    public RecyclerAdapterDialogs mAdapter;
    public List<VkModelDialogs> mVkModelDialogsList = new ArrayList<>();
    public int DIALOGS_SIZE;
    private static final int DIALOGS_COUNT = 40;
    public static final String HISTORY_ID = "history";
    public ProgressBar mProgressBar;
    public static final String OFFSET_KEY = "offset";
    public static final String COUNT_KEY = "count";

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
        mProgressBar = view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final DividerItemDecoration decoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(layoutManager);






//       try {
//            response = VkApiMethods.getDialogs(0, DIALOGS_COUNT);
//            DIALOGS_SIZE = getCount(response);

            final Bundle bundle = new Bundle();
//            bundle.putString(REQUEST, response);
            bundle.putInt(OFFSET_KEY, 0);
            bundle.putInt(COUNT_KEY, DIALOGS_COUNT);
            getLoaderManager().initLoader(LOADER_ID, bundle,this).forceLoad();


/*        } catch (final InterruptedException | JSONException | ExecutionException pE) {
            Log.e(Constants.ERROR, pE.getMessage());
            pE.printStackTrace();
        }*/

        mAdapter = new RecyclerAdapterDialogs(this, mRecyclerView, mVkModelDialogsList);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setLoadMore(new ILoadMore() {


            @Override
            public void onLoadMore() {

                if (mVkModelDialogsList.size() < DIALOGS_SIZE) {
                    mRecyclerView.post(new Runnable() {

                        @Override
                        public void run() {


//                            try {
                                if (mVkModelDialogsList.size() / DIALOGS_COUNT < DIALOGS_SIZE / DIALOGS_COUNT) {

                                    final Bundle bundle = new Bundle();
                                    bundle.putInt(OFFSET_KEY,mVkModelDialogsList.size());
                                    bundle.putInt(COUNT_KEY, DIALOGS_COUNT);
//                                    bundle.putString(REQUEST, (VkApiMethods.getDialogs(mVkModelDialogsList.size(), DIALOGS_COUNT)));
                                    getLoaderManager().restartLoader(LOADER_ID, bundle, DialogsFragment.this).forceLoad();

                                } else {
                                    final Bundle bundle = new Bundle();
                                    bundle.putInt(OFFSET_KEY,mVkModelDialogsList.size());
                                    bundle.putInt(COUNT_KEY, DIALOGS_SIZE%DIALOGS_COUNT);
//                                    bundle.putString(REQUEST, (VkApiMethods.getDialogs(mVkModelDialogsList.size(), DIALOGS_COUNT)));
                                    getLoaderManager().restartLoader(LOADER_ID, bundle, DialogsFragment.this).forceLoad();
                                }


                         /*   } catch (final InterruptedException | ExecutionException pE) {
                                Log.e(Constants.ERROR, pE.getMessage());
                                pE.printStackTrace();
                            }*/


                            mAdapter.setLoaded();

                        }
                    });
                }
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(final View pView, final int pPosition) {
                final int id = mVkModelDialogsList.get(pPosition).message.user_id;
                startActivity(new Intent(getContext(), MessagesHistory.class).putExtra(HISTORY_ID, id));

            }

            @Override
            public void onLongItemClick(final View pView, final int pPosition) {

            }
        }));

        return view;
    }

    int getCount(final String pResponse) throws JSONException {
        final JSONObject jsonObject = new JSONObject(pResponse);
        return jsonObject.getJSONObject("response").optInt("count");
    }

    @Override
    public Loader<List<VkModelDialogs>> onCreateLoader(final int id, final Bundle args) {
        Loader<List<VkModelDialogs>> listLoader = null;
        if (id==LOADER_ID){
            listLoader = new ParsingDialogsAsyncTask(getContext(),args);
        }
        mProgressBar.setVisibility(View.VISIBLE);
        return listLoader;

    }

    @Override
    public void onLoadFinished(final Loader<List<VkModelDialogs>> loader, final List<VkModelDialogs> data) {

        DIALOGS_SIZE = data.get(0).dialogs_count;
        mVkModelDialogsList.addAll(data);
        mAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(final Loader<List<VkModelDialogs>> loader) {

    }
}

