package com.corp.srihari.increment;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

import java.util.List;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(SampleSlide.newInstance1(R.layout.slide_1));
        addSlide(SampleSlide.newInstance1(R.layout.slide_2));
        addSlide(SampleSlide.newInstance1(R.layout.slide_3));
        addSlide(SampleSlide.newInstance1(R.layout.slide_4));

    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Runtime.getRuntime().gc();
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Runtime.getRuntime().gc();
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        MusicService.play_music(IntroActivity.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MusicService.pause_music();
    }

    @Override
    public void onResume() {
        super.onResume();
        MusicService.play_music(IntroActivity.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}


