package com.example.myapplication.clients;

import java.io.IOException;
import java.io.InputStream;

public interface IHttpUrlClient {

    String getRequest(String pRequest) throws IOException;
    InputStream getInputStream (String pRequest) throws IOException;
    String getLongPollRequest(final String pRequest)throws IOException;
}
