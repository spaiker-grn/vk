package com.example.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.clients.ILoadMore;
import com.example.myapplication.clients.ParsingDialogsAsyncTask;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.VkApiMethods;
import com.example.myapplication.vkapi.vkapimodels.VkModelDialogs;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class DialogsFragment extends Fragment {

    private static final int END_INDEX = 70;
    private int MESSAGE_OFFSET;
    public RecyclerView mRecyclerView;
    public RecyclerAdapterDialogs mAdapter;
    public List<VkModelDialogs> mVkModelDialogsList = new ArrayList<>();
    public  String countMessages;


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
            response = VkApiMethods.getDialogs(0);
            MESSAGE_OFFSET = getCount(response);
            mParsingDialogsAsyncTask.execute(response);
            mVkModelDialogsList = mParsingDialogsAsyncTask.get();

        } catch (final InterruptedException | JSONException | ExecutionException pE) {
            Log.e(Constants.ERROR, pE.getMessage());
            pE.printStackTrace();
        }


        mAdapter = new RecyclerAdapterDialogs(mRecyclerView, mVkModelDialogsList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setLoadMore(new ILoadMore() {

            @Override
            public void onLoadMore() {


                mRecyclerView.post(new Runnable() {

                    @Override
                    public void run() {
                        final ParsingDialogsAsyncTask ParsingDialogsAsyncTask = new ParsingDialogsAsyncTask();



                        try {
                            ParsingDialogsAsyncTask.execute(VkApiMethods.getDialogs(mVkModelDialogsList.get(mVkModelDialogsList.size()-2).message.id));
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
        });
        return view;
    }

    int getCount(final String pResponse) throws JSONException {
        final JSONObject jsonObject = new JSONObject(pResponse);
        return jsonObject.getJSONObject("response").optInt("count");
    }


    private class RecyclerAdapterDialogs extends RecyclerView.Adapter<RecyclerViewHolderDialogs> {

        List<VkModelDialogs> mDialogsList;
        ILoadMore loadMore;
        boolean isLoading;
        //int visibleThreshold = 5;
        int lastVisibleItem;
        int totalItemCount;

        RecyclerAdapterDialogs(final RecyclerView pRecyclerView, final List<VkModelDialogs> pMDialogsList) {
            mDialogsList = pMDialogsList;
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) pRecyclerView.getLayoutManager();
            pRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                    if (!isLoading) {
                        if ( totalItemCount-1 == lastVisibleItem/*totalItemCount <= (lastVisibleItem + visibleThreshold)*/) {
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
        public RecyclerViewHolderDialogs onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
            final View view = LayoutInflater.from(pParent.getContext()).inflate(R.layout.dialogs_item, pParent, false);
            return new RecyclerViewHolderDialogs(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewHolderDialogs pHolder, final int pPosition) {
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

    private class RecyclerViewHolderDialogs extends RecyclerView.ViewHolder {

        TextView mUserId;
        TextView mLastMessage;
        TextView mTime;
        TextView mUnread;
        RelativeLayout mLayout;
        ImageView mOut;

        RecyclerViewHolderDialogs(final View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.dialogs_layout);
            mUserId = itemView.findViewById(R.id.user_dialogs_text_view);
            mLastMessage = itemView.findViewById(R.id.last_message_dialogs_text_view);
            mTime = itemView.findViewById(R.id.time_dialogs_text_view);
            mUnread = itemView.findViewById(R.id.circle_text_view);
            mOut = itemView.findViewById(R.id.out_circle_image_view);

        }

        void bind(final VkModelDialogs pVkModelDialogs) {

            if (pVkModelDialogs.unread != 0) {
                mUnread.setText(String.valueOf(pVkModelDialogs.unread));
                mUnread.setVisibility(View.VISIBLE);
            } else {
                mUnread.setVisibility(View.INVISIBLE);
            }

            if ("".equals(pVkModelDialogs.message.title)) {
                if(pVkModelDialogs.user !=null){mUserId.setText(pVkModelDialogs.user.getFullName());}

            } else {
                mUserId.setText(pVkModelDialogs.message.title);
            }

            mTime.setText(getTime(pVkModelDialogs.message.date));

            if ("".equals(pVkModelDialogs.message.body)) {
                if (pVkModelDialogs.message.attachments != null) {
                    mLastMessage.setText(Constants.WALL_POST);
                } else {
                    mLastMessage.setText(Constants.FWD_MESSAGE);
                }
            } else {
                mLastMessage.setText(subString(pVkModelDialogs.message.body, END_INDEX));
            }

            if (!pVkModelDialogs.message.out && !pVkModelDialogs.message.read_state) {
                mLayout.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
            } else {
                mLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }

            if (!pVkModelDialogs.message.read_state) {
                if (pVkModelDialogs.message.out) {
                    mLastMessage.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
                    mLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                } else {
                    mLayout.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
                    mLastMessage.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
                }



            } else {
                mLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                mLastMessage.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }

            if (pVkModelDialogs.message.out) {
                mOut.setVisibility(View.VISIBLE);
            } else {
                mOut.setVisibility(View.GONE);
            }

        }

        String getTime(final long date) {
            final Locale locale = new Locale("ru");
            final DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
            final String[] shortMonths = {
                    "янв.", "фев.", "март", "апр.", "май", "июн.",
                    "июл.", "авг.", "сен.", "окт.", "нояб.", "дек."};
            dfs.setMonths(shortMonths);
            final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM", locale);
            sdf.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
            sdf.setDateFormatSymbols(dfs);
            return sdf.format(date * 1000);

        }

        String subString(final String pS, final int pLength) {
            if (pS != null && pS.length() > pLength) {
                return pS.substring(0, pLength) + "...";
            } else {
                return pS;
            }

        }

    }
}

