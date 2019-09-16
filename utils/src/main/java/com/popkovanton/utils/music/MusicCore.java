package com.popkovanton.utils.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;


public abstract class MusicCore {
    private static final String TAG = "MusicCore";

    private boolean soundEnable = true;
    private boolean continueMusic = true;
    private MediaPlayer mp;
    Context context;

    protected abstract float getMusicVolume();

    protected abstract int getMusicRes();

    protected final boolean isSoundEnable() {
        return soundEnable;
    }

    protected final boolean isContinueMusic() {
        return continueMusic;
    }

    protected void setSoundEnable() {
        this.soundEnable = !soundEnable;
    }

    /**
     * Call when switching between screens
     */
    public void setContinueMusic(boolean continueMusic) {
        this.continueMusic = continueMusic;
    }

    public void initSoundOnOff() {
        if (context != null) {
            if (isSoundEnable()) {
                continueMusic = false;
                start(context);
            } else {
                if (!continueMusic) {
                    pause();
                }
            }
        }
    }

    private void start(Context context) {
        if (mp != null) {
            if (!mp.isPlaying()) {
                mp.start();
            }
        } else {
            mp = MediaPlayer.create(context, getMusicRes());
        }

        try {
            mp.setVolume(getMusicVolume(), getMusicVolume());
            mp.setLooping(true);
            mp.start();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void pause() {
        if (!continueMusic) {
            if (mp.isPlaying()) {
                mp.pause();
            }
        }
    }


    public void release() {
        try {
            if (mp != null) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
                mp.release();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
