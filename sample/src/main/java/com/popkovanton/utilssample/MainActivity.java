package com.popkovanton.utilssample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.popkovanton.utils.SoundTypeDefault;
import com.popkovanton.utilssample.util.Sound;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("sound_test", "activity init");
        Sound.getInstance(this);
        findViewById(R.id.clickButton).setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clickButton:
                clickSoundPlay();
                break;
        }
    }

    public void clickSoundPlay() {
        Log.i("sound_test", "click button");
        Sound.getInstance(this).playSound(SoundTypeDefault.CLICK);
    }
}
