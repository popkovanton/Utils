package com.popkovanton.utilssample.util;

import android.annotation.SuppressLint;
import android.content.Context;

import com.popkovanton.utils.sound.ISoundInitialization;
import com.popkovanton.utils.sound.ISoundType;
import com.popkovanton.utils.sound.SoundCore;
import com.popkovanton.utils.sound.SoundInitializationDefault;


public class Sound extends SoundCore {

    @SuppressLint("StaticFieldLeak")
    private static Sound sound;
    private ISoundInitialization soundInitialization;

    private Sound(Context context) {
        super(context);
    }

    public static Sound getInstance(Context context) {
        if (sound == null) {
            sound = new Sound(context);
        }
        return sound;
    }

    @Override
    protected ISoundInitialization getSoundInstruction() {
        if(soundInitialization == null){
            soundInitialization = new SoundInitializationDefault();
        }
        return soundInitialization;
    }

    @Override
    public void playSound(ISoundType soundS) {
        playSoundWithKey(soundS);
        createMap();
    }


    @Override
    protected boolean isSoundEnabled() {
        return true;
    }

    @Override
    protected int getMaxStreams() {
        return 1;
    }

    @Override
    protected float getVolume() {
        return 0.5f;
    }

    @Override
    protected int getRandomSoundDelay() {
        return 0;
    }

    @Override
    protected int getRandomSoundPeriod() {
        return 0;
    }

}
