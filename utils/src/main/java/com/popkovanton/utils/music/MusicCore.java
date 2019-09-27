package com.popkovanton.utils.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public abstract class MusicCore {
    private static final String TAG = "MusicCore";

    private boolean soundEnable = true;
    private boolean continueMusic = true;
    private MediaPlayer mp;
    protected Context context;

    protected abstract float getMusicVolume();

    protected abstract float getMusicVolumeRatio();

    protected abstract int getMusicRes();
    protected abstract void setMusicRes(int resId);

    public final boolean isSoundEnable() {
        return soundEnable;
    }

    public final boolean isContinueMusic() {
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
            if(getMusicRes() != 0) {
                mp = MediaPlayer.create(context, getMusicRes());
            }
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
            if (mp != null) {
                if (mp.isPlaying()) {
                    mp.pause();
                }
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
                mp = null;
                setMusicRes(0);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void reset() {
        try {
            if (mp != null) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
                mp.reset();
                mp = null;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void reduceVolume() {
        if (isSoundEnable() && mp != null) {
            mp.setVolume(getMusicVolume() / getMusicVolumeRatio(), getMusicVolume() / getMusicVolumeRatio());
        }
    }


    public void increaseVolume() {
        if (isSoundEnable() && mp != null) {
            mp.setVolume(getMusicVolume() * getMusicVolumeRatio(), getMusicVolume() * getMusicVolumeRatio());
        }
    }

    public void setDefaultVolume() {
        if (isSoundEnable() && mp != null) {
            mp.setVolume(getMusicVolume(), getMusicVolume());
        }
    }
}
