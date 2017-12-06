package com.example.myapplication.imageloader.Utils;

import android.util.Log;

import com.example.myapplication.serviceclasses.Constants;

import java.io.Closeable;

public class Util {

    public static void closeSilently(final Closeable pCloseable) {
        if (pCloseable != null) {
            try {
                pCloseable.close();
            } catch (final Exception pE) {
                Log.d(Constants.MY_TAG, pE.getMessage());
            }
        }
    }



}
