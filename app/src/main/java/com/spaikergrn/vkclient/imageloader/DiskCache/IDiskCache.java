package com.spaikergrn.vkclient.imageloader.DiskCache;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface IDiskCache {


    File getDirectory();

    File get(String imageUri);

    boolean save(String imageUri, InputStream imageStream) throws IOException;

    boolean save(String imageUri, Bitmap bitmap) throws IOException;

    boolean remove(String imageUri);

    void clear();

}
