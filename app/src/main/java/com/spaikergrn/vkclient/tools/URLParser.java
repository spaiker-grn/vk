package com.spaikergrn.vkclient.tools;

import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import java.net.MalformedURLException;
import java.net.URL;

public class URLParser {

    public String parse(final String pURL) {

        String[] parts;

        final URL url;
        try {
            url = new URL(pURL);
            parts = url.getRef().split("&");
            parts = parts[0].split("=");

            return parts[1];

        } catch (final MalformedURLException pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.getCause());
        }
        return Constants.PARSE_ERROR_TAG;
    }

}
