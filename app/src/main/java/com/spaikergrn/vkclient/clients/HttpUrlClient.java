package com.spaikergrn.vkclient.clients;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUrlClient implements IHttpUrlClient {

    private static final int BUFFER_SIZE = 1024;

    public String getRequest(final String pRequest) throws IOException {

        final InputStream inputStream = getInputStream(pRequest);
        final ByteArrayOutputStream result = new ByteArrayOutputStream();
        final byte[] buffer = new byte[BUFFER_SIZE];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(Constants.UTF_8);
    }

    public String getLongPollRequest(final String pRequest) throws IOException {

        final URL url = new URL(pRequest);
        final HttpsURLConnection URLConnection = (HttpsURLConnection) url.openConnection();
        URLConnection.setReadTimeout(Constants.HTTP_CLIENT_TIMEOUT);
        URLConnection.setConnectTimeout(Constants.HTTP_CLIENT_TIMEOUT);
        URLConnection.setRequestMethod(Constants.URL_BUILDER.POST);
        URLConnection.setDoInput(true);

        if (URLConnection.getResponseCode()== HttpURLConnection.HTTP_OK)
        {
            final InputStream inputStream = URLConnection.getInputStream();
            final ByteArrayOutputStream result = new ByteArrayOutputStream();
            final byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(Constants.UTF_8);
        }
        return null;
    }

    @Override
    public InputStream getInputStream(final String pRequest) throws IOException {
        final URL url = new URL(pRequest);
        final HttpsURLConnection URLConnection = (HttpsURLConnection) url.openConnection();
        return URLConnection.getInputStream();
    }
}