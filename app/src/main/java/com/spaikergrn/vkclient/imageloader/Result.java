package com.spaikergrn.vk_client.imageloader;

import android.graphics.Bitmap;

public class Result {

    private final Request mRequest;
    private Bitmap mBitmap;
    private Exception mException;

    Result(final Request pRequest) {
        this.mRequest = pRequest;
    }

    void setBitmap(final Bitmap pBitmap) {
        this.mBitmap = pBitmap;
    }

    void setException(final Exception pException) {
        this.mException = pException;
    }

    Request getRequest() {
        return mRequest;
    }

    Bitmap getBitmap() {
        return mBitmap;
    }

    public Exception getException() {
        return mException;
    }

}
