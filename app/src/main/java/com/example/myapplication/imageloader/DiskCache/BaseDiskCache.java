package com.example.myapplication.imageloader.DiskCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.StatFs;
import android.util.Log;

import com.example.myapplication.imageloader.Utils.FileNameGenerator;
import com.example.myapplication.imageloader.Utils.Util;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.tools.IoUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

public class BaseDiskCache implements IDiskCache {

    private static final long MIN_DISK_CACHE_SIZE = 10 * 1024 * 1024;
    private static final int DEFAULT_BUFFER_SIZE = 4 * 1024;
    private static final int DEFAULT_COMPRESS_QUALITY = 100;
    private static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    private final FileNameGenerator mFileNameGenerator;
    private final int mBufferSize = DEFAULT_BUFFER_SIZE;
    private final Bitmap.CompressFormat mCompressFormat = DEFAULT_COMPRESS_FORMAT;
    private final long MAX_DISK_CACHE_SIZE;
    private final File mCacheDir;
    private long mUsedCacheSize;
    private final File mBaseCacheDir;

    public BaseDiskCache(final Context pContext, final String pCacheChildDir) {
        mFileNameGenerator = new FileNameGenerator();

        mBaseCacheDir = pContext.getCacheDir();

        if (pCacheChildDir == null){
            mCacheDir = pContext.getCacheDir();
        }  else {
            mCacheDir = new File(pContext.getCacheDir(), pCacheChildDir);
        }

        if (!mCacheDir.exists()) {
            final boolean mkdirs = this.mCacheDir.mkdirs();
            if (!mkdirs) {
                throw new IllegalStateException("Can't create dir for images");
            }

        }

        Log.d(Constants.MY_TAG, "BaseDiskCacheDir: " + mCacheDir);
        MAX_DISK_CACHE_SIZE = calculateCacheSize(mCacheDir);
        //MAX_DISK_CACHE_SIZE = 1000000;
        Log.d(Constants.MY_TAG, "BaseDiskCacheSize: " + MAX_DISK_CACHE_SIZE);
        mUsedCacheSize = getUsedSize(mBaseCacheDir);
        Log.d(Constants.MY_TAG, "getUsedCacheSize: " + mUsedCacheSize);
        checkAvailableCache();

    }

    private void checkAvailableCache() {
        if (mUsedCacheSize >= MAX_DISK_CACHE_SIZE) {
            clear();
        }
    }

    @Override
    public File getDirectory() {
        return mCacheDir;
    }

    @Override
    public File get(final String pImageUri) {
        final File file = getFile(pImageUri);
        file.setLastModified(System.currentTimeMillis());
        return file;
    }

    @Override
    public boolean save(final String pImageUri, final InputStream pIs) throws IOException {

        final File imageFile = getFile(pImageUri);
        final File tmpFile = new File(imageFile.getAbsolutePath() + Constants.ImgLoader.TEMP_IMAGE_POSTFIX);
        boolean loaded = false;
        try {
            final OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), mBufferSize);
            try {
                loaded = IoUtils.copyStream(pIs, os);
            } finally {
                Util.closeSilently(os);
            }
        } finally {
            if (loaded && !tmpFile.renameTo(imageFile)) {
                loaded = false;
            }
            if (!loaded) {
                tmpFile.delete();
            }
        }
        return loaded;
    }

    @Override
    public boolean save(final String pImageUri, final Bitmap pBitmap) throws IOException {
        final File imageFile = getFile(pImageUri);
        final File tmpFile = new File(imageFile.getAbsolutePath() + Constants.ImgLoader.TEMP_IMAGE_POSTFIX);
        final OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), mBufferSize);
        boolean savedSuccessfully = false;
        try {
            savedSuccessfully = pBitmap.compress(mCompressFormat, DEFAULT_COMPRESS_QUALITY, os);
            imageFile.setLastModified(System.currentTimeMillis());
        } finally {
            Util.closeSilently(os);
            if (savedSuccessfully && !tmpFile.renameTo(imageFile)) {
                savedSuccessfully = false;
            }
            if (!savedSuccessfully) {
                tmpFile.delete();
            }
        }
        //pBitmap.recycle();

        return savedSuccessfully;
    }

    @Override

    public boolean remove(final String pImageUri) {
        return getFile(pImageUri).delete();
    }

    @Override
    public void clear() {
        final File[] files = mBaseCacheDir.listFiles();

        long cleared = 0;
        int index = 0;
        fileListSort(files);

/*        List<File> fileList = new ArrayList<>();

        fileList = Arrays.asList(files);

        while (!fileList.isEmpty() && mUsedCacheSize >= MAX_DISK_CACHE_SIZE * 0.8) {
            final long fileSize = fileList.get(0).length();
            final boolean isDeleted = fileList.get(0).delete();
            fileList.remove(0);
            if (isDeleted) {
                mUsedCacheSize -= fileSize;
                cleared += fileSize;
            }
        }*/

/*        for (int i=0; i< files.length; i++){

            Log.d(Constants.MY_TAG, "FILES LENGTH: " + files.length);
            Log.d(Constants.MY_TAG, "FILES: " + files[i].lastModified());

        }*/

        while (index < files.length && mUsedCacheSize >= MAX_DISK_CACHE_SIZE * 0.8) {


            try {
                final long fileSize = files[index].length();
                final boolean isDeleted = files[index].delete();
                if (isDeleted) {
                    mUsedCacheSize -= fileSize;
                    cleared += fileSize;
                }
            } catch (final Exception pE) {
                Log.e(Constants.ERROR, "Error clear: " + pE );
            }
            index++;
        }


        Log.d(Constants.MY_TAG, "cleared: " + cleared);
    }

    private void fileListSort(final File[] pFiles) {
        Arrays.sort(pFiles, new Comparator<File>() {

            public int compare(final File f1, final File f2) {
                return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
            }
        });
    }

    private File getFile(final String pImageUri) {
        final String fileName = mFileNameGenerator.generate(pImageUri);
        return new File(mCacheDir, fileName);
    }

    private long calculateCacheSize(final File pFile) {

        final StatFs statFs = new StatFs(pFile.getAbsolutePath());
        final long totalSize = ((long) statFs.getBlockCount()) * statFs.getBlockSize();

        Log.d(Constants.MY_TAG, "calculateCacheSize: " + totalSize / Constants.Values.PERCENT_FROM_DISK_SIZE);
        return totalSize / Constants.Values.PERCENT_FROM_DISK_SIZE;
    }

    private static long getUsedSize(final File pFile) {

        long length = 0;

        for (final File file : pFile.listFiles()) {
            try {
                if (file != null) {
                    if (file.isFile()) {
                        length += file.length();
                    } else {
                        length += getUsedSize(file);
                    }

                }

            } catch (final Exception pE) {
                Log.e(Constants.ERROR, "Get used size: " + pE);
            }
        }

        return length;
    }

}

