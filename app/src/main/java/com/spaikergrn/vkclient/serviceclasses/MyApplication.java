package com.spaikergrn.vk_client.serviceclasses;

import android.app.Application;

import android.os.Build;
import android.support.annotation.RequiresApi;

public class MyApplication extends Application {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(getApplicationContext());
    }
}

