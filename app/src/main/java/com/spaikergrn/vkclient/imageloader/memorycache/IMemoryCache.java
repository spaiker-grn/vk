package com.spaikergrn.vkclient.imageloader.memorycache;

import android.graphics.Bitmap;

import java.util.Collection;

public interface IMemoryCache {

    boolean put(String pKey, Bitmap pValue);

    Bitmap get(String pKey);

    Bitmap remove(String pKey);

    Collection<String> keys();

    void clear();

}
