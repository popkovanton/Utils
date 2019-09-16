package com.popkovanton.utils.music;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.popkovanton.utils.R;

/**
 * Implementation to Activities onCreate with getLifecycle().addObserver(MusicDefault.getInstance(...));
 */
public class MusicDefault extends MusicCore implements
        LifecycleObserver {

    @SuppressLint("StaticFieldLeak")
    private static MusicDefault musicDefault;
    private int musicResDefault = R.raw.music_default;

    public MusicDefault(Context context) {
        this.context = context;
    }

    public static MusicDefault getInstance(Context context) {
        if (musicDefault == null) {
            musicDefault = new MusicDefault(context);
        }
        return musicDefault;
    }

    @Override
    protected float getMusicVolume() {
        return 0.3f;
    }

    @Override
    protected int getMusicRes() {
        return musicResDefault;
    }

    @Override
    public void setSoundEnable() {
        super.setSoundEnable();
        initSoundOnOff();
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
