package com.popkovanton.utils.music;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Implementation to Activities onCreate with getLifecycle().addObserver(MusicDefault.getInstance(...));
 */
public class MusicDefault extends MusicCore implements
        LifecycleObserver {

    @SuppressLint("StaticFieldLeak")
    private static MusicDefault musicDefault;
    private static int musicRes;

    public MusicDefault(Context context) {
        this.context = context;
    }

    public static MusicDefault getInstance(Context context) {
        if (musicDefault == null) {
            musicDefault = new MusicDefault(context);
        }
        return musicDefault;
    }

    public static MusicDefault getInstance(Context context, int sMusicResDefault) {
        if (musicDefault == null) {
            musicDefault = new MusicDefault(context);
        }
        musicRes = sMusicResDefault;
        return musicDefault;
    }

    public void soundSwitch(){
        setSoundEnable();
        initSoundOnOff();
    }

    @Override
    protected float getMusicVolume() {
        return 0.3f;
    }

    @Override
    protected float getMusicVolumeRatio() {
        return 2.5f;
    }

    @Override
    public int getMusicRes() {
        return musicRes;
    }

    @Override
    public void setMusicRes(int resId) {
        musicRes = resId;
    }

    @Override
    public void setSoundEnable() {
        super.setSoundEnable();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void initSoundOnOff() {
        super.initSoundOnOff();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void pause() {
        super.pause();
    }
}
