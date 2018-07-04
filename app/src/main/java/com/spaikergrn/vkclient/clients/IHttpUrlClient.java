package com.spaikergrn.vkclient.clients;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public interface IHttpUrlClient {

    String getRequestWithErrorCheck(String pRequest) throws IOException, JSONException;

    InputStream getInputStream(String pRequest) throws IOException;

    String getLongPollRequest(final String pRequest) throws IOException;
}
