package com.example.myapplication.serviceclasses;

import android.content.Context;

public final class ContextHolder {

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(final Context pContext) {
        sContext = pContext;
    }
}
