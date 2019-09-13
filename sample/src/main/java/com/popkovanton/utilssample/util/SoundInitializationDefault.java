package com.popkovanton.utilssample.util;


import com.popkovanton.utils.ISoundInitialization;
import com.popkovanton.utils.ISoundType;
import com.popkovanton.utils.SoundTypeDefault;
import com.popkovanton.utilssample.R;

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
