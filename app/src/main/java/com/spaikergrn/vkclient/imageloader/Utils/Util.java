package com.spaikergrn.vk_client.imageloader.Utils;

import android.util.Log;

import com.spaikergrn.vk_client.serviceclasses.Constants;

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
