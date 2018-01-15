package com.spaikergrn.vkclient.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import java.io.IOException;

public class MediaPlayerService extends Service {

    MediaPlayer mMediaPlayer;

    @Override
    public int onStartCommand(final Intent pIntent, final int pFlags, final int pStartId) {

        final String uri = pIntent.getStringExtra(Constants.AUDIO_URL);
        releaseMP();

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mMediaPlayer.setDataSource(uri);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);

        } catch (final IOException pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        }

        return super.onStartCommand(pIntent, pFlags, pStartId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent pIntent) {
        return null;
    }

    private void releaseMP() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
    MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {

        @Override
        public void onPrepared(final MediaPlayer pMediaPlayer) {
            pMediaPlayer.start();
        }
    };
    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(final MediaPlayer pMediaPlayer) {
            stopSelf();
        }
    };
}
