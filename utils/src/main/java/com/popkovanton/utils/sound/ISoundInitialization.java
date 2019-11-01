package com.popkovanton.utils.sound;


import java.util.ArrayList;
import java.util.Map;

public interface ISoundInitialization {
    Map<ISoundType, Integer> getSoundMap();
    ArrayList<Integer> getRandomSoundArray();

    /**
     * If you want use this list, put this lifecycle command on OnCreate() in activity class
     * getLifecycle().addObserver(SoundCore);
     */
    ArrayList<Integer> getRandomSoundArrayWithDelay();
}
