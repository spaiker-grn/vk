package com.example.myapplication.imageloader;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public final class Request {

    String url;
    WeakReference<ImageView> target;
    int width;
    int height;

    private Request(final Builder builder) {
        url = builder.mUrl;
        target = builder.mTarget;
    }

    public static final class Builder {
        private final ImageLoader mImageLoader;
        private String mUrl;
        private WeakReference<ImageView> mTarget;

        Builder(final ImageLoader pImageLoader) {
            this.mImageLoader = pImageLoader;
        }

        Builder load(final String val) {
            mUrl = val;
            return this;
        }

        public void into(final ImageView val) {
            mTarget = new WeakReference<>(val);
            mImageLoader.enqueue(this.build());
        }

        Request build() {
            return new Request(this);
        }
    }

}
