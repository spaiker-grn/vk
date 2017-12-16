package com.example.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.VkApiMethods;
import com.example.myapplication.vkapi.vkapimodels.VkModelLongPollServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LongPollService extends Service {

    ExecutorService mExecutorService;

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public int onStartCommand(final Intent pIntent, final int pFlags, final int pStartId) {

        mExecutorService.execute(new LongPollRun());
        return super.onStartCommand(pIntent, pFlags, pStartId);

    }

    @Nullable
    @Override
    public IBinder onBind(final Intent pIntent) {
        return null;
    }

    class LongPollRun implements Runnable {

        private JSONObject mJsonObjectPollRequest;
        private String mTs;
        private JSONArray mUpdatesArray;

        @Override
        public void run() {
            String s;

            try {


                    final JSONObject jsonObjectServer = new JSONObject(VkApiMethods.getLongPollServer());
                    final VkModelLongPollServer longPollServer = new VkModelLongPollServer(jsonObjectServer);
                    mTs = longPollServer.getTs();
                    do {
                        s = VkApiMethods.getLongPollRequest(longPollServer.getServer(), longPollServer.getKey(), mTs);
                        mJsonObjectPollRequest = new JSONObject(s);
                        mUpdatesArray = mJsonObjectPollRequest.getJSONArray(Constants.Parser.UPDATES);

                        for (int i = 0; i < mUpdatesArray.length(); i++) {
                            Log.d(Constants.MY_TAG, "ARRAY: " + mUpdatesArray.getJSONArray(i).get(0));
                            if (Constants.Values.STRING_VALUES_FOUR.equals(mUpdatesArray.getJSONArray(i).getString(0))) {
                                final Intent intent = new Intent(Constants.LONG_POLL_BROADCAST);
                                intent.putExtra(Constants.MTS_KEY, mTs);
                                intent.putExtra(Constants.Parser.USER_ID, mUpdatesArray.getJSONArray(i).getInt(3));
                                sendBroadcast(intent);
                            }
                        }
                        mTs = mJsonObjectPollRequest.getString(Constants.Parser.TS);

                        Log.d(Constants.MY_TAG, "Long POLL RESPONSE" + s);
                    } while (!mJsonObjectPollRequest.has(Constants.Parser.FAILED));

                } catch (ExecutionException | IOException | InterruptedException | JSONException pE) {
                    Log.e(Constants.ERROR, "Long Poll Service ", pE);
                }
        }
    }
}


