package com.spaikergrn.vkclient.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.spaikergrn.vkclient.clients.HttpUrlClient;
import com.spaikergrn.vkclient.clients.IHttpUrlClient;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.tools.NetworkUtil;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelLongPollServerK;

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

        static final int ARRAY_USER_ID = 3;
        static final int IN_OUT_FLAG = 2;
        static final int OUT_FLAG = 35;
        private JSONObject mJsonObjectPollRequest;
        private String mTs;
        private JSONArray mUpdatesArray;
        int inOutFlag;

        @Override
        public void run() {
            String s;

            try {
                final JSONObject jsonObjectServer = new JSONObject(VkApiMethods.getLongPollServer());
                final VkModelLongPollServerK longPollServer = new VkModelLongPollServerK(jsonObjectServer);
                mTs = longPollServer.getTs();
                do {
                    s = getLongPollRequest(longPollServer.getServer(), longPollServer.getKey(), mTs);
                    mJsonObjectPollRequest = new JSONObject(s);
                    mUpdatesArray = mJsonObjectPollRequest.getJSONArray(Constants.Parser.UPDATES);

                    for (int i = 0; i < mUpdatesArray.length(); i++) {
                        final JSONArray requestArray = mUpdatesArray.getJSONArray(i);
                        if (Constants.Values.STRING_VALUES_FOUR.equals(requestArray.getString(0))) {
                            inOutFlag = requestArray.getInt(IN_OUT_FLAG);
                            final Intent intent = new Intent(Constants.LONG_POLL_BROADCAST);
                            intent.putExtra(Constants.MTS_KEY, mTs);
                            intent.putExtra(Constants.Parser.USER_ID, requestArray.getInt(ARRAY_USER_ID));
                            sendBroadcast(intent);
                            if (inOutFlag != OUT_FLAG) {
                                getApplicationContext().startService(new Intent(getApplicationContext(), NotificationService.class));
                            }
                            return;
                        }
                    }
                    mTs = mJsonObjectPollRequest.getString(Constants.Parser.TS);

                    Log.d(Constants.MY_TAG, "Long POLL RESPONSE" + s);
                } while (!mJsonObjectPollRequest.has(Constants.Parser.FAILED));

            } catch (ExecutionException | IOException | InterruptedException | JSONException pE) {
                Log.e(Constants.ERROR, "Long Poll Service: ", pE.getCause());
                try {
                    Thread.sleep(Constants.SLEEP_TIME);
                } catch (final InterruptedException pEs) {
                    Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
                }
                if (NetworkUtil.getConnectivityStatus(getApplicationContext())){
                    startService(new Intent(getApplicationContext(), LongPollService.class));
                }
            }
        }

        String getLongPollRequest(final String pServer, final String pKey, final String pRequest) throws IOException {
            final IHttpUrlClient httpUrlClient = new HttpUrlClient();
            final String URL = String.format(Constants.URL_BUILDER.LONG_POLL_RESPONSE, pServer, pKey, pRequest);
            return httpUrlClient.getLongPollRequest(URL);
        }
    }
}


