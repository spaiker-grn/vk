package com.spaikergrn.vkclient.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtil {

    public static boolean getConnectivityStatus(final Context pContext) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) pContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;

        final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }
}
