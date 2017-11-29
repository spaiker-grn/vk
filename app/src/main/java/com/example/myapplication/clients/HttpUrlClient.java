package com.example.myapplication.clients;

import android.util.Log;

import com.example.myapplication.serviceclasses.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUrlClient implements IHttpUrlClient {

    private BufferedReader mBufferedReader;
    private StringBuffer mStringBuffer;

    @Override
    public String getRequest(final String pRequest) {

        try {
            final URL url = new URL(pRequest);
            final HttpsURLConnection URLConnection = (HttpsURLConnection) url.openConnection();

            mBufferedReader = new BufferedReader(new InputStreamReader(URLConnection.getInputStream()));
            mStringBuffer = new StringBuffer();

            String readLine;
            while ((readLine = mBufferedReader.readLine()) != null) {
                mStringBuffer.append(readLine);
            }
            mBufferedReader.close();

        } catch (final MalformedURLException pE) {
            Log.e(Constants.ERROR, pE.getMessage());
        } catch (final IOException pE) {
            Log.e(Constants.ERROR, pE.getMessage());
        } finally {
            if (mBufferedReader != null) {
                try {
                    mBufferedReader.close();
                } catch (final IOException pE) {
                    Log.i(Constants.ERROR, Constants.ERROR_CLOSING_INPUT_STREAM);
                }
            }
        }
        return mStringBuffer.toString();
    }
}