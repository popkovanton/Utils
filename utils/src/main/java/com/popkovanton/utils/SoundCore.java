package com.popkovanton.utils;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import androidx.lifecycle.LifecycleObserver;

import java.util.Map;

public abstract class SoundCore implements LifecycleObserver {

    @SuppressLint("StaticFieldLeak")
    private SoundPool mSoundPool;
    private Context context;
    private Map<ISoundType, Integer> map;

    protected SoundCore(Context context) {
        this.context = context;
        initSound();
    }

    protected abstract ISoundInitialization getSoundInstruction();

    protected abstract void playSound(ISoundType soundS);

    protected abstract boolean isSoundEnabled();

    protected abstract float getVolume();

    protected int getMaxStreams(){
        return 1;
    }

    private void initSound() {
        enableSoundPool();
        createMap();
    }

    private void enableSoundPool() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // < Android 5
            createOldSoundPool();
        } else {
            // > Android 5
            createNewSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(getMaxStreams())
                .build();
    }

    private void createOldSoundPool() {
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }

    protected void createMap() {
        if (map == null && getSoundInstruction() != null) {
            map = getSoundInstruction().getSoundMap();
            for (Map.Entry<ISoundType, Integer> entry : map.entrySet()) {
                entry.setValue(loadSound(entry.getValue(), getContext()));
            }
        }
    }

    private Context getContext() {
        return context;
    }

    protected Map<ISoundType, Integer> getMap() {
        return map;
    }

    protected void playSoundWithKey(ISoundType soundT) {
        if (map != null && isSoundEnabled()) {
            if (map.containsKey(soundT)) {
                if (map.get(soundT) > 0) {
                    mSoundPool.play(map.get(soundT), getVolume(), getVolume(), 0, 0, 1);
                }
            }
        }
    }

    protected void playSoundWithKey(ISoundType soundT, int priority) {
        if (map != null && isSoundEnabled()) {
            if (map.containsKey(soundT)) {
                if (map.get(soundT) > 0) {
                    mSoundPool.play(map.get(soundT), getVolume(), getVolume(), priority, 0, 1);
                }
            }
        }
    }

    private int loadSound(Integer fileName, Context context) {
        return mSoundPool.load(context, fileName, 1);
    }
}
