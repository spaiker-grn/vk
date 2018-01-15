package com.spaikergrn.vkclient.imageloader.memorycache;

import android.graphics.Bitmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryCache implements IMemoryCache {

    private final Map<String, Bitmap> mMap;

    private final int mMaxSize;
    private int mSize;

    public MemoryCache(final int pMaxsize) {
        if (pMaxsize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.mMaxSize = pMaxsize;
        this.mMap = new LinkedHashMap<>(0, 0.75f, true);
    }

    @Override
    public final Bitmap get(final String pKey) {
        if (pKey == null) {
            throw new NullPointerException("key == null");
        }

        synchronized (this) {
            return mMap.get(pKey);
        }
    }

    @Override
    public final boolean put(final String pKey, final Bitmap pValue) {
        if (pKey == null || pValue == null) {
            throw new NullPointerException("key == null || value == null");
        }

        synchronized (this) {
            mSize += sizeOf(pValue);
            final Bitmap previous = mMap.put(pKey, pValue);
            if (previous != null) {
                mSize -= sizeOf(previous);
            }
        }

        trimToSize(mMaxSize);
        return true;
    }

    private void trimToSize(final int pMaxSize) {
        while (true) {
            final String key;
            final Bitmap value;
            synchronized (this) {
                if (mSize < 0 || (mMap.isEmpty() && mSize != 0)) {
                    throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }

                if (mSize <= pMaxSize || mMap.isEmpty()) {
                    break;
                }

                final Map.Entry<String, Bitmap> toEvict = mMap.entrySet().iterator().next();
                if (toEvict == null) {
                    break;
                }
                key = toEvict.getKey();
                value = toEvict.getValue();
                mMap.remove(key);
                mSize -= sizeOf(value);
            }
        }
    }

    @Override
    public final Bitmap remove(final String pKey) {
        if (pKey == null) {
            throw new NullPointerException("key == null");
        }

        synchronized (this) {
            final Bitmap previous = mMap.remove(pKey);
            if (previous != null) {
                mSize -= sizeOf(previous);
            }
            return previous;
        }
    }

    @Override
    public Collection<String> keys() {
        synchronized (this) {
            return new HashSet<>(mMap.keySet());
        }
    }

    @Override
    public void clear() {
        trimToSize(-1);
    }

    private int sizeOf(final Bitmap pValue) {
        return pValue.getRowBytes() * pValue.getHeight();
    }
}
