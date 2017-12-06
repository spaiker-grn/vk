package com.example.myapplication.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.clients.HttpUrlClient;
import com.example.myapplication.clients.IHttpUrlClient;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.VkApiMethods;
import com.example.myapplication.vkapi.vkapimodels.VkModelLongPollServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ViewPagerFragment extends Fragment {

    private TextView mTextViewUrl;
    private TextView mTextViewResponse;

    public static ViewPagerFragment newInstance() {
        final Bundle bundle = new Bundle();
        final ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.view_pager_fragment_layout, container, false);

        final Button button = view.findViewById(R.id.get);
        mTextViewUrl = view.findViewById(R.id.url_text_view);
        mTextViewResponse = view.findViewById(R.id.json_text_view);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();

            }
        });
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class MyAsyncTask extends AsyncTask<String, String, VkModelLongPollServer>{

        @Override
        protected VkModelLongPollServer doInBackground(final String... pStrings) {
            final IHttpUrlClient httpUrlClient = new HttpUrlClient();

            VkModelLongPollServer vkModelLongPollServer = null;
            try {

                final JSONObject jsonObject = new JSONObject(VkApiMethods.getLongPollDate());

                vkModelLongPollServer = new VkModelLongPollServer(jsonObject.getJSONObject(Constants.Parser.RESPONSE));
            } catch (JSONException  pE) {
                pE.printStackTrace();
            } catch (InterruptedException pE) {
                pE.printStackTrace();
            } catch (ExecutionException pE) {
                pE.printStackTrace();
            } catch (IOException pE) {
                pE.printStackTrace();
            }

            return vkModelLongPollServer;
        }

        @Override
        protected void onPostExecute(final VkModelLongPollServer pVkModelLongPollServer) {
            super.onPostExecute(pVkModelLongPollServer);
            mTextViewResponse.setText(pVkModelLongPollServer.getServer());

        }
    }
}
