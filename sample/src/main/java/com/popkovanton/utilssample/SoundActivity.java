package com.popkovanton.utilssample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.popkovanton.utils.music.MusicDefault;
import com.popkovanton.utils.sound.SoundTypeDefault;
import com.popkovanton.utilssample.util.Sound;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SoundActivity extends AppCompatActivity implements
        SoundListAdapter.ISoundList {

    @BindView(R.id.soundList)
    RecyclerView soundList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        ButterKnife.bind(this);

        getLifecycle().addObserver(MusicDefault.getInstance(this));
        Sound.getInstance(this);
        initMusicList();
    }

    private void initMusicList() {
        SoundListAdapter soundListAdapter = new SoundListAdapter(this, getMusicList(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        soundList.setLayoutManager(linearLayoutManager);
        soundList.setAdapter(soundListAdapter);
        soundList.setHasFixedSize(true);
    }

    private ArrayList<Integer> getMusicList() {
        ArrayList<Integer> musicList = new ArrayList<>();
        musicList.add(R.raw.sample_1);
        musicList.add(R.raw.sample_2);
        musicList.add(R.raw.sample_3);
        musicList.add(R.raw.sample_4);
        return musicList;
    }


    public void clickSoundPlay() {
        Sound.getInstance(this).playSound(SoundTypeDefault.CLICK);
    }

    @OnClick(R.id.bPlay)
    public void musicPlay() {
        clickSoundPlay();
        MusicDefault.getInstance(this).initSoundOnOff();
    }

    @OnClick(R.id.bPause)
    public void musicPause() {
        clickSoundPlay();
        MusicDefault.getInstance(this).pause();
    }

    @OnClick(R.id.bRelease)
    public void musicRelease() {
        clickSoundPlay();
        MusicDefault.getInstance(this).release();
    }

    @OnClick(R.id.bReset)
    public void musicReset() {
        clickSoundPlay();
        MusicDefault.getInstance(this).reset();
    }

    @OnClick(R.id.soundSwitch)
    public void musicSwitch() {
        clickSoundPlay();
        MusicDefault.getInstance(this).soundSwitch();
    }

    @Override
    public void onElementClick(int resId) {
        clickSoundPlay();
        MusicDefault.getInstance(this).reset();
        MusicDefault.getInstance(this).setMusicRes(resId);
        MusicDefault.getInstance(this).initSoundOnOff();
    }
}
