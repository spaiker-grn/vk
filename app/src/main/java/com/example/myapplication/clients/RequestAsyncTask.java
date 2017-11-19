package com.example.myapplication.clients;

import android.os.AsyncTask;

/**
 * Created by Дмитрий on 05.11.2017.
 */

public class RequestAsyncTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(final String... pStrings) {

        final IHttpUrlClient httpUrlClient = new HttpUrlClient();

        return httpUrlClient.getRequest(pStrings[0]);
    }

}
