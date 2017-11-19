package com.example.myapplication.tools;

import com.example.myapplication.serviceclasses.Constants;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Дмитрий on 04.11.2017.
 */

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
            pE.getMessage();
        }

        return Constants.PARSE_ERROR;

    }

}
