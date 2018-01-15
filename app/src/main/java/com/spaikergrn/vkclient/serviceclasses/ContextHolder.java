package com.spaikergrn.vkclient.serviceclasses;

import android.annotation.SuppressLint;
import android.content.Context;

public final class ContextHolder {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(final Context pContext) {
        sContext = pContext;
    }
}
