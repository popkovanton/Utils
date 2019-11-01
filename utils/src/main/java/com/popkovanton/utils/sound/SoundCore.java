package com.popkovanton.utils.sound;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class SoundCore implements LifecycleObserver {

    @SuppressLint("StaticFieldLeak")
    private SoundPool mSoundPool;
    private Context context;
    private Map<ISoundType, Integer> map;
    private ArrayList<Integer> randomSound;
    private ArrayList<Integer> randomSoundDelay;
    private Timer timer;
    private SoundRandom soundRandom;

    protected SoundCore(Context context) {
        this.context = context;
        initSound();
    }

    protected abstract ISoundInitialization getSoundInstruction();

    protected abstract void playSound(ISoundType soundS);

    protected abstract boolean isSoundEnabled();

    protected abstract float getVolume();

    protected abstract int getRandomSoundDelay();

    protected abstract int getRandomSoundPeriod();

    protected int getMaxStreams() {
        return 1;
    }

    private void initSound() {
        enableSoundPool();
        createMap();
        createRandomSounds();
        createRandomSoundsDelay();
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
        mSoundPool = new SoundPool(getMaxStreams(), AudioManager.STREAM_MUSIC, 0);
    }

    protected void createMap() {
        if (map == null && getSoundInstruction() != null) {
            map = getSoundInstruction().getSoundMap();
            if (map != null) {
                for (Map.Entry<ISoundType, Integer> entry : map.entrySet()) {
                    entry.setValue(loadSound(entry.getValue(), getContext()));
                }
            }
        }
    }

    protected void createRandomSounds() {
        if (randomSound == null && getSoundInstruction() != null) {
            randomSound = getSoundInstruction().getRandomSoundArray();
            if (randomSound != null) {
                for (int i = 0; i < randomSound.size(); i++) {
                    randomSound.set(i, loadSound(randomSound.get(i), getContext()));
                }
            }
        }
    }

    protected void createRandomSoundsDelay() {
        if (randomSoundDelay == null && getSoundInstruction() != null) {
            randomSoundDelay = getSoundInstruction().getRandomSoundArrayWithDelay();
            if (randomSoundDelay != null) {
                for (int i = 0; i < randomSoundDelay.size(); i++) {
                    randomSoundDelay.set(i, loadSound(randomSoundDelay.get(i), getContext()));
                }
            }
        }
    }

    private Context getContext() {
        return context;
    }

    protected Map<ISoundType, Integer> getMap() {
        return map;
    }

    protected long getSoundDuration(ISoundType soundS){
        int duration;
        int soundRawId = getSoundWithKey(soundS);
        if(soundRawId > 0) {
            MediaPlayer player = MediaPlayer.create(getContext(), getSoundWithKey(soundS));
            duration = player.getDuration();
            player.release();
        } else {
            duration = 0;
        }
        return duration;
    }

    protected int getSoundWithKey(ISoundType soundT) {
        if (map != null) {
            if (map.containsKey(soundT)) {
                return getSoundInstruction().getSoundMap().get(soundT);
            }
        }
        return 0;
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

    private class SoundRandom extends TimerTask {

        @Override
        public void run() {
            final int randomElement = randomSoundDelay.get(new Random().nextInt(randomSoundDelay.size()));
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSoundPool.play(randomElement, getVolume(), getVolume(), 0, 0, 1);
                }
            });
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void timerPause() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            soundRandom = null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void timerResume() {
        if (randomSoundDelay != null && isSoundEnabled()) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            timer = new Timer();
            soundRandom = new SoundRandom();
            timer.scheduleAtFixedRate(soundRandom, getRandomSoundDelay(), getRandomSoundPeriod());
        }
    }
}
