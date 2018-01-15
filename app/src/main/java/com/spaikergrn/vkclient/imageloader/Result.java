package com.spaikergrn.vkclient.imageloader;

import android.graphics.Bitmap;

class Result {

    private final Request mRequest;
    private Bitmap mBitmap;

    Result(final Request pRequest) {
        this.mRequest = pRequest;
    }

    void setBitmap(final Bitmap pBitmap) {
        this.mBitmap = pBitmap;
    }

    Request getRequest() {
        return mRequest;
    }

    Bitmap getBitmap() {
        return mBitmap;
    }
}
