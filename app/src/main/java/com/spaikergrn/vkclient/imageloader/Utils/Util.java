package com.spaikergrn.vkclient.imageloader.Utils;

import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import java.io.Closeable;

public final class Util {

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
