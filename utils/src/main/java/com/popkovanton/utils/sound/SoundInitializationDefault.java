package com.popkovanton.utils.sound;


import com.popkovanton.utils.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SoundInitializationDefault implements ISoundInitialization {

    @Override
    public Map<ISoundType, Integer> getSoundMap() {
        Map<ISoundType, Integer> map = new HashMap<>();
        map.put(SoundTypeDefault.CLICK, R.raw.click);
        return map;
    }

    @Override
    public ArrayList<Integer> getRandomSoundArray() {
        return null;
    }

    @Override
    public ArrayList<Integer> getRandomSoundArrayWithDelay() {
        return null;
    }
}
