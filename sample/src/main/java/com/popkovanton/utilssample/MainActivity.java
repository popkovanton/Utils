package com.popkovanton.utilssample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.soundActivity).setOnClickListener(this);
        findViewById(R.id.recomputeImageActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.soundActivity:
                intent = new Intent(this, SoundActivity.class);
                startActivity(intent);
                break;
            case R.id.recomputeImageActivity:
                intent = new Intent(this, RecomputeImageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
