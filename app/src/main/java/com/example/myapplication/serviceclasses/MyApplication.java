package com.example.myapplication.serviceclasses;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

//create ContextHolder
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onCreate() {
        super.onCreate();
        MyApplication.sContext = getApplicationContext();

    }

    public static Context getAppContext() {
        return MyApplication.sContext;
    }
}

