package com.popkovanton.utils.sound;


import com.popkovanton.utils.R;

import java.util.HashMap;
import java.util.Map;

public class SoundInitializationDefault implements ISoundInitialization {

    @Override
    public Map<ISoundType, Integer> getSoundMap() {
        Map<ISoundType, Integer> map = new HashMap<>();
        map.put(SoundTypeDefault.CLICK, R.raw.click);
        return map;
    }
}
