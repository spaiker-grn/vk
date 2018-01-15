package com.spaikergrn.vkclient.serviceclasses;

import android.app.Application;

import android.support.v7.app.AppCompatDelegate;

public class MyApplication extends Application {

    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ProfileInfoHolder.initVkModelUser();
    }
}

