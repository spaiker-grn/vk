package com.example.myapplication.serviceclasses;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by Дмитрий on 06.11.2017.
 */

//create ContextHolder
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public void onCreate() {
        super.onCreate();
        MyApplication.sContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.sContext;
    }
}

