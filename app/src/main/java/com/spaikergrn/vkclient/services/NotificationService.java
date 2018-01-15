package com.spaikergrn.vkclient.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import java.io.IOException;

public class NotificationService extends Service {

    MediaPlayer mMediaPlayer;

    @Override
    public int onStartCommand(final Intent pIntent, final int pFlags, final int pStartId) {

        final boolean isEnableNotification = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getBoolean(Constants.PreferencesKeys.NOTIFICATIONS_NEW_MESSAGE, true);

        if (isEnableNotification) {

            releaseMP();

            final Uri uri = Uri.parse(PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext())
                    .getString(Constants.PreferencesKeys.NOTIFICATIONS_NEW_MESSAGE_RINGTONE, Constants.Parser.EMPTY_STRING));
            Log.d(Constants.MY_TAG, "ringtone path " + uri);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mMediaPlayer.setDataSource(getApplicationContext(), uri);
                mMediaPlayer.prepare();
                mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                mMediaPlayer.start();
            } catch (final IOException pE) {
                Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
            }

            final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            final boolean isVibrate = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext())
                    .getBoolean(Constants.PreferencesKeys.NOTIFICATIONS_NEW_MESSAGE_VIBRATE, true);

            if (vibrator != null && isVibrate) {
                vibrator.vibrate(Constants.VIBRATION_DURATIONS);
            }
        }
        return super.onStartCommand(pIntent, pFlags, pStartId);
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent pIntent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    private void releaseMP() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(final MediaPlayer pMediaPlayer) {
            stopSelf();
        }
    };
}
