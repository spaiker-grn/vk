package com.example.myapplication.clients;

import com.example.myapplication.serviceclasses.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUrlClient implements IHttpUrlClient {

    public String getRequest(final String pRequest) throws IOException {

        final InputStream inputStream = getInputStream(pRequest);
        final ByteArrayOutputStream result = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(Constants.UTF_8);
    }

    @Override
    public InputStream getInputStream(final String pRequest) throws IOException {
        final URL url = new URL(pRequest);
        final HttpsURLConnection URLConnection = (HttpsURLConnection) url.openConnection();
        return URLConnection.getInputStream();
    }
}