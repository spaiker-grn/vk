package com.example.myapplication.imageloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.clients.HttpUrlClient;
import com.example.myapplication.imageloader.DiskCache.BaseDiskCache;
import com.example.myapplication.imageloader.Utils.Util;
import com.example.myapplication.imageloader.memorycache.MemoryCache;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.IoUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import static android.content.ContentValues.TAG;

public final class ImageLoader {

    @SuppressLint("StaticFieldLeak")
    private static volatile ImageLoader singleton;

    private static final int MAX_MEMORY_FOR_IMAGES = 64 * 1024 * 1024;
    private final BlockingDeque<Request> mDeque;
    private final MemoryCache mLruCache;
    private final ExecutorService mExecutorService;
    private final Context mContext;
    private final Object mLock = new Object();
    private Drawable mDrawable;
    private final BaseDiskCache mBaseDiskCache;
    private static String mChildDiskCache;

    public static ImageLoader with(final Context pContext) {
        if (singleton == null) {
            synchronized (ImageLoader.class) {
                if (singleton == null) {
                    singleton = new ImageLoader(pContext);
                }
            }
        }
        return singleton;
    }

    public static ImageLoader with(final Context pContext, final String pChildDiskCache) {
        mChildDiskCache = pChildDiskCache;
        return with(pContext);
    }

    private ImageLoader(final Context pContext) {

        mContext = pContext;
        mDeque = new LinkedBlockingDeque<>();
        mExecutorService = Executors.newFixedThreadPool(1);
        mBaseDiskCache = new BaseDiskCache(mContext, mChildDiskCache);
        mLruCache = new MemoryCache(getCacheSize());

    }

    private Drawable getPlaceHolder() {
        if (mDrawable == null) {
            return mContext.getResources().getDrawable(R.drawable.ic_friends);
        }
        return mDrawable;
    }

    public void setPlaceHolder(final int pDrawableResource) {
        mDrawable = mContext.getResources().getDrawable(pDrawableResource);

    }

    public Request.Builder load(final String pUrl) {
        return new Request.Builder(this).load(pUrl);
    }

    private void dispatchLoading() {

        new ImageResultAsyncTask().executeOnExecutor(mExecutorService);
    }

    private void processImageResult(final Result pImageResult) {
        if (pImageResult != null) {
            final Request request = pImageResult.getRequest();
            final ImageView imageView = request.target.get();
            if (imageView != null) {
                final Object tag = imageView.getTag();
                if (tag != null && tag.equals(request.url)) {
                    imageView.setImageBitmap(pImageResult.getBitmap());
                }
            }
        }
    }

    void enqueue(final Request pRequest) {
        final ImageView imageView = pRequest.target.get();

        if (imageView == null) {

            return;
        }

        final Bitmap bitmap = mLruCache.get(pRequest.url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageDrawable(getPlaceHolder());
            if (imageHasSize(pRequest)) {
                imageView.setTag(pRequest.url);
                mDeque.addFirst(pRequest);
                dispatchLoading();
            } else {
                deferImageRequest(pRequest);
            }
        }

    }

    private boolean imageHasSize(final Request pRequest) {
        if (pRequest.width > 0 && pRequest.height > 0) {
            return true;
        }

        final ImageView imageView = pRequest.target.get();
        if (imageView != null && imageView.getWidth() > 0 && imageView.getHeight() > 0) {
            pRequest.width = imageView.getWidth();
            pRequest.height = imageView.getHeight();
            return true;
        }

        return false;
    }

    private void deferImageRequest(final Request pRequest) {
        final ImageView imageView = pRequest.target.get();
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                final ImageView view = pRequest.target.get();
                if (view == null) {
                    return true;
                }

                view.getViewTreeObserver().removeOnPreDrawListener(this);

                if (view.getWidth() > 0 && view.getHeight() > 0) {
                    pRequest.width = view.getWidth();
                    pRequest.height = view.getHeight();
                    enqueue(pRequest);
                }
                return true;
            }
        });
    }

    private int getCacheSize() {
        return Math.min((int) (Runtime.getRuntime().maxMemory() / 4), MAX_MEMORY_FOR_IMAGES);
    }

    private boolean hasDiskCache() {
        return mBaseDiskCache != null;
    }

    private Bitmap getScaledBitmap(final InputStream pInputStream, final int pWidth, final int pHeight) throws IOException {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final byte[] chunk = new byte[1 << 16];
        int bytesRead;

        while ((bytesRead = pInputStream.read(chunk)) > 0) {
            byteArrayOutputStream.write(chunk, 0, bytesRead);
        }
        final byte[] bytes = byteArrayOutputStream.toByteArray();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, pWidth, pHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    private static int calculateInSampleSize(final BitmapFactory.Options pOptions, final int pReqWidth, final int pReqHeight) {
        // Raw height and width of image
        final int height = pOptions.outHeight;
        final int width = pOptions.outWidth;
        int inSampleSize = 1;

        if (height > pReqHeight || width > pReqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) pReqHeight);
            final int widthRatio = Math.round((float) width / (float) pReqWidth);

            // Choose the smallest ratio as inSampleSize value
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        Log.d(TAG, "calculateInSampleSize: " + inSampleSize);
        return inSampleSize;
    }

    @SuppressLint("StaticFieldLeak")
    private class ImageResultAsyncTask extends AsyncTask<Void, Void, Result> {

        @Override
        protected Result doInBackground(final Void... pVoids) {

            Result result = null;
            final Bitmap bitmap;

            try {
                final Request request = mDeque.takeFirst();
                result = new Result(request);

                try {
                    if (hasDiskCache()) {
                        result = getFromDiskCache(request);
                        if (result.getBitmap() != null) {
                            return result;
                        }
                    }
                } catch (final FileNotFoundException pE) {
                    Log.e(Constants.ERROR, Constants.ImgLoader.DO_IN_BACKGROUND + pE);
                }

                final InputStream inputStream = new HttpUrlClient().getInputStream(request.url);
                bitmap = getScaledBitmap(inputStream, request.width, request.height);
                Log.d(TAG, "doInBackground: FROM NET " + request.url);

                Util.closeSilently(inputStream);

                if (bitmap != null) {

                    result.setBitmap(bitmap);

                    synchronized (mLock) {
                        mLruCache.put(request.url, bitmap);
                    }

                    if (hasDiskCache()) {
                        synchronized (mLock) {
                            mBaseDiskCache.save(request.url, bitmap);
                        }
                    }

                } else {
                    throw new IllegalStateException(Constants.ImgLoader.BITMAP_IS_NULL);
                }

                return result;
            } catch (final Exception pE) {
                Log.e(Constants.ERROR, Constants.ImgLoader.DO_IN_BACKGROUND, pE);
                if (result != null) {
                    result.setException(pE);
                }
                return result;
            }
        }

        @Override
        protected void onPostExecute(final Result pResult) {
            processImageResult(pResult);
        }
    }

    private Result getFromDiskCache(final Request pRequest) throws IOException {

        final Result result = new Result(pRequest);
        final Bitmap bitmap;
        final File file = mBaseDiskCache.get(pRequest.url);
        if (!file.exists()) {
            throw new FileNotFoundException("File not exist");
        }
        final InputStream fileStream = IoUtils.getIsFromFile(file);
        bitmap = getScaledBitmap(fileStream, pRequest.width, pRequest.height);

        Util.closeSilently(fileStream);

        if (bitmap != null) {
            result.setBitmap(bitmap);
            Log.d(TAG, "doInBackground: from disk cache " + pRequest.url);
        }
        return result;
    }

}
