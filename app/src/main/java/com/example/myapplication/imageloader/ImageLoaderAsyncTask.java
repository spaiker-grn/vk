package com.example.myapplication.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import com.example.myapplication.serviceclasses.Constants;
import java.io.InputStream;

public class ImageLoaderAsyncTask extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(final String... pStrings) {

        final String imageUrl = pStrings[0];
        Bitmap bitmapImage = null;
        try {
            final InputStream inputStream = new java.net.URL(imageUrl).openStream();
            bitmapImage = BitmapFactory.decodeStream(inputStream);
        } catch (final Exception pE) {
            Log.e(Constants.ERROR, pE.getMessage());
            pE.printStackTrace();
        }
        return bitmapImage;

    }
}
